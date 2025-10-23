/**
 * Enhanced interface for a one-dimensional numeric series.
 * Adds common statistics operations layered on the kernel.
 *
 * All methods must be implementable using only the kernel operations
 * and {@code Standard} methods.
 *
 * Unless otherwise stated, methods do not modify {@code this}.
 *
 * @author Ruoxi Zhang
 */
public interface MiniStatisticsToolkit extends MiniStatisticsToolkitKernel {

    /**
     * Returns the arithmetic mean of all values.
     *
     * @return mean value over all stored values
     * @requires length() > 0
     * @ensures this = #this
     */
    double mean();

    /**
     * Returns the minimum value.
     *
     * @return minimum value among all stored values
     * @requires length() > 0
     * @ensures this = #this
     */
    double min();

    /**
     * Returns the maximum value.
     *
     * @return maximum value among all stored values
     * @requires length() > 0
     * @ensures this = #this
     */
    double max();

    /**
     * Returns the (population) standard deviation of all values.
     *
     * @return population standard deviation of all stored values
     * @requires length() > 0
     * @ensures this = #this
     */
    double stddev();

    /**
     * Returns the simple moving average over the most recent {@code k} values.
     *
     * @param k window size
     * @return mean of the last {@code k} values
     * @requires 1 <= k && k <= length()
     * @ensures this = #this
     */
    double movingAverage(int k);
}
