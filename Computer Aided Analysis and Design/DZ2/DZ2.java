import java.util.Random;

public class DZ2 {
    public static void main(String[] args){
        Function3 function3 = new Function3();
        Function1 function1 = new Function1();
        Function2 function2 = new Function2();
        Function3Task2 function3Task2 = new Function3Task2();
        Function4 function4=new Function4();
        Function6 function6=new Function6();
        GoldenRatio goldenRatio;
        Matrix startingPoint=new Matrix(1,1);
        startingPoint.setElementAt(10,0,0);
        CoordinateAxesSearch coordinateAxesSearch;
        SimplexNelderMead simplexNelderMead;
        HookeJeeves hookeJeeves;
        try {

            System.out.println("TASK 1");
            goldenRatio = new GoldenRatio(10,function3);
            goldenRatio.start(true);
            System.out.println("Number of function calls for Golden Ration: "+function3.getNumberOfCalls());
            System.out.println();
            function3.resetNumberOfCalls();

            coordinateAxesSearch=new CoordinateAxesSearch(null, startingPoint,function3);
            coordinateAxesSearch.start();
            System.out.println("Number of function calls for Coordinate Axes Search:" + function3.getNumberOfCalls());
            function3.resetNumberOfCalls();

            simplexNelderMead=new SimplexNelderMead(function3);
            simplexNelderMead.start(startingPoint,true);
            System.out.println("Number of function calls for Simplex Nelder Mead:" + function3.getNumberOfCalls());
            function3.resetNumberOfCalls();

            hookeJeeves=new HookeJeeves(function3);
            hookeJeeves.start(startingPoint,true);
            System.out.println("Number of function calls for Hooke-Jeeves:" + function3.getNumberOfCalls());
            function3.resetNumberOfCalls();

            startingPoint.setElementAt(25,0,0);
            goldenRatio = new GoldenRatio(25,function3);
            goldenRatio.start(true);
            System.out.println("Number of function calls for Golden Ration: "+function3.getNumberOfCalls());
            System.out.println();
            function3.resetNumberOfCalls();

            coordinateAxesSearch=new CoordinateAxesSearch(null, startingPoint,function3);
            coordinateAxesSearch.start();
            System.out.println("Number of function calls for Coordinate Axes Search:" + function3.getNumberOfCalls());
            function3.resetNumberOfCalls();

            simplexNelderMead=new SimplexNelderMead(function3);
            simplexNelderMead.start(startingPoint,true);
            System.out.println("Number of function calls for Simplex Nelder Mead:" + function3.getNumberOfCalls());
            function3.resetNumberOfCalls();

            hookeJeeves=new HookeJeeves(function3);
            hookeJeeves.start(startingPoint,true);
            System.out.println("Number of function calls for Hooke-Jeeves:" + function3.getNumberOfCalls());
            function3.resetNumberOfCalls();

            System.out.println();
            System.out.println("TASK 2");

            startingPoint=new Matrix(2,1);
            startingPoint.setElementAt(-1.9,0,0);
            startingPoint.setElementAt(2,1,0);
            coordinateAxesSearch=new CoordinateAxesSearch(null,startingPoint,function1);
            double coordinateMin=coordinateAxesSearch.start();
            int coordinateCalls = function1.getNumberOfCalls();
            function1.resetNumberOfCalls();
            simplexNelderMead=new SimplexNelderMead(function1);
            double simplexMin=simplexNelderMead.start(startingPoint,false);
            int simplexCalls=function1.getNumberOfCalls();
            function1.resetNumberOfCalls();
            hookeJeeves=new HookeJeeves(function1);
            double hookeMin=hookeJeeves.start(startingPoint,false);
            int hookeCalls=function1.getNumberOfCalls();
            function1.resetNumberOfCalls();

            System.out.println("Comparison for function 1");
            System.out.println("Method\t\t\t\tMin\t\t\tNumber of calls");
            System.out.println("Coordinate search: "+coordinateMin+"\t\t\t\t"+coordinateCalls);
            System.out.println("Simplex Nelder Mead: "+simplexMin+"\t\t\t"+simplexCalls);
            System.out.println("Hooke-Jeeves: "+hookeMin+"\t\t\t\t\t"+hookeCalls);

            System.out.println();
            startingPoint=new Matrix(2,1);
            startingPoint.setElementAt(0.1,0,0);
            startingPoint.setElementAt(0.3,1,0);
            coordinateAxesSearch=new CoordinateAxesSearch(null,startingPoint,function2);
            coordinateMin=coordinateAxesSearch.start();
            coordinateCalls = function2.getNumberOfCalls();
            function2.resetNumberOfCalls();
            simplexNelderMead=new SimplexNelderMead(function2);
            simplexMin=simplexNelderMead.start(startingPoint,false);
            simplexCalls=function2.getNumberOfCalls();
            function2.resetNumberOfCalls();
            hookeJeeves=new HookeJeeves(function2);
            hookeMin=hookeJeeves.start(startingPoint,false);
            hookeCalls=function2.getNumberOfCalls();
            function2.resetNumberOfCalls();

            System.out.println();
            System.out.println("Comparison for function 2");
            System.out.println("Method\t\t\t\tMin\t\t\tNumber of calls");
            System.out.println("Coordinate search: "+coordinateMin+"\t\t\t\t"+coordinateCalls);
            System.out.println("Simplex Nelder Mead: "+simplexMin+"\t\t\t"+simplexCalls);
            System.out.println("Hooke-Jeeves: "+hookeMin+"\t\t\t\t\t"+hookeCalls);

            System.out.println();
            startingPoint=new Matrix(5,1);
            startingPoint.setElementAt(0,0,0);
            startingPoint.setElementAt(0,1,0);
            startingPoint.setElementAt(0,2,0);
            startingPoint.setElementAt(0,3,0);
            startingPoint.setElementAt(0,4,0);
            coordinateAxesSearch=new CoordinateAxesSearch(null,startingPoint,function3Task2);
            coordinateMin=coordinateAxesSearch.start();
            coordinateCalls = function3Task2.getNumberOfCalls();
            function3Task2.resetNumberOfCalls();
            simplexNelderMead=new SimplexNelderMead(function3Task2);
            simplexMin=simplexNelderMead.start(startingPoint,false);
            simplexCalls=function3Task2.getNumberOfCalls();
            function3Task2.resetNumberOfCalls();
            hookeJeeves=new HookeJeeves(function3Task2);
            hookeMin=hookeJeeves.start(startingPoint,false);
            hookeCalls=function3Task2.getNumberOfCalls();
            function3Task2.resetNumberOfCalls();

            System.out.println();
            System.out.println("Comparison for function 3");
            System.out.println("Method\t\t\t\tMin\t\t\tNumber of calls");
            System.out.println("Coordinate search: "+coordinateMin+"\t\t\t\t"+coordinateCalls);
            System.out.println("Simplex Nelder Mead: "+simplexMin+"\t\t\t"+simplexCalls);
            System.out.println("Hooke-Jeeves: "+hookeMin+"\t\t\t\t\t"+hookeCalls);

            System.out.println();
            startingPoint=new Matrix(2,1);
            startingPoint.setElementAt(5.1,0,0);
            startingPoint.setElementAt(1.1,1,0);
            coordinateAxesSearch=new CoordinateAxesSearch(null,startingPoint,function4);
            coordinateMin=coordinateAxesSearch.start();
            coordinateCalls = function4.getNumberOfCalls();
            function4.resetNumberOfCalls();
            simplexNelderMead=new SimplexNelderMead(function4);
            simplexMin=simplexNelderMead.start(startingPoint,false);
            simplexCalls=function4.getNumberOfCalls();
            function4.resetNumberOfCalls();
            hookeJeeves=new HookeJeeves(function4);
            hookeMin=hookeJeeves.start(startingPoint,false);
            hookeCalls=function4.getNumberOfCalls();
            function4.resetNumberOfCalls();

            System.out.println();
            System.out.println("Comparison for function 4");
            System.out.println("Method\t\t\t\tMin\t\t\tNumber of calls");
            System.out.println("Coordinate search: "+coordinateMin+"\t\t\t\t"+coordinateCalls);
            System.out.println("Simplex Nelder Mead: "+simplexMin+"\t\t\t"+simplexCalls);
            System.out.println("Hooke-Jeeves: "+hookeMin+"\t\t\t\t\t"+hookeCalls);

            System.out.println();
            System.out.println("TASK 3");
            startingPoint=new Matrix(2,1);
            startingPoint.setElementAt(5,0,0);
            startingPoint.setElementAt(5,1,0);
            function4.resetNumberOfCalls();
            simplexNelderMead=new SimplexNelderMead(function4);
            simplexMin=simplexNelderMead.start(startingPoint,true);
            simplexCalls=function4.getNumberOfCalls();
            function4.resetNumberOfCalls();
            hookeJeeves=new HookeJeeves(function4);
            hookeMin=hookeJeeves.start(startingPoint,true);
            hookeCalls=function4.getNumberOfCalls();
            function4.resetNumberOfCalls();
            System.out.println("Method\t\t\t\tMin\t\t\tNumber of calls");
            System.out.println("Simplex Nelder Mead: "+simplexMin+"\t\t\t"+simplexCalls);
            System.out.println("Hooke-Jeeves: "+hookeMin+"\t\t\t\t\t"+hookeCalls);

            System.out.println();
            System.out.println("TASK 4");
            startingPoint.setElementAt(0.5,0,0);
            startingPoint.setElementAt(0.5,1,0);
            int[] numOfCalls= new int[20];
            double[] mins=new double[20];
            for(int i=1;i<=20;i++){
                simplexNelderMead=new SimplexNelderMead(function1,i, 1E-6,1,0.5,2,0.5);
                mins[i-1]=simplexNelderMead.start(startingPoint,false);
                numOfCalls[i-1]=function1.getNumberOfCalls();
                function1.resetNumberOfCalls();
            }
            System.out.println("Starting point (0.5,0.5)");
            System.out.println("Movement\t\t\t\tMin\t\t\tNumber of calls");
            for(int i=1;i<=20;i++){
                System.out.println(i+"\t\t\t"+mins[i-1]+"\t\t\t"+numOfCalls[i-1]);
            }

            startingPoint.setElementAt(20,0,0);
            startingPoint.setElementAt(20,1,0);
            for(int i=1;i<=20;i++){
                simplexNelderMead=new SimplexNelderMead(function1,i, 1E-6,1,0.5,2,0.5);
                mins[i-1]=simplexNelderMead.start(startingPoint,false);
                numOfCalls[i-1]=function1.getNumberOfCalls();
                function1.resetNumberOfCalls();
            }
            System.out.println("Starting point (20,20)");
            System.out.println("Movement\t\t\t\tMin\t\t\tNumber of calls");
            for(int i=1;i<=20;i++){
                System.out.println(i+"\t\t\t"+mins[i-1]+"\t\t\t"+numOfCalls[i-1]);
            }


            System.out.println();
            System.out.println("TASK 5");
            Random random= new Random();
            double el;
            startingPoint=new Matrix(2,1);

            mins=new double[20];

            simplexNelderMead=new SimplexNelderMead(function6);
            hookeJeeves = new HookeJeeves(function6);
            //simplexNelderMead.setThreshold(1E-4);
            for(int i=0;i<20;i++){

                el=-50+(50+50)*random.nextDouble();
                startingPoint.setElementAt(el,0,0);
                el=-50+(50+50)*random.nextDouble();
                startingPoint.setElementAt(el,1,0);
                mins[i]=simplexNelderMead.start(startingPoint,false);
                //coordinateAxesSearch=new CoordinateAxesSearch(null,startingPoint,function6);
                //mins[i]=coordinateAxesSearch.start();
            }

            System.out.println("Found minimums");
            for(int i=0;i<20;i++){
                System.out.println(mins[i]);
            }


        } catch (MatrixSizeIncompatibleException e) {
            e.printStackTrace();
        }
    }
}
