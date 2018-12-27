import java.util.Random;

public class BinaryChromosome implements Chromosome {
    private int size;
    private int chromosomeSize;
    private int[] elements;
    private double fitness;
    private int lowerLimit;
    private int upperLimit;

    public BinaryChromosome(int size, Random random, int precision, int lowerLimit, int upperLimit){
        this.size=size;
        chromosomeSize=(int) (Math.log(Math.floor(1 + (upperLimit - lowerLimit) * Math.pow(10, precision))) / Math.log(2));
        this.upperLimit=upperLimit;
        this.lowerLimit=lowerLimit;
        elements=new int[size];
        for(int i=0;i<size;i++){
            elements[i]=random.nextInt((int)Math.pow(2,chromosomeSize)-1);
        }
    }

    public BinaryChromosome(int size, int chromosomeSize, int[] elements, int lowerLimit, int upperLimit){
        this.size=size;
        this.chromosomeSize=chromosomeSize;
        this.elements=elements;
        this.lowerLimit=lowerLimit;
        this.upperLimit=upperLimit;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getChromosomeSize() {
        return chromosomeSize;
    }

    public void setChromosomeSize(int chromosomeSize) {
        this.chromosomeSize = chromosomeSize;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public void setElementsAt(int index, int el){
        elements[index]=el;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public void setFitness(double fitness) {
        this.fitness=fitness;
    }

    @Override
    public Matrix getPoint() {
        Matrix point = new Matrix(size,1);
        double element;
        for(int i=0;i<size;i++){
            element=elements[i]/(Math.pow(2,chromosomeSize)-1)*(upperLimit-lowerLimit)+lowerLimit;
            point.setElementAt(element,i,0);
        }
        return point;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[ ");
        double element;
        for (int i=0; i<size;i++) {
            element=elements[i]/(Math.pow(2,chromosomeSize)-1)*(upperLimit-lowerLimit)+lowerLimit;
            sb.append(element+ " ");
        }
        sb.append("]T");
        return sb.toString();
    }

    public int[] getBinary(int index) {
        int[] sol = new int[chromosomeSize];
        String binary = Integer.toBinaryString(elements[index]);
        int diff = chromosomeSize - binary.length();
        if (diff > 0) {
            for (int i = 0; i < diff; i++) {
                binary = "0" + binary;
            }
        }
        for (int j = 0; j < binary.length(); j++) {
            int tmp = Integer.parseInt("" + binary.charAt(j));
            sol[j] = tmp;
        }
        return sol;
    }

}
