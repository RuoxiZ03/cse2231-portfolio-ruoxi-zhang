import java.util.Scanner;

/**
 * Command-line demo for {@code MiniStatisticsToolkit}.
 *
 * <p>This program reads a single line of comma- or space-separated
 * {@code double} values from standard input, loads them into a
 * {@link MiniStatisticsToolkit1L} instance, and then prints a set of
 * summary statistics (mean, min, max, standard deviation, and a moving
 * average over the last three values).</p>
 *
 * <p>This class serves as one of the "use cases" demonstrating how the
 * component can be used in a simple terminal-style application.</p>
 *
 * @author Ruoxi Zhang
 */
public final class MiniStatisticsToolkitCliDemo {

    /**
     * Private constructor to prevent instantiation.
     */
    private MiniStatisticsToolkitCliDemo() {
        // no instances
    }

    /**
     * Entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter values (comma or space separated): ");
        String line = in.nextLine();

        String[] parts = line.split("[,\\s]+");

        MiniStatisticsToolkit toolkit = new MiniStatisticsToolkit1L();
        for (String p : parts) {
            if (!p.isEmpty()) {
                toolkit.addData(Double.parseDouble(p));
            }
        }

        System.out.println("Sequence: " + toolkit);
        System.out.println("Mean: " + toolkit.mean());
        System.out.println("Min: " + toolkit.min());
        System.out.println("Max: " + toolkit.max());
        System.out.println("StdDev (pop.): " + toolkit.stddev());
        System.out.println(
                "Moving average (last 3): " + toolkit.movingAverage(3));

        in.close();
    }
}