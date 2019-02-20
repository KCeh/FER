public class DZ5 {
    public static void main(String[] args){
        Matrix matrix = new Matrix("DZ5-1.txt");
        Matrix inverse;

        try {
            System.out.println("TASK 1");
            inverse = Matrix.getInverse(matrix);
            System.out.println("Inverse : ");
            System.out.println(inverse);
        }catch (Exception ex){
            System.out.println("Inverse doesn't exist");
        }

        try {
            System.out.println("TASK 2");
            matrix=new Matrix("DZ5-2.txt");
            inverse = Matrix.getInverse(matrix);
            System.out.println("Inverse : ");
            System.out.println(inverse);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        try {
            System.out.println("TASK 3");
            matrix=new Matrix("DZ5-3.txt");
            Matrix x = new Matrix(2,1);
            x.setElementAt(0,0,0);
            x.setElementAt(2,1,0);
            NumericalIntegration numericalIntegration = new TrapezeMethod();
            numericalIntegration.integrate(matrix,x,0.1,7,1, true, "DZ5-3-solution-trapeze.txt");

            System.out.println();
            numericalIntegration = new RungeKutta();
            numericalIntegration.integrate(matrix,x,0.1,7,1, true, "DZ5-3-solution-RungeKutta.txt");

            System.out.println();
            System.out.println("TASK 3 - attenuation");
            matrix=new Matrix("DZ5-3-1.txt");
            numericalIntegration.integrate(matrix,x,0.1,10,1, true, "DZ5-3-1-solution.txt");

            System.out.println();
            System.out.println("TASK 4");
            matrix=new Matrix("DZ5-4.txt");
            x.setElementAt(1,0,0);
            x.setElementAt(-2,1,0);
            numericalIntegration = new TrapezeMethod();
            numericalIntegration.integrate(matrix,x,0.1,5,1, true, "DZ5-4-solution-trapeze.txt");

            numericalIntegration = new RungeKutta();
            numericalIntegration.integrate(matrix,x,0.1,5,1, true, "DZ5-4-solution-RungeKutta.txt");
            numericalIntegration.integrate(matrix,x,0.01,5,10, true, "DZ5-4-solution-RungeKutta-correct.txt");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
