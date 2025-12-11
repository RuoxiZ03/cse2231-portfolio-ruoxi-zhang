import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Secondary implementations for {@code MiniStatisticsToolkit}.
 *
 * All methods here are implemented strictly using
 * kernel operations (e.g., {@code length()}, {@code entries()}) and
 * {@code Standard} methods; no representation access/assumptions.
 *
 * Unless otherwise stated, all methods are observations and
 * do not modify {@code this}.
 *
 * Note: {@link #stddev()} computes the population standard deviation.
 *
 * @author Ruoxi Zhang
 */
public abstract class MiniStatisticsToolkitSecondary implements MiniStatisticsToolkit {

    /**
     * Returns a read-only iterator over the values from oldest to newest.
     *
     * @return fresh iterator
     * @ensures this = #this
     */
    private MiniStatisticsToolkitKernel.Entries it() {
        return this.entries();
    }

    /**
     * Returns a string representation of the sequence in the form
     * {@code [v0, v1, ..., v(n-1)]}, where values appear in insertion order.
     *
     * @return textual representation of the abstract state
     * @ensures this = #this
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        MiniStatisticsToolkitKernel.Entries it = this.it();
        boolean first = true;
        while (it.hasNext()) {
            if (!first) {
                sb.append(", ");
            }
            double v = it.next();
            sb.append(v);
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Reports whether {@code obj} represents the same abstract sequence
     * of values (same length and same value at every position).
     *
     * Two toolkits are equal iff they have equal lengths and for every
     * valid index {@code i}, the {@code i}-th value of {@code this} equals
     * the {@code i}-th value of {@code obj} (using {@code Double.compare}).
     *
     * @param obj object to compare
     * @return whether {@code obj} is an abstract-state equal toolkit
     * @ensures this = #this
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MiniStatisticsToolkit other)) {
            return false;
        }

        if (this.length() != other.length()) {
            return false;
        }

        MiniStatisticsToolkitKernel.Entries a = this.entries();
        MiniStatisticsToolkitKernel.Entries b = other.entries();
        while (a.hasNext() && b.hasNext()) {
            double xa = a.next();
            double xb = b.next();
            if (Double.compare(xa, xb) != 0) {
                return false;
            }
        }
        return !a.hasNext() && !b.hasNext();
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}:
     * equal abstract states have equal hash codes.
     *
     * @return hash code derived from the ordered sequence of values
     * @ensures this = #this
     */
    @Override
    public final int hashCode() {
        int h = 1;
        MiniStatisticsToolkitKernel.Entries it = this.it();
        while (it.hasNext()) {
            double v = it.next();
            h = 31 * h + Double.hashCode(v);
        }
        return h;
    }

    /**
     * Returns the arithmetic mean of all stored values.
     *
     * @return mean value over all elements
     * @requires length() > 0
     * @ensures this = #this
     */
    @Override
    public final double mean() {
        assert this.length() > 0 : "Violation of: length() > 0";

        double sum = 0.0;
        int n = 0;
        MiniStatisticsToolkitKernel.Entries it = this.it();
        while (it.hasNext()) {
            sum += it.next();
            n += 1;
        }
        return sum / n;
    }

    /**
     * Returns the minimum value in the sequence.
     *
     * @return minimum value
     * @requires length() > 0
     * @ensures this = #this
     */
    @Override
    public final double min() {
        assert this.length() > 0 : "Violation of: length() > 0";

        MiniStatisticsToolkitKernel.Entries it = this.it();
        double m = it.next();
        while (it.hasNext()) {
            double v = it.next();
            if (v < m) {
                m = v;
            }
        }
        return m;
    }

    /**
     * Returns the maximum value in the sequence.
     *
     * @return maximum value
     * @requires length() > 0
     * @ensures this = #this
     */
    @Override
    public final double max() {
        assert this.length() > 0 : "Violation of: length() > 0";

        MiniStatisticsToolkitKernel.Entries it = this.it();
        double m = it.next();
        while (it.hasNext()) {
            double v = it.next();
            if (v > m) {
                m = v;
            }
        }
        return m;
    }

    /**
     * Returns the <b>population</b> standard deviation of all values:
     * {@code sqrt( Σ (x - μ)^2 / n )}, where {@code μ = mean()} and
     * {@code n = length()}.
     *
     * @return population standard deviation
     * @requires length() > 0
     * @ensures this = #this
     */
    @Override
    public final double stddev() {
        assert this.length() > 0 : "Violation of: length() > 0";

        final double mu = this.mean();

        double sumSq = 0.0;
        int n = 0;
        MiniStatisticsToolkitKernel.Entries it = this.it();
        while (it.hasNext()) {
            double x = it.next();
            double d = x - mu;
            sumSq += d * d;
            n += 1;
        }
        return Math.sqrt(sumSq / n);
    }

    /**
     * Returns the simple moving average over the most recent {@code k} values.
     *
     * @param k window size
     * @return average of the last {@code k} values
     * @requires 1 <= k && k <= length()
     * @ensures this = #this
     */
    @Override
    public final double movingAverage(int k) {
        assert 1 <= k && k <= this.length()
                : "Violation of: 1 <= k && k <= length()";

        Deque<Double> window = new ArrayDeque<>(k);
        double windowSum = 0.0;

        MiniStatisticsToolkitKernel.Entries it = this.it();
        while (it.hasNext()) {
            double x = it.next();
            window.addLast(x);
            windowSum += x;
            if (window.size() > k) {
                windowSum -= window.removeFirst();
            }
        }
        return windowSum / k;
    }
}