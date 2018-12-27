public class DZ1 {
    public static void main(String[] args) {

        System.out.println("1. problem");
        Matrix zad1 = new Matrix("1zad.txt");
        zad1.printToConsole();

        Matrix zad1Test = zad1.multiplyWithScalar(5./3);
        zad1Test.printToConsole();

        zad1Test=zad1Test.multiplyWithScalar(3./5);
        System.out.println("Epsilon = "+Matrix.getThreshold());
        System.out.println("A and A' are equal? "+zad1.equals(zad1Test));

        zad1Test.printToConsole();

        Matrix.setThreshold(1e-17);
        System.out.println("Epsilon = "+Matrix.getThreshold());
        System.out.println("A and A' are equal? "+zad1.equals(zad1Test));


        Matrix.setThreshold(1e-6);
        System.out.println();
        System.out.println("2. problem");
        Matrix zad2Matrix=new Matrix("2zadMatrix.txt");
        Matrix zad2FreeVector= new Matrix("2zadFreeVector.txt");
        Matrix zad2LU = new Matrix(zad2Matrix);
        Matrix zad2LUP = new Matrix(zad2Matrix);

        //zad2LU.printToConsole();
        //zad2LUP.printToConsole();

        try {
            System.out.println("LU decomposition");
            zad2LU.decomposition();
            zad2LU.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        Matrix zad2PermutationMatrix = new Matrix(zad2LUP.getN(),zad2LUP.getM());
        Matrix zad2Y;
        Matrix zad2X;
        try {
            System.out.println();
            System.out.println("LUP decomposition");
            zad2LUP.decomposition(true,zad2PermutationMatrix);
            zad2LUP.printToConsole();
            zad2PermutationMatrix.printToConsole();

            zad2Y=zad2LUP.substitutionForwards(zad2PermutationMatrix.multiply(zad2FreeVector));
            System.out.println("Y");
            zad2Y.printToConsole();

            zad2X=zad2LUP.substitutionBackwards(zad2Y);
            System.out.println("Solution vector X");
            zad2X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("3. problem");
        Matrix zad3Matrix=new Matrix("3zadMatrix.txt");
        Matrix zad3FreeVector= new Matrix("3zadFreeVector.txt");
        Matrix zad3LU = new Matrix(zad3Matrix);
        Matrix zad3LUP = new Matrix(zad3Matrix);
        Matrix zad3Y;
        Matrix zad3X;
        //zad3LU.printToConsole();
        //zad3LUP.printToConsole();

        try {
            System.out.println("LU decomposition");
            zad3LU.decomposition();
            zad3LU.printToConsole();

            zad3Y=zad3LU.substitutionForwards(zad3FreeVector);
            System.out.println("Y");
            zad3Y.printToConsole();

            zad3X=zad3LU.substitutionBackwards(zad3Y);
            System.out.println("Solution vector X");
            zad3X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        Matrix zad3PermutationMatrix = new Matrix(zad3LUP.getN(),zad3LUP.getM());

        try {
            System.out.println();
            System.out.println("LUP decomposition");
            zad3LUP.decomposition(true,zad3PermutationMatrix);
            zad3LUP.printToConsole();
            zad3LUP.printToFile("zad3LUP.txt");
            zad3PermutationMatrix.printToConsole();

            zad3Y=zad3LUP.substitutionForwards(zad3PermutationMatrix.multiply(zad3FreeVector));
            System.out.println("Y");
            zad3Y.printToConsole();

            zad3X=zad3LUP.substitutionBackwards(zad3Y);
            System.out.println("Solution vector X");
            zad3X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println();
        System.out.println("4. problem");
        Matrix zad4Matrix=new Matrix("4zadMatrix.txt");
        Matrix zad4FreeVector= new Matrix("4zadFreeVector.txt");
        Matrix zad4LU = new Matrix(zad4Matrix);
        Matrix zad4LUP = new Matrix(zad4Matrix);
        Matrix zad4Y;
        Matrix zad4X;
        //zad4LU.printToConsole();
        //zad4LUP.printToConsole();

        try {
            System.out.println("LU decomposition");
            zad4LU.decomposition();
            zad4LU.printToConsole();

            zad4Y=zad4LU.substitutionForwards(zad4FreeVector);
            System.out.println("Y");
            zad4Y.printToConsole();

            zad4X=zad4LU.substitutionBackwards(zad4Y);
            System.out.println("Solution vector X");
            zad4X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        Matrix zad4PermutationMatrix = new Matrix(zad4LUP.getN(),zad4LUP.getM());

        try {
            System.out.println();
            System.out.println("LUP decomposition");
            zad4LUP.decomposition(true,zad4PermutationMatrix);
            zad4LUP.printToConsole();
            zad4LUP.printToFile("zad4LUP.txt");
            zad4PermutationMatrix.printToConsole();

            zad4Y=zad4LUP.substitutionForwards(zad4PermutationMatrix.multiply(zad4FreeVector));
            System.out.println("Y");
            zad4Y.printToConsole();

            zad4X=zad4LUP.substitutionBackwards(zad4Y);
            System.out.println("Solution vector X");
            zad4X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("5. problem");
        Matrix zad5Matrix=new Matrix("5zadMatrix.txt");
        Matrix zad5FreeVector= new Matrix("5zadFreeVector.txt");
        Matrix zad5LUP = new Matrix(zad5Matrix);
        Matrix zad5Y;
        Matrix zad5X;
        //zad5LUP.printToConsole();

        Matrix zad5PermutationMatrix = new Matrix(zad5LUP.getN(),zad5LUP.getM());

        try {
            System.out.println();
            System.out.println("LUP decomposition");
            zad5LUP.decomposition(true,zad5PermutationMatrix);
            zad5LUP.printToConsole();
            zad5PermutationMatrix.printToConsole();

            zad5Y=zad5LUP.substitutionForwards(zad5PermutationMatrix.multiply(zad5FreeVector));
            System.out.println("Y");
            zad5Y.printToConsole();

            zad5X=zad5LUP.substitutionBackwards(zad5Y);
            System.out.println("Solution vector X");
            zad5X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


        System.out.println("6. problem");
        Matrix.setThreshold(1e-6);
        Matrix zad6Matrix=new Matrix("6zadMatrix.txt");
        Matrix zad6FreeVector= new Matrix("6zadFreeVector.txt");
        Matrix zad6LU = new Matrix(zad6Matrix);
        Matrix zad6LUP = new Matrix(zad6Matrix);
        Matrix zad6Y;
        Matrix zad6X;
        //zad6LU.printToConsole();
        //zad6LUP.printToConsole();

        try {
            System.out.println("LU decomposition");
            zad6LU.decomposition();
            zad6LU.printToConsole();

            zad6Y=zad6LU.substitutionForwards(zad6FreeVector);
            System.out.println("Y");
            zad6Y.printToConsole();

            zad6X=zad4LU.substitutionBackwards(zad6Y);
            System.out.println("Solution vector X");
            zad6X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        Matrix zad6PermutationMatrix = new Matrix(zad6LUP.getN(),zad6LUP.getM());

        try {
            System.out.println();
            System.out.println("LUP decomposition");
            zad6LUP.decomposition(true,zad6PermutationMatrix);
            zad6LUP.printToConsole();
            zad6PermutationMatrix.printToConsole();

            zad6Y=zad6LUP.substitutionForwards(zad6PermutationMatrix.multiply(zad6FreeVector));
            System.out.println("Y");
            zad6Y.printToConsole();

            zad6X=zad6LUP.substitutionBackwards(zad6Y);
            System.out.println("Solution vector X");
            zad6X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println();
            Matrix.adaptSystem(zad6Matrix,zad6FreeVector);
            zad6Matrix.printToConsole();
            zad6FreeVector.printToConsole();
            zad6LUP = new Matrix(zad6Matrix);
            zad6LUP.decomposition(true,zad6PermutationMatrix);

            zad6LUP.printToConsole();
            zad6PermutationMatrix.printToConsole();

            zad6Y=zad6LUP.substitutionForwards(zad6PermutationMatrix.multiply(zad6FreeVector));
            System.out.println("Y");
            zad6Y.printToConsole();

            zad6X=zad6LUP.substitutionBackwards(zad6Y);
            System.out.println("Solution vector X");
            zad6X.printToConsole();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
