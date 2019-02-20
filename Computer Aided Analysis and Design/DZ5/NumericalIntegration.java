import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface NumericalIntegration {
    void integrate(Matrix A, Matrix x, double stepT, double Tmax, int iterationForPrint, boolean toFile, String fileName) throws MatrixSizeIncompatibleException, ZeroElementException, IOException;
}
