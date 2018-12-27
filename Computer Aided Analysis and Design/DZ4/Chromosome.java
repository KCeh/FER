public interface Chromosome {
    double getFitness();
    void  setFitness(double fitness);
    Matrix getPoint();
    int getSize();
}
