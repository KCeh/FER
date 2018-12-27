public class SimplexNelderMead {
    private double threshold;
    private GoalFunction goalFunction;
    private double alpha, beta, gamma, sigma;
    private double movement;

    public SimplexNelderMead(GoalFunction goalFunction) {
        this.goalFunction = goalFunction;
        movement = 1;
        threshold = 1E-6;
        alpha = 1;
        beta = 0.5;
        gamma = 2;
        sigma = 0.5;
    }

    public SimplexNelderMead(GoalFunction goalFunction, double movement, double threshold, double alpha, double beta, double gamma, double sigma) {
        this.goalFunction = goalFunction;
        this.movement = movement;
        this.threshold = threshold;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.sigma = sigma;
    }

    //ucitavanje iz datoteke...


    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double start(Matrix startingPoint, boolean print) throws MatrixSizeIncompatibleException {
        System.out.println("Simplex Nelder Mead with starting point: ");
        System.out.println(startingPoint);
        int n=startingPoint.getN();

        Matrix[] simplexPoints = new Matrix[n+1];
        for(int i=0;i<n;i++){
            simplexPoints[i]=new Matrix(startingPoint);
            double element=simplexPoints[i].getElementAt(i,0)+movement;
            simplexPoints[i].setElementAt(element,i,0);
        }
        simplexPoints[n]=new Matrix(startingPoint);


        int l=0;
        int h=0;
        Matrix Xc,minVal;
        while (true){
            double min=Double.MAX_VALUE;
            double max=Double.MIN_VALUE;
            for(int i=0;i<=n;i++){
                double val=goalFunction.getValueAt(simplexPoints[i]).getElementAt(0,0);
                if(val<min){
                    min=val;
                    l=i;
                }
                if(val>max){
                    max=val;
                    h=i;
                }
            }

            Xc = new Matrix(n,1);
            for(int i=0;i<n;i++){
                double sum=0;
                for(int j=0;j<=n;j++){
                    if(j==h) continue;
                    sum+=simplexPoints[j].getElementAt(i,0);
                }
                sum/=n;
                Xc.setElementAt(sum,i,0);
            }
            /*
            System.out.println("Simplex points");
            for(int i=0;i<=n;i++){
                System.out.println(simplexPoints[i]);
            }*/

            double FXc=goalFunction.getValueAt(Xc).getElementAt(0,0);
            if(print){
                System.out.println("F(Centroid)="+FXc+" , Centroid:");
                System.out.println(Xc);
            }

            Matrix Xr=Xc.multiplyWithScalar(1+alpha).subtract(simplexPoints[h].multiplyWithScalar(alpha));
            double FXr=goalFunction.getValueAt(Xr).getElementAt(0,0);
            double FXl=goalFunction.getValueAt(simplexPoints[l]).getElementAt(0,0);
            double FXh;
            double FXe;
            double FXk;
            Matrix Xe;
            Matrix Xk;

            if(FXr<FXl){
                Xe=Xc.multiplyWithScalar(1-gamma).add(Xr.multiplyWithScalar(gamma));
                FXe=goalFunction.getValueAt(Xe).getElementAt(0,0);
                if(FXe<FXl){
                    simplexPoints[h]=Xe;
                }else {
                    simplexPoints[h]=Xr;
                }
            }else {
                boolean flag=true;
                for(int i=0;i<=n;i++){
                    if(i==h) continue;
                    if(FXr<goalFunction.getValueAt(simplexPoints[i]).getElementAt(0,0)){
                        flag=false;
                        break;
                    }
                }

                if(flag){
                    FXh=goalFunction.getValueAt(simplexPoints[h]).getElementAt(0,0);
                    if(FXr<FXh){
                        simplexPoints[h]=Xr;
                    }
                    Xk=Xc.multiplyWithScalar(1-beta).add(simplexPoints[h].multiplyWithScalar(beta));
                    FXk=goalFunction.getValueAt(Xk).getElementAt(0,0);
                    if(FXk<goalFunction.getValueAt(simplexPoints[h]).getElementAt(0,0)){
                        simplexPoints[h]=Xk;
                    }else {
                        for(int i=0;i<=n;i++){
                            //sigma
                            simplexPoints[i]=simplexPoints[i].add(simplexPoints[l]).multiplyWithScalar(sigma);
                        }
                    }
                }else {
                    simplexPoints[h]=Xr;
                }
            }
            minVal=simplexPoints[l];
            double sum=0;
            for(int i=0;i<=n;i++){
                sum+=Math.pow(goalFunction.getValueAt(simplexPoints[i]).getElementAt(0,0)-FXc,2);
            }
            sum/=n;
            if(Math.sqrt(sum)<=threshold) break;
        }
        System.out.println("Min for x: ");
        System.out.println(minVal);
        double min = goalFunction.getValueAt(minVal).getElementAt(0,0);
        System.out.println("Min = "+min);
        return min;
        /*System.out.println("Simplex points");
        for(int i=0;i<=n;i++){
            System.out.println(simplexPoints[i]);
        }*/
    }
}
