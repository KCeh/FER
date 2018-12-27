public class TASK1 {
    public static void main(String[] args) {
        Function3DZ4 function3DZ4= new Function3DZ4(5,1);
        Function1 function1=new Function1();
        Function6 function6=new Function6(2,1);
        Function7 function7 = new Function7(2,1);
        int sizeOfPopulation = 100;
        int tournamentK=3;
        short typeOfSolution = 0;
        short typeOfCrossing = 0;
        double mutationProbability = 0.25;
        int maxNumberOfEvaluations = 100000;
        int lowerLimit = -50;
        int upperLimit = 150;
        int numberOfVariables = 5;
        int precision = 3;


        try {
            System.out.println("TASK 1");
            System.out.println();
            System.out.println("Binary representation");
            System.out.println("Function 3 (5 var)");
            GeneticAlgorithm geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function3DZ4);
            geneticAlgorithm.start(false);

            numberOfVariables = 2;
            System.out.println("Function 6 (2 var)");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            geneticAlgorithm.start(false);

            System.out.println("Function 7 (2 var)");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
            geneticAlgorithm.start(false);

            System.out.println();
            System.out.println("Floating point representation");
            numberOfVariables=5;
            typeOfSolution=1;
            System.out.println("Function 3 (5 var)");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function3DZ4);
            geneticAlgorithm.start(false);

            numberOfVariables = 2;
            System.out.println("Function 6 (2 var)");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            geneticAlgorithm.start(false);

            System.out.println("Function 7 (2 var)");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
            geneticAlgorithm.start(false);


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}