import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Test class for MiniStatisticsToolkit.
 * Shows that the component behaves as expected.
 * 
 * @author Ruoxi Zhang
 */
public final class MiniStatisticsToolkitTest {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private MiniStatisticsToolkitTest() {
    }

    /**
     * Main method for testing.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        MiniStatisticsToolkit stats = new MiniStatisticsToolkit();
        stats.addData(10.0);
        stats.addData(20.0);
        stats.addData(30.0);

        out.println("Number of data points: " + stats.length());
        out.println("Mean value: " + stats.mean());

        stats.removeLast();
        out.println("After removing last value:");
        out.println("Number of data points: " + stats.length());
        out.println("Mean value: " + stats.mean());

        out.close();
    }
}

