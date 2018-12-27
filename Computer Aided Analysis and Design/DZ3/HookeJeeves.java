public class HookeJeeves {
    private Matrix threshold=null;
    private double defaultThreshold=1E-6;
    private Matrix dx=null;
    private double defaultDx=0.5;
    private GoalFunction goalFunction;

    public HookeJeeves(GoalFunction goalFunction, Matrix threshold, Matrix dx){
        this.goalFunction=goalFunction;
        this.threshold=threshold;
        this.dx=dx;
    }

    public HookeJeeves(GoalFunction goalFunction){
        this.goalFunction=goalFunction;
    }
    //ucitavanje iz datoteke...

    public double start(Matrix xB, boolean print) throws MatrixSizeIncompatibleException {
        //vrijednosti se mijenjaju dx
        defaultDx=0.5;
        System.out.println("Hooke-Jeeves with starting point: ");
        System.out.println(xB);
        Matrix xP=new Matrix(xB);
        Matrix xN;
        double fxN,fxP,fxB;

        while (true){
            xN=explore(xP);
            fxN=goalFunction.getValueAt(xN).getElementAt(0,0);
            fxB=goalFunction.getValueAt(xB).getElementAt(0,0);
            fxP=goalFunction.getValueAt(xP).getElementAt(0,0);
            /*
            System.out.println("XB: "+ xB);
            System.out.println("F(XB): "+ fxB);
            */
            if(print){
                System.out.println("XB: "+ xB+"XP: "+xP+"XN: "+xN);
                System.out.println("F(XB): "+ fxB+" F(XP): "+fxP+" F(XN): "+fxN);
            }
            if(fxN<fxB){
                xP=xN.multiplyWithScalar(2).subtract(xB);
                xB=new Matrix(xN);
            }else {
                if(dx!=null){
                  dx=dx.multiplyWithScalar(1/2);
                }else {
                    defaultDx/=2;
                }
                xP=new Matrix(xB);
            }

            if(dx!=null){
                boolean flag=true;
                for(int i=0;i<dx.getN();i++){
                    if(dx.getElementAt(i,0)>threshold.getElementAt(i,0)) flag=false;
                }
                if(flag) break;
            }else {
                if(defaultDx<defaultThreshold) break;
            }

        }
        System.out.println("Min for x: ");
        System.out.println(xB);
        double min=goalFunction.getValueAt(xB).getElementAt(0,0);
        System.out.println("Min = "+min);
        return min;
    }

    public Matrix startForPoint(Matrix xB) throws MatrixSizeIncompatibleException {
        defaultDx=0.5;
        Matrix xP=new Matrix(xB);
        Matrix xN;
        double fxN,fxP,fxB;

        while (true){
            xN=explore(xP);
            fxN=goalFunction.getValueAt(xN).getElementAt(0,0);
            fxB=goalFunction.getValueAt(xB).getElementAt(0,0);
            fxP=goalFunction.getValueAt(xP).getElementAt(0,0);

            if(fxN<fxB){
                xP=xN.multiplyWithScalar(2).subtract(xB);
                xB=new Matrix(xN);
            }else {
                if(dx!=null){
                    dx=dx.multiplyWithScalar(1/2);
                }else {
                    defaultDx/=2;
                }
                xP=new Matrix(xB);
            }

            if(dx!=null){
                boolean flag=true;
                for(int i=0;i<dx.getN();i++){
                    if(dx.getElementAt(i,0)>threshold.getElementAt(i,0)) flag=false;
                }
                if(flag) break;
            }else {
                if(defaultDx<defaultThreshold) break;
            }

        }
        return xB;
    }

    private Matrix explore(Matrix xP) {
        Matrix x=new Matrix(xP);
        for(int i=0, n=xP.getN();i<n;i++){
            double p = goalFunction.getValueAt(x).getElementAt(0,0);
            double element=x.getElementAt(i,0);
            if(dx!=null){
                element+=dx.getElementAt(i,0);
            }else {
                element+=defaultDx;
            }
            x.setElementAt(element,i,0);
            double m=goalFunction.getValueAt(x).getElementAt(0,0);
            if(m>p){
                if(dx!=null){
                    element-=2*dx.getElementAt(i,0);
                }else {
                    element-=2*defaultDx;
                }
                x.setElementAt(element,i,0);
                m=goalFunction.getValueAt(x).getElementAt(0,0);
                if(m>p){
                    if(dx!=null){
                        element+=dx.getElementAt(i,0);
                    }else {
                        element+=defaultDx;
                    }
                    x.setElementAt(element,i,0);
                }
            }
        }
        return x;
    }


}
