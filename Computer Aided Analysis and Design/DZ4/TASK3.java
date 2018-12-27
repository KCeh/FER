import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class TASK3 {
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
            System.out.println("TASK 3");
            function6=new Function6(3,1);
            precision=4;
            maxNumberOfEvaluations=200000;
            mutationProbability=0.1;
            typeOfSolution=0;
            numberOfVariables=3;
            GeneticAlgorithm geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("TASK3-function6.txt"), "utf-8"))) {
                writer.write("Pop. size = "+sizeOfPopulation+" mutation prob = "+mutationProbability +" max. numb. of evaluations = "
                        +maxNumberOfEvaluations+"\n");
                writer.write("Binary chromosome, precision = "+precision+"\n");
                writer.write("Number of var = 3\n");
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }

                writer.write("\nNumber of var = 6\n");
                numberOfVariables=6;
                function6=new Function6(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }

                writer.write("\n\nFloating point chromosome\n");
                writer.write("Number of var = 3\n");
                typeOfSolution=1;
                numberOfVariables=3;
                function6=new Function6(3,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }
                writer.write("\nNumber of var = 6\n");
                numberOfVariables=6;
                function6=new Function6(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }
            }


            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("TASK3-function7.txt"), "utf-8"))) {
                writer.write("Pop. size = "+sizeOfPopulation+" mutation prob = "+mutationProbability +" max. numb. of evaluations = "
                        +maxNumberOfEvaluations+"\n");
                writer.write("Binary chromosome, precision = "+precision+"\n");
                writer.write("Number of var = 3\n");
                numberOfVariables=3;
                typeOfSolution=0;
                function7=new Function7(3,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }

                writer.write("\nNumber of var = 6\n");
                numberOfVariables=6;
                function7=new Function7(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }

                writer.write("\n\nFloating point chromosome\n");
                writer.write("Number of var = 3\n");
                typeOfSolution=1;
                numberOfVariables=3;
                function7=new Function7(3,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }
                writer.write("\nNumber of var = 6\n");
                numberOfVariables=6;
                function7=new Function7(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write(geneticAlgorithm.start(false));
                }
            }


            /*
            System.out.println("TASK 3 - box plot");
            function6=new Function6(3,1);
            precision=4;
            maxNumberOfEvaluations=200000;
            mutationProbability=0.1;
            typeOfSolution=0;
            numberOfVariables=3;
            GeneticAlgorithm geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("TASK3-function6-boxPlot.txt"), "utf-8"))) {
                writer.write("Chromosome=binary\n");
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 3, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                numberOfVariables=6;
                function6=new Function6(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 6, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                writer.write("\nChromosome=floating point\n");
                typeOfSolution=1;
                numberOfVariables=3;
                function6=new Function6(3,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 3, ");
                    writer.write(geneticAlgorithm.start(true));
                }
                numberOfVariables=6;
                function6=new Function6(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 6, ");
                    writer.write(geneticAlgorithm.start(true));
                }
            }


            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("TASK3-function7-boxPlot.txt"), "utf-8"))) {
                writer.write("Binary chromosome\n");
                numberOfVariables=3;
                typeOfSolution=0;
                function7=new Function7(3,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 3, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                numberOfVariables=6;
                function7=new Function7(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 6, ");
                    writer.write(geneticAlgorithm.start(true));
                }

                writer.write("\n\nFloating point chromosome\n");
                typeOfSolution=1;
                numberOfVariables=3;
                function7=new Function7(3,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 3, ");
                    writer.write(geneticAlgorithm.start(true));
                }
                numberOfVariables=6;
                function7=new Function7(6,1);
                geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                        ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
                for(int i=0;i<10;i++){
                    writer.write("Number of var = 6, ");
                    writer.write(geneticAlgorithm.start(true));
                }
            }
            */

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
