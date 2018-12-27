import java.util.ArrayList;

public class DZ3 {
    public static void main(String[] args) {
        try {
            Function1 function1 = new Function1();
            Function2 function2 = new Function2();
            Function3 function3 = new Function3();
            Function4 function4 = new Function4();
            Matrix startingPoint = new Matrix(2, 1);

            System.out.println("TASK 1");

            System.out.println("Function 3 without golden ration");
            startingPoint.setElementAt(0, 0, 0);
            startingPoint.setElementAt(0, 1, 0);
            GradientDescent gradientDescent = new GradientDescent(function3);
            gradientDescent.start(startingPoint, false);
            System.out.println("number of calls: " + function3.getNumberOfCalls());
            System.out.println("number of gradient calls: " + function3.getNumberOfGradientCalls());
            function3.resetNumberOfCalls();
            function3.resetNumberOfGradientCalls();

            System.out.println("Function 3 with golden ration");
            startingPoint.setElementAt(0, 0, 0);
            startingPoint.setElementAt(0, 1, 0);
            gradientDescent = new GradientDescent(function3);
            gradientDescent.start(startingPoint, true);
            System.out.println("number of calls: " + function3.getNumberOfCalls());
            System.out.println("number of gradient calls: " + function3.getNumberOfGradientCalls());
            function3.resetNumberOfCalls();
            function3.resetNumberOfGradientCalls();


            System.out.println("\nTASK 2");

            System.out.println("Function 1 with fastest gradient descend");
            startingPoint.setElementAt(-1.9, 0, 0);
            startingPoint.setElementAt(2, 1, 0);
            gradientDescent=new GradientDescent(function1);
            gradientDescent.start(startingPoint,true);
            System.out.println("number of calls: " + function1.getNumberOfCalls());
            System.out.println("number of gradient calls: " + function1.getNumberOfGradientCalls());
            function1.resetNumberOfCalls();
            function1.resetNumberOfGradientCalls();

            System.out.println("Function 1 with Newton-Raphson");
            NewtonRaphson newtonRaphson=new NewtonRaphson(function1);
            newtonRaphson.start(startingPoint, true);
            System.out.println("number of calls: " + function1.getNumberOfCalls());
            System.out.println("number of gradient calls: " + function1.getNumberOfGradientCalls());
            System.out.println("number of Hess calls: "+function1.getNumberOfHessCalls());
            function1.resetNumberOfCalls();
            function1.resetNumberOfGradientCalls();
            function1.resetNumberOfHessCalls();

            System.out.println("Function 2 with fastest gradient descend");
            startingPoint.setElementAt(0.1, 0, 0);
            startingPoint.setElementAt(0.3, 1, 0);
            gradientDescent=new GradientDescent(function2);
            gradientDescent.start(startingPoint,true);
            System.out.println("number of calls: " + function2.getNumberOfCalls());
            System.out.println("number of gradient calls: " + function2.getNumberOfGradientCalls());
            function2.resetNumberOfCalls();
            function2.resetNumberOfGradientCalls();

            System.out.println("Function 2 with Newton-Raphson");
            newtonRaphson=new NewtonRaphson(function2);
            newtonRaphson.start(startingPoint, true);
            System.out.println("number of calls: " + function2.getNumberOfCalls());
            System.out.println("number of gradient calls: " + function2.getNumberOfGradientCalls());
            System.out.println("number of Hess calls: "+function2.getNumberOfHessCalls());
            function2.resetNumberOfCalls();
            function2.resetNumberOfGradientCalls();
            function2.resetNumberOfHessCalls();

            System.out.println("\nTASK 3");
            LimitationFunction limit1=new LimitationFunction1();
            LimitationFunction limit2=new LimitationFunction2();
            ArrayList<LimitationFunction> g= new ArrayList<>();
            g.add(limit1);
            g.add(limit2);
            System.out.println("Function 1");
            startingPoint.setElementAt(-1.9, 0, 0);
            startingPoint.setElementAt(2, 1, 0);
            Box box=new Box(function1,-100,100,g);
            box.start(startingPoint);
            System.out.println("number of calls: " + function1.getNumberOfCalls());
            function1.resetNumberOfCalls();

            System.out.println("Function 2");
            startingPoint.setElementAt(0.1, 0, 0);
            startingPoint.setElementAt(0.3, 1, 0);
            box=new Box(function2,-100,100,g);
            box.start(startingPoint);
            System.out.println("number of calls: " + function2.getNumberOfCalls());
            function2.resetNumberOfCalls();

            System.out.println("\nTASK 4");
            System.out.println("Function 1");
            startingPoint.setElementAt(-1.9, 0, 0);
            startingPoint.setElementAt(2, 1, 0);
            TransformationWithoutLimitations transformationWithoutLimitations=new TransformationWithoutLimitations(function1,-100,100,g,null);
            transformationWithoutLimitations.start(startingPoint);
            System.out.println("number of calls: " + function1.getNumberOfCalls());
            function1.resetNumberOfCalls();

            System.out.println("Function 2");
            startingPoint.setElementAt(0.1, 0, 0);
            startingPoint.setElementAt(0.3, 1, 0);
            transformationWithoutLimitations=new TransformationWithoutLimitations(function2,-100,100,g,null);
            transformationWithoutLimitations.start(startingPoint);
            System.out.println("number of calls: " + function2.getNumberOfCalls());
            function2.resetNumberOfCalls();


            System.out.println("Function 1 with different point");
            startingPoint.setElementAt(2.4, 0, 0);
            startingPoint.setElementAt(2, 1, 0);
            transformationWithoutLimitations=new TransformationWithoutLimitations(function1,-100,100,g,null);
            transformationWithoutLimitations.start(startingPoint);
            System.out.println("number of calls: " + function1.getNumberOfCalls());
            function1.resetNumberOfCalls();

            System.out.println("Function 2 with different point");
            startingPoint.setElementAt(3, 0, 0);
            startingPoint.setElementAt(0.3, 1, 0);
            transformationWithoutLimitations=new TransformationWithoutLimitations(function2,-100,100,g,null);
            transformationWithoutLimitations.start(startingPoint);
            System.out.println("number of calls: " + function2.getNumberOfCalls());
            function2.resetNumberOfCalls();

            System.out.println("\nTASK 5");
            g= new ArrayList<>();
            LimitationFunction limit3=new LimitationFunction3();
            LimitationFunction limit4=new LimitationFunction4();
            LimitationFunction limit5=new LimitationFunction5();
            g.add(limit3);
            g.add(limit4);
            ArrayList<LimitationFunction> h = new ArrayList<>();
            h.add(limit5);

            System.out.println("Function 4");
            startingPoint.setElementAt(0,0,0);
            startingPoint.setElementAt(0,1,0);
             transformationWithoutLimitations=new TransformationWithoutLimitations(function4,-100,100,g,h);
            transformationWithoutLimitations.start(startingPoint);
            System.out.println("number of calls: " + function4.getNumberOfCalls());
            function4.resetNumberOfCalls();

            System.out.println("Function 4 with starting point not within limits");
            startingPoint.setElementAt(5,0,0);
            startingPoint.setElementAt(5,1,0);
            transformationWithoutLimitations=new TransformationWithoutLimitations(function4,-100,100,g,h);
            transformationWithoutLimitations.start(startingPoint);
            System.out.println("number of calls: " + function4.getNumberOfCalls());
            function4.resetNumberOfCalls();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
