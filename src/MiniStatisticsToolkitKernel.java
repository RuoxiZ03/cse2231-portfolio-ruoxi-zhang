import components.standard.Standard;

/**
 * Kernel interface for a one-dimensional numeric series (double values).
 * Provides the minimal operations needed to model the data type.
 *
 * <p>Model: a finite sequence of {@code double} values in insertion order.</p>
 * 
 * <p>Note: This kernel exposes only minimal capabilities; statistics and other
 * utilities are layered in the enhanced interface.</p>
 *
 * author Ruoxi Zhang
 */
public interface MiniStatisticsToolkitKernel extends Standard<MiniStatisticsToolkit>{

    /**
     * Read-only forward iterator over the values in insertion order.
     *
     * This minimal iterator exists so secondary operations (mean, min, etc.)
     * can be layered on top of the kernel without exposing representation.
     * Iteration must not modify the enclosing {@code this}.
     */
    interface Entries {
        /**
         * Reports whether another value is available.
         *
         * @return whether there is a next value
         * @ensures this = #this
         * @ensures result = (there exists an unvisited element)
         */
        boolean hasNext();

        /**
         * Returns the next value.
         *
         * @return next value in sequence
         * @requires hasNext()
         * @ensures this = #this
         */
        double next();
    }

    /**
     * Appends a new data value at the logical end of the series.
     *
     * @param value the numeric value to append
     * @updates this
     * @ensures <pre>
     *   #this is a prefix of this  and
     *   length(this) = length(#this) + 1  and
     *   the last entry of this equals value
     * </pre>
     */
    void addData(double value);

    /**
     * Removes the most recently added value.
     *
     * @updates this
     * @requires length() > 0
     * @ensures <pre>
     *   length(this) = length(#this) - 1  and
     *   the removed value is the last value of #this
     * </pre>
     */
    void removeLast();

    /**
     * Reports the number of stored values.
     *
     * @return the number of values
     * @ensures this = #this
     * @ensures result = length(this)
     */
    int length();

    /**
     * Returns a read-only iterator over the values, oldest to newest.
     *
     * @return a read-only iterator over values
     * @ensures this = #this
     */
    Entries entries();
}