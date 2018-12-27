public class TASK2 {
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
            System.out.println("TASK 2");
            typeOfSolution=1;
            typeOfCrossing=0;
            System.out.println("Number of var = 1");
            numberOfVariables=1;
            System.out.println("Function 6");
            function6=new Function6(1,1);
            function7 = new Function7(1,1);
            GeneticAlgorithm geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            geneticAlgorithm.start(false);
            System.out.println("Function 7");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
            geneticAlgorithm.start(false);

            System.out.println();
            System.out.println("Number of var = 3");
            System.out.println("Function 6");
            numberOfVariables=3;
            function6=new Function6(3,1);
            function7 = new Function7(3,1);
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            geneticAlgorithm.start(false);
            System.out.println("Function 7");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
            geneticAlgorithm.start(false);

            System.out.println();
            System.out.println("Number of var = 6");
            System.out.println("Function 6");
            numberOfVariables=6;
            sizeOfPopulation = 200;
            function6=new Function6(6,1);
            function7 = new Function7(6,1);
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            geneticAlgorithm.start(false);
            System.out.println("Function 7");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
            geneticAlgorithm.start(false);

            System.out.println();
            System.out.println("Number of var = 10");
            System.out.println("Function 6");
            numberOfVariables=10;
            mutationProbability=0.1;
            function6=new Function6(10,1);
            function7 = new Function7(10,1);
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function6);
            geneticAlgorithm.start(false);
            System.out.println("Function 7");
            geneticAlgorithm=new GeneticAlgorithm(sizeOfPopulation,typeOfSolution,typeOfCrossing,mutationProbability
                    ,maxNumberOfEvaluations,lowerLimit,upperLimit,numberOfVariables,precision,function7);
            geneticAlgorithm.start(false);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
