import java.util.*;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    private int sizeOfPopulation;
    private short typeOfSolution;
    private short typeOfCrossing;
    private double mutationProbability;
    private int maxNumberOfEvaluations;
    private int lowerLimit;
    private int upperLimit;
    private int numberOfVariables;
    private int precision;
    private GoalFunction goalFunction;
    private int tournamentK;

    public GeneticAlgorithm(int sizeOfPopulation, short typeOfSolution, short typeOfCrossing, double mutationProbability,
                            int maxNumberOfEvaluations, int lowerLimit, int upperLimit, int numberOfVariables, int precision,
                            GoalFunction goalFunction) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.typeOfSolution = typeOfSolution;
        this.typeOfCrossing = typeOfCrossing;
        this.mutationProbability = mutationProbability;
        this.maxNumberOfEvaluations = maxNumberOfEvaluations;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.numberOfVariables = numberOfVariables;
        this.precision = precision;
        this.goalFunction = goalFunction;
        this.tournamentK=3;
    }

    public GeneticAlgorithm(int sizeOfPopulation, short typeOfSolution, short typeOfCrossing, double mutationProbability,
                            int maxNumberOfEvaluations, int lowerLimit, int upperLimit, int numberOfVariables, int precision,
                            GoalFunction goalFunction, int tournamentK) {
        this(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability,maxNumberOfEvaluations,lowerLimit,
                upperLimit,numberOfVariables,precision,goalFunction);
        this.tournamentK=tournamentK;
    }

    //ucitavanje iz datoteke...

    public String start(boolean boxPlot) throws MatrixSizeIncompatibleException {
        System.out.println("Genetic algorithm start!");
        goalFunction.resetNumberOfCalls();
        int numberOfEvaluationsForSolution=0;
        if (typeOfSolution == 0) {
            if (typeOfCrossing == 0) {
                System.out.println("Using binary chromosome with single point crossing");
            } else {
                System.out.println("Using binary chromosome with uniform crossing");
            }
        } else {
            if (typeOfCrossing == 0) {
                System.out.println("Using float point chromosome with arithmetic crossing");
            } else {
                System.out.println("Using float point chromosome with heuristic crossing");
            }
        }

        Random random = new Random();
        Chromosome[] population = new Chromosome[sizeOfPopulation];
        double bestFitness = Double.MAX_VALUE;
        int bestFitnessIndex = -1;
        if (typeOfSolution == 0) {
            for (int i = 0; i < sizeOfPopulation; i++) {
                population[i] = new BinaryChromosome(numberOfVariables, random, precision, lowerLimit, upperLimit);
            }
        } else {
            for (int i = 0; i < sizeOfPopulation; i++) {
                population[i] = new FloatingPointChromosome(numberOfVariables, random, lowerLimit, upperLimit);
            }
        }

        for (int i = 0; i < sizeOfPopulation; i++) {
            //- goal vrijednost ???
            //pazi trebalo bi popraviti i na drugim mjestima
            double fitness = goalFunction.getValueAt(population[i].getPoint()).getElementAt(0, 0);
            if (bestFitness > fitness) {
                bestFitness = fitness;
                bestFitnessIndex = i;
                numberOfEvaluationsForSolution=goalFunction.getNumberOfCalls();
            }
            population[i].setFitness(fitness);
        }
        System.out.println("Best solution " + population[bestFitnessIndex] + " goal function value = " + bestFitness
                + " number of evaluations = " + goalFunction.getNumberOfCalls());


        while (true) {
            double oldBestFitness = bestFitness;
            Map<Integer, Double> map = new TreeMap<>();
            while (map.size()!=tournamentK){
                int a = random.nextInt(sizeOfPopulation);
                map.put(a,population[a].getFitness());
            }
            Map<Integer,Double> sorted =
                    map.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            Set<Map.Entry<Integer,Double>> set = sorted.entrySet();
            int worstIndex=1;
            int parent1=0;
            int parent2=0;
            int k=0;
            for(Map.Entry<Integer,Double> entry:set){
                if(k==0) worstIndex=entry.getKey();
                if(k==set.size()-2) parent2=entry.getKey();
                if(k==set.size()-1) parent1=entry.getKey();
                k++;
            }
            Chromosome child;


            if (typeOfSolution == 0) {
                if (typeOfCrossing == 0) {
                    child = singlePointCrossing((BinaryChromosome) population[parent1], (BinaryChromosome) population[parent2]);
                } else {
                    child = multiplePointsCrossing((BinaryChromosome) population[parent1], (BinaryChromosome) population[parent2]);
                }

                mutateBinary((BinaryChromosome) child, mutationProbability, random);
            } else {
                if (typeOfCrossing == 0) {
                    child = arithmeticCrossing((FloatingPointChromosome)population[parent1], (FloatingPointChromosome)population[parent2], random);
                } else {
                    child = heuristicCrossing((FloatingPointChromosome)population[parent1], (FloatingPointChromosome)population[parent2], random);
                }
                mutateFP((FloatingPointChromosome) child, mutationProbability, random);
            }

            double fitness = goalFunction.getValueAt(child.getPoint()).getElementAt(0, 0);
            if (bestFitness > fitness) {
                bestFitness = fitness;
                bestFitnessIndex = worstIndex;
            }
            child.setFitness(fitness);
            population[worstIndex] = child;

            if (oldBestFitness > bestFitness) {
                numberOfEvaluationsForSolution=goalFunction.getNumberOfCalls();
                System.out.println("Best solution " + population[bestFitnessIndex] + ", goal function value = " + bestFitness
                        + ", number of evaluations = " + numberOfEvaluationsForSolution);

            }

            if (goalFunction.getNumberOfCalls() >= maxNumberOfEvaluations || bestFitness < 1E-6){
                break;
            }
        }
        String solution;
        if(!boxPlot) {
            solution = ("Best solution " + population[bestFitnessIndex] + ", goal function value = " + bestFitness
                    + ", number of evaluations = " + numberOfEvaluationsForSolution + "\n");
        }else {
            solution=Double.valueOf(bestFitness).toString()+"\n";
        }
        return solution;
    }

    private BinaryChromosome singlePointCrossing(BinaryChromosome first, BinaryChromosome second) {
        int size = first.getSize();
        int[] elements = new int[size];
        for (int i = 0; i < size; i++) {
            int[] a = first.getBinary(i);
            int[] b = second.getBinary(i);
            int[] c = new int[a.length];

            for (int j = 0, len = a.length; j < len; j++) {
                if (j < len / 2) {
                    c[j] = a[j];
                } else {
                    c[j] = b[j];
                }
            }

            String tmp = "";
            for (int j = 0, len = c.length; j < len; j++) {
                tmp += c[j];
            }

            elements[i] = Integer.parseInt(tmp, 2);
        }

        return new BinaryChromosome(size, first.getChromosomeSize(), elements, first.getLowerLimit(), first.getUpperLimit());
    }

    private BinaryChromosome multiplePointsCrossing(BinaryChromosome first, BinaryChromosome second) {
        int size = first.getSize();
        int[] elements = new int[size];
        for (int i = 0; i < size; i++) {
            int[] a = first.getBinary(i);
            int[] b = second.getBinary(i);
            int[] c = new int[a.length];

            for (int j = 0, len = a.length; j < len; j++) {
                if (j < len / 4) {
                    c[j] = a[j];
                } else if (j < len / 2) {
                    c[j] = b[j];
                } else {
                    c[j] = a[j];
                }
            }

            String tmp = "";
            for (int j = 0, len = c.length; j < len; j++) {
                tmp += c[j];
            }

            elements[i] = Integer.parseInt(tmp, 2);
        }
        return new BinaryChromosome(size, first.getChromosomeSize(), elements, first.getLowerLimit(), first.getUpperLimit());
    }

    private void mutateBinary(BinaryChromosome child, double mutationProbability, Random random) {
        for (int i = 0; i < child.getSize(); i++) {
            if (random.nextDouble() <= mutationProbability) {
                int[] sol = child.getBinary(i);
                for (int j = 0; j < sol.length; j++) {
                    if (random.nextDouble() <= mutationProbability) {
                        sol[j] = 1 - sol[j];
                    }
                }
                String tmp = "";
                for (int j : sol) {
                    tmp += sol[j];
                }
                int el = Integer.parseInt(tmp, 2);
                child.setElementsAt(i, el);
            }
        }
    }

    private FloatingPointChromosome arithmeticCrossing(FloatingPointChromosome first, FloatingPointChromosome second, Random random) {
        int size=first.getSize();
        double[] elements=new double[size];
        for (int i=0;i<size;i++){
            double a=random.nextDouble();
            elements[i]=a*first.getElementAt(i)+(1-a)*second.getElementAt(i);
            if(elements[i]<lowerLimit) elements[i]=lowerLimit;
            else if(elements[i]>upperLimit) elements[i]=upperLimit;
        }

        return new FloatingPointChromosome(size,lowerLimit,upperLimit,elements);
    }

    private FloatingPointChromosome heuristicCrossing(FloatingPointChromosome first, FloatingPointChromosome second, Random random) {
        int size=first.getSize();
        double[] elements=new double[size];

        boolean reverse=false;
        if(first.getFitness()>second.getFitness()){
            reverse=true;
        }
        for (int i=0;i<size;i++){
            double a=random.nextDouble();
            if(reverse){
                elements[i]=a*(second.getElementAt(i)-first.getElementAt(i))+second.getElementAt(i);
            }else {
                elements[i]=a*(first.getElementAt(i)-second.getElementAt(i))+first.getElementAt(i);
            }
            if(elements[i]<lowerLimit) elements[i]=lowerLimit;
            else if(elements[i]>upperLimit) elements[i]=upperLimit;
        }

        return new FloatingPointChromosome(size,lowerLimit,upperLimit,elements);
    }

    private void mutateFP(FloatingPointChromosome child, double mutationProbability, Random random) {
        for (int i = 0; i < child.getSize(); i++) {
            if (random.nextDouble() <= mutationProbability) {
                double value=random.nextDouble() * (upperLimit - lowerLimit) + lowerLimit;
                child.setElementAt(i, value);
            }
        }
    }

}
