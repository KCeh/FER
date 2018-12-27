import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class TASK4 {
    public static void main(String[] args) {
        Function3DZ4 function3DZ4= new Function3DZ4(5,1);
        Function1 function1=new Function1();
        Function6 function6=new Function6(2,1);
        Function7 function7 = new Function7(2,1);
        int sizeOfPopulation = 100;
        int tournamentK=3;
        short typeOfSolution = 0;
        short typeOfCrossing = 0;
        double mutationProbability = 0.3;
        int maxNumberOfEvaluations = 100000;
        int lowerLimit = -50;
        int upperLimit = 150;
        int numberOfVariables = 5;
        int precision = 3;


        try {
            System.out.println("TASK 4");
            function6=new Function6(6,1);
            numberOfVariables=6;
            typeOfSolution=1;
            mutationProbability=0.1;
            sizeOfPopulation=30;

            GeneticAlgorithm geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("TASK4-sizeOfPopulation-boxPlot.txt"), "utf-8"))) {
                for(int i=0;i<10;i++){
                    writer.write("Size of population = 30, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                sizeOfPopulation=50;
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Size of population = 50, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                sizeOfPopulation=100;
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Size of population = 100, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                sizeOfPopulation=200;
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Size of population = 200, ");
                    writer.write(geneticAlgorithm.start(true));
                }
            }

            sizeOfPopulation=100;
            mutationProbability=0.1;
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("TASK4-mutationProbability-boxPlot.txt"), "utf-8"))) {
                for(int i=0;i<10;i++){
                    writer.write("Mutation probability = 0.1, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                mutationProbability=0.3;
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Mutation probability = 0.3, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                mutationProbability=0.6;
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Mutation probability = 0.6, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                mutationProbability=0.9;
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Mutation probability = 0.9, ");
                    writer.write(geneticAlgorithm.start(true));
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
