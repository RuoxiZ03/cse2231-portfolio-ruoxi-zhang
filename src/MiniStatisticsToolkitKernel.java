/**
 * Kernel interface for a simple statistics toolkit.
 * @author Ruoxi Zhang
 */

public interface MiniStatisticsToolkitKernel {

    /**
     * Adds a data point to the toolkit.
     * 
     * @param value
     *            The numeric value to add
     * @updates this
     * @ensures this includes value
     */
    void addData(double value);

    /**
     * Removes the most recently added data point.
     * 
     * @updates this
     * @requires this is not empty
     * @ensures last value is removed
     */
    void removeLast();

    /**
     * Returns the number of data points.
     * 
     * @return count
     * @ensures length of data = count
     */
    int length();
}
