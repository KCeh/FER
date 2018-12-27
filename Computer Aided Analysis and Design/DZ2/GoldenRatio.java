import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GoldenRatio {
    private double threshold = 1E-6;
    private double a, b;
    private double c, d;
    private static final double k = 0.5 * (Math.sqrt(5) - 1);
    private GoalFunction goalFunction;

    public GoldenRatio(double a, double b, double e, GoalFunction goalFunction) {
        this.a = a;
        this.b = b;
        this.threshold = e;
        this.goalFunction = goalFunction;
    }

    public GoldenRatio(double a, double b, GoalFunction goalFunction) {
        this(a, b, 1E-6, goalFunction);
    }

    public GoldenRatio(double startingPoint, GoalFunction goalFunction) throws MatrixSizeIncompatibleException {
        this.goalFunction =goalFunction;
        findUnimodalInterval(startingPoint);
    }

    public GoldenRatio(Matrix staringPoint,GoalFunction goalFunction, int i) throws MatrixSizeIncompatibleException {
        //tu negdje greska?
        this.goalFunction=goalFunction;
        /*double start=0;
        for(int i=0, n=staringPoint.getN();i<n;i++){
            start=staringPoint.getElementAt(i,0);
            if(start!=0){
                break;
            }
        }*/
        findUnimodalInterval(staringPoint,i);
    }

    //testrati s formatom
    public GoldenRatio(String fileName, GoalFunction goalFunction) throws MatrixSizeIncompatibleException {
        this.goalFunction = goalFunction;
        readFromTextFile(fileName);
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public boolean readFromTextFile(String fileName) throws MatrixSizeIncompatibleException {
        ArrayList<Double> list = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine().trim();
            String[] tmp = line.split("\\s+");

            for (int i = 0; i < tmp.length; i++) {
                list.add(Double.parseDouble(tmp[i]));
            }

        } catch (IOException ex) {
            System.err.println("Error while reading file: " + fileName);
            return false;
        }

        if (list.size() == 2) {
            threshold = list.get(0);
            findUnimodalInterval(list.get(1));
        } else {
            threshold = list.get(0);
            a = list.get(1);
            b = list.get(2);
        }

        return true;
    }

    private void findUnimodalInterval(double startingPoint) {
        double h = 1;
        double l = startingPoint - h;
        double r = startingPoint + h;
        double m = startingPoint;
        double fl, fm, fr;
        int step = 1;

        fm = goalFunction.getValueAt(startingPoint);
        fl = goalFunction.getValueAt(l);
        fr = goalFunction.getValueAt(r);

        if (fm < fr && fm < fl) {
            return;
        } else if (fm > fr) {
            do {
                l = m;
                m = r;
                fm = fr;
                r = startingPoint + h * (step *= 2);
                fr = goalFunction.getValueAt(r);
            } while (fm > fr);
        } else {
            do {
                r = m;
                m = l;
                fm = fl;
                l = startingPoint - h * (step *= 2);
                fl = goalFunction.getValueAt(l);
            } while (fm > fl);
        }
        a = l;
        b = r;

        System.out.println("Unimodal interval = [" + a + " " + b + "]");
    }

    private void findUnimodalInterval(Matrix startingPoint,int j) throws MatrixSizeIncompatibleException {
        Matrix h=new Matrix(startingPoint.getN(),startingPoint.getM());
        a=startingPoint.getElementAt(j,0);
        h.setElementAt(1,j,0);
        //double h = 1;
        Matrix l = startingPoint.subtract(h);
        Matrix r = startingPoint.add(h);
        Matrix m = new Matrix(startingPoint);
        double fl, fm, fr;
        int step = 1;

        fm = goalFunction.getValueAt(startingPoint).getElementAt(0,0);
        fl = goalFunction.getValueAt(l).getElementAt(0,0);
        fr = goalFunction.getValueAt(r).getElementAt(0,0);

        if (fm < fr && fm < fl) {
            return;
        } else if (fm > fr) {
            do {
                l = new Matrix(m);
                m = new Matrix(r);
                fm = fr;
                r = startingPoint.add(h.multiplyWithScalar(step *= 2));
                fr = goalFunction.getValueAt(r).getElementAt(0,0);
            } while (fm > fr);
        } else {
            do {
                r = new Matrix(m);
                m = new Matrix(l);
                fm = fl;
                l = startingPoint.subtract(h.multiplyWithScalar(step *= 2));
                fl = goalFunction.getValueAt(l).getElementAt(0,0);
            } while (fm > fl);
        }
        goalFunction.setInputVector(startingPoint,j);
        a=l.getElementAt(j,0);
        b=r.getElementAt(j,0);

        System.out.println("Unimodal interval = [" + a + " " + b + "]");
    }

    public double start(boolean print) {
        if (print)
            System.out.println("Golden ratio:");
        c = b - k * (b - a);
        d = a + k * (b - a);
        double fa = goalFunction.getValueAt(a);
        double fb = goalFunction.getValueAt(b);
        double fc = goalFunction.getValueAt(c);
        double fd = goalFunction.getValueAt(d);
        while (Math.abs(b - a) > threshold) {
            if(print){
                System.out.println("a: "+a+" c:"+c+" d:"+d+" b:"+b);
                System.out.println("fa: "+fa+" fc:"+fc+" fd:"+fd+" fb:"+fb);
            }
            if (fc < fd) {
                b = d;
                d = c;
                c = b - k * (b - a);
                fb = fd;
                fd = fc;
                fc = goalFunction.getValueAt(c);
            } else {
                a = c;
                c = d;
                d = a + k * (b - a);
                fa = fc;
                fc = fd;
                fd = goalFunction.getValueAt(d);
            }
        }
        System.out.println("Golden ration solution = [" + a + " " + b + "]");
        if(print){
            System.out.println("Min for x: "+(a + b) / 2);
            System.out.println("Min= "+goalFunction.getValueAt((a + b) / 2));
        }
        return (a + b) / 2;
    }
}
