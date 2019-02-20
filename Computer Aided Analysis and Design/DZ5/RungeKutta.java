import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RungeKutta implements NumericalIntegration{
    private final double threshold =1E-6;
    @Override
    public void integrate(Matrix A, Matrix x, double stepT, double Tmax, int iterationForPrint, boolean toFile, String fileName) throws MatrixSizeIncompatibleException, ZeroElementException, IOException {
        System.out.println("Numerical integration with Runge-Kutta method of 4th order");
        System.out.println("Integration step "+stepT);
        System.out.println("Integration interval [0, "+Tmax+"]");
        System.out.println("Starting values of x:");
        System.out.println(x);
        double T = 0;
        Matrix m1,m2,m3,m4;

        Map<Double, Double> x1Map= new HashMap<>();
        Map<Double, Double> x2Map= new HashMap<>();

        System.out.println("\tT\t\tx1\t\tx2");
        System.out.println(T+"\t\t"+x.getElementAt(0,0)+"\t\t"+x.getElementAt(1,0));
        x1Map.put(T,x.getElementAt(0,0));
        x2Map.put(T,x.getElementAt(1,0));

        int counter=0;
        Matrix Xk;
        while(Math.abs(T-Tmax)> threshold){
            counter++;
            T+=stepT;

            m1=A.multiply(x);
            m2=A.multiply(x.add(m1.multiplyWithScalar(stepT*0.5)));
            m3=A.multiply(x.add(m2.multiplyWithScalar(stepT*0.5)));
            m4=A.multiply(x.add(m3.multiplyWithScalar(stepT)));
            Xk=x.add((m1.add(m2.multiplyWithScalar(2)).add(m3.multiplyWithScalar(2)).add(m4)).multiplyWithScalar(stepT*1./6));

            if(counter==iterationForPrint){
                counter=0;
                System.out.println(T+"\t\t"+Xk.getElementAt(0,0)+"\t\t"+Xk.getElementAt(1,0));
                x1Map.put(T,Xk.getElementAt(0,0));
                x2Map.put(T,Xk.getElementAt(1,0));
            }

            x=new Matrix(Xk);
        }

        if(toFile){
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"))) {
                for(Map.Entry<Double, Double> entry:x1Map.entrySet()){
                    writer.write(entry.getKey()+", "+entry.getValue()+"\n");
                }
                writer.write("\n");
                for(Map.Entry<Double, Double> entry:x2Map.entrySet()){
                    writer.write(entry.getKey()+", "+entry.getValue()+"\n");
                }
            }
        }
    }
}
