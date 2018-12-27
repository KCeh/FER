import java.util.Random;

public class FloatingPointChromosome implements Chromosome{
    private int size;
    private double fitness;
    private double[] elements;
    private int lowerLimit;
    private int upperLimit;

    public FloatingPointChromosome(int size, Random random, int lowerLimit, int upperLimit){
        this.size=size;
        elements=new double[size];
        this.lowerLimit=lowerLimit;
        this.upperLimit=upperLimit;
        for(int i=0;i<size;i++){
            elements[i]=random.nextDouble()*(upperLimit-lowerLimit)+lowerLimit;
        }
    }

    public FloatingPointChromosome(int size, int lowerLimit, int upperLimit, double[] elements){
        this.size=size;
        this.lowerLimit=lowerLimit;
        this.upperLimit=upperLimit;
        this.elements=elements;
    }

    public double getElementAt(int index){
        return elements[index];
    }

    public void setElementAt(int index, double value){
        elements[index]=value;
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
        Matrix point=new Matrix(size,1);
        for(int i=0;i<size;i++){
            point.setElementAt(elements[i],i,0);
        }
        return point;
    }

    @Override
    public int getSize() {
        return  size;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[ ");
        for (int i=0;i<size;i++) {
            sb.append(elements[i] + " ");
        }
        sb.append("]T");
        return sb.toString();
    }
}
