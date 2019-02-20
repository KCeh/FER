import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private static double threshold = 1E-6;
    private int n, m;
    private double[][] matrix;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        matrix = new double[n][m];
    }

    public Matrix(double[][] matrix) {
        n = matrix.length;
        m = matrix[0].length;
        this.matrix = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

    }

    public Matrix(Matrix a) {
        this(a.matrix);
    }

    public static double getThreshold() {
        return threshold;
    }

    public static void setThreshold(double threshold) {
        Matrix.threshold = threshold;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Matrix(String fileName) {
        readFromTextFile(fileName);
    }

    public void assign(Matrix matrix) {
        this.n = matrix.n;
        this.m = matrix.m;
        this.matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.matrix[i][j] = matrix.matrix[i][j];
            }
        }

    }

    public boolean readFromTextFile(String fileName) {
        ArrayList<Double> list = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine().trim();
            String[] tmp = line.split("\\s+");
            m = tmp.length;

            while (true) {
                for (int i = 0; i < tmp.length; i++) {
                    list.add(Double.parseDouble(tmp[i]));
                }
                line = bufferedReader.readLine();
                if (line == null) break;
                tmp = line.split("\\s+");
            }
        } catch (IOException ex) {
            System.err.println("Error while reading file: " + fileName);
            return false;
        }

        n = list.size() / m;

        matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = list.get(j + i * m);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                stringBuilder.append(matrix[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void printToConsole() {
        System.out.println(toString());
    }

    public void printToFile(String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.write(toString());
        } catch (Exception ex) {
            System.err.println("Error while printing matrix to file!");
        }
    }

    public double getElementAt(int n, int m) {
        return matrix[n][m];
    }

    public void setElementAt(double element, int n, int m) {
        matrix[n][m] = element;
    }

    public static boolean isZeroMatrix(Matrix a) {
        for (int i = 0; i < a.n; i++) {
            for (int j = 0; j < a.m; j++) {
                if (a.matrix[i][j] != 0) return false;
            }
        }
        return true;
    }


    public Matrix add(Matrix b) throws MatrixSizeIncompatibleException {
        Matrix c = checkSizesForArithmetic(this, b);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                c.matrix[i][j] = this.matrix[i][j] + b.matrix[i][j];
            }
        }

        return c;
    }

    public Matrix subtract(Matrix b) throws MatrixSizeIncompatibleException {
        Matrix c = checkSizesForArithmetic(this, b);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                c.matrix[i][j] = this.matrix[i][j] - b.matrix[i][j];
            }
        }

        return c;
    }

    public static Matrix checkSizesForArithmetic(Matrix a, Matrix b) throws MatrixSizeIncompatibleException {
        Matrix c;
        if (!isZeroMatrix(a)) {
            c = new Matrix(a.n, a.m);
        } else if (!isZeroMatrix(b)) {
            return new Matrix(b.matrix);
        } else {
            return new Matrix(1, 1);
        }

        if (a.n != b.n || a.m != b.m) throw new MatrixSizeIncompatibleException("Sizes of matrix are not same!");
        return c;
    }

    public Matrix multiply(Matrix b) throws MatrixSizeIncompatibleException {
        Matrix c;
        if (this.m != b.n) throw new MatrixSizeIncompatibleException("Can't multiply matrix because of sizes!");
        c = new Matrix(this.n, b.m);

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < b.m; j++) {
                c.matrix[i][j] = 0;
                for (int k = 0; k < this.m; k++) {
                    c.matrix[i][j] += this.matrix[i][k] * b.matrix[k][j];
                }
            }
        }
        return c;
    }

    public Matrix transpose() {
        Matrix b = new Matrix(m, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                b.matrix[j][i] = matrix[i][j];
            }
        }
        return b;
    }

    public void addUnary(Matrix b) throws MatrixSizeIncompatibleException {
        if (n != b.n || m != b.m) throw new MatrixSizeIncompatibleException("Sizes of matrix are not same!");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] += b.matrix[i][j];
            }
        }

    }

    public void subtractUnary(Matrix b) throws MatrixSizeIncompatibleException {
        if (n != b.n || m != b.m) throw new MatrixSizeIncompatibleException("Sizes of matrix are not same!");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] -= b.matrix[i][j];
            }
        }
    }

    public Matrix multiplyWithScalar(double scalar) {
        Matrix b = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                b.matrix[i][j] = this.matrix[i][j] * scalar;
            }
        }
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Matrix)) return false;

        Matrix b = (Matrix) o;
        boolean result = n == b.n && m == b.m;
        if (!result) return false;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                if (Math.abs(this.matrix[i][j] - b.matrix[i][j]) >= threshold) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + n;
        result = 31 * result + m;
        result = 31 * result + Arrays.hashCode(matrix);
        return result;
    }

    public Matrix substitutionForwards(Matrix vector) throws MatrixSizeIncompatibleException {
        checkSubstitutionConditions(vector);
        Matrix solution = new Matrix(vector.n, vector.m);

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += this.getElementAt(i, j) * solution.getElementAt(j, 0);
            }
            double tmp = vector.getElementAt(i, 0) - sum;
            solution.setElementAt(tmp, i, 0);
        }

        return solution;
    }

    public static void adaptSystem(Matrix system, Matrix freeVector) throws MatrixSizeIncompatibleException {
        if (system.n != freeVector.n || freeVector.m != 1)
            throw new MatrixSizeIncompatibleException("System matrix or free vector size is wrong!");
        boolean flagDivide;
        boolean flagMultiply;
        for (int i = 0; i < system.n; i++) {
            flagDivide = true;
            flagMultiply = true;
            for (int j = 0; j < system.m; j++) {
                if (system.matrix[i][j] % (1E9) != 0) {
                    flagDivide = false;
                }

                if (system.matrix[i][j] > (1E-8)) {
                    flagMultiply = false;
                }

            }


            if (flagDivide && freeVector.matrix[i][0] % (1E9) == 0) {
                for (int j = 0; j < system.m; j++)
                    system.matrix[i][j] = system.matrix[i][j] / (1E9);
                freeVector.matrix[i][0] = freeVector.matrix[i][0] / (1E9);
            }

            if (flagMultiply && freeVector.matrix[i][0] < (1E-8)) {
                for (int j = 0; j < system.m; j++)
                    system.matrix[i][j] = system.matrix[i][j] * (1E8);
                freeVector.matrix[i][0] = freeVector.matrix[i][0] * (1E8);
            }
        }
    }

    public Matrix substitutionBackwards(Matrix vector) throws MatrixSizeIncompatibleException, ZeroElementException {
        checkSubstitutionConditions(vector);
        Matrix solution = new Matrix(vector.n, vector.m);

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += this.getElementAt(i, j) * solution.getElementAt(j, 0);
            }
            double tmp = vector.getElementAt(i, 0) - sum;
            if (Math.abs(this.getElementAt(i, i) - 0) < threshold)
                throw new ZeroElementException("Trying to divide with element too close to 0 in substitution backwards");
            solution.setElementAt(tmp / this.getElementAt(i, i), i, 0);
        }

        return solution;
    }

    public void checkSubstitutionConditions(Matrix vector) throws MatrixSizeIncompatibleException {
        if (vector.n != this.n)
            throw new MatrixSizeIncompatibleException("Vector must have same length as matrix");
        /*if (this.n != this.m)
            throw new MatrixSizeIncompatibleException("Matrix must be squared");*/
    }

    public void decomposition(boolean PFlag, Matrix permutationMatrix) throws MatrixSizeIncompatibleException, ZeroElementException {
        if (this.n != this.m) throw new MatrixSizeIncompatibleException("Matrix must be squared");
        if (PFlag) {
            if (permutationMatrix == null || permutationMatrix.n != this.n || permutationMatrix.m != this.m)
                throw new MatrixSizeIncompatibleException("Permutation matrix not compatible");
            for (int i = 0; i < permutationMatrix.n; i++) {
                for (int j = 0; j < permutationMatrix.m; j++) {
                    if (i == j)
                        permutationMatrix.setElementAt(1, i, j);
                    else
                        permutationMatrix.setElementAt(0, i, j);
                }

            }
        }

        for (int i = 0; i < n - 1; i++) {
            if (PFlag) {
                double max = 0.0;
                int indexMax = i;
                double abs;

                for (int k = i; k < this.n; k++) {
                    abs = Math.abs(this.getElementAt(k, i));
                    if (abs > max) {
                        max = abs;
                        indexMax = k;
                    }
                }

                if (max < threshold)
                    throw new ZeroElementException("Pivot element too close to 0, matrix is degenerated");

                swapRows(permutationMatrix.matrix, i, indexMax);
                swapRows(this.matrix, i, indexMax);
            }
            double pivot = this.getElementAt(i, i);
            if (Math.abs(pivot - 0) < threshold)
                throw new ZeroElementException("Pivot element too close to 0, matrix is degenerated");
            for (int j = i + 1; j < n; j++) {


                double tmp = this.getElementAt(j, i) / pivot;
                this.setElementAt(tmp, j, i);
                for (int k = i + 1; k < n; k++) {
                    tmp = this.getElementAt(j, k) - this.getElementAt(j, i) * this.getElementAt(i, k);
                    this.setElementAt(tmp, j, k);
                }
            }
        }
    }

    public void decomposition() throws ZeroElementException, MatrixSizeIncompatibleException {
        decomposition(false, null);
    }

    public static void swapRows(double array[][], int rowA, int rowB) {
        double tmpRow[] = array[rowA];
        array[rowA] = array[rowB];
        array[rowB] = tmpRow;
    }

    public static boolean calculateDifferenceWithE(Matrix a, Matrix b, Matrix E, double epsilon) throws MatrixSizeIncompatibleException {
        int n = a.getN();
        if (E == null) {
            if (n != b.getN())
                throw new MatrixSizeIncompatibleException("Matrix are not same dimensions");
            for (int i = 0; i < n; i++) {
                if (Math.abs(a.getElementAt(i, 0) - b.getElementAt(i, 0)) > epsilon) return false;
            }
        } else {
            if (n != b.getN() || n != E.getN())
                throw new MatrixSizeIncompatibleException("Matrix are not same dimensions");
            for (int i = 0; i < n; i++)
                if (Math.abs(a.getElementAt(i, 0) - b.getElementAt(i, 0)) > E.getElementAt(i, 0)) return false;
        }
        return true;
    }

    public static double calculateNorm(Matrix point) {
        double norm = 0;
        for (int i = 0, n = point.getN(); i < n; i++) {
            norm += Math.pow(point.getElementAt(i, 0), 2);
        }
        norm = Math.sqrt(norm);
        return norm;
    }

    public static Matrix getInverse(Matrix A) throws MatrixSizeIncompatibleException, ZeroElementException {
        int n = A.n;
        if (n != A.m) throw new MatrixSizeIncompatibleException("Matrix must be squared for inverse");
        Matrix permutationMatrix = new Matrix(A.n, A.n);
        A.decomposition(true, permutationMatrix);
        Matrix y = new Matrix(n, n);
        Matrix x = new Matrix(n, n);
        Matrix E = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            E.setElementAt(1, i, i);
        }

        Matrix b = permutationMatrix.multiply(E);
        for (int j = 0; j < n; j++) {
            Matrix bi = getColumn(b, j);
            Matrix yi = A.substitutionForwards(bi);
            for (int i = 0; i < n; i++) {
                y.matrix[i][j] = yi.matrix[i][0];
            }

        }

        for (int j = 0; j < n; j++) {
            Matrix yi = getColumn(y, j);
            Matrix xi = A.substitutionBackwards(yi);
            for (int i = 0; i < n; i++) {
                x.matrix[i][j] = xi.matrix[i][0];
            }
        }

        return x;
    }

    public static Matrix getColumn(Matrix A, int j) {
        int n = A.getN();
        Matrix result = new Matrix(n, 1);
        for (int i = 0; i < n; i++) {
            result.setElementAt(A.getElementAt(i, j), i, 0);
        }
        return result;
    }

    public static Matrix makeIdentityMatrix(int n){
        Matrix matrix = new Matrix(n,n);
        for(int i=0;i<n;i++){
            matrix.matrix[i][i]=1;
        }
        return matrix;
    }
}
