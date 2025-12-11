import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for the secondary/enhanced methods of MiniStatisticsToolkit.
 *
 * These tests exercise mean, min, max, stddev, movingAverage, toString,
 * equals, and hashCode, using the concrete implementation
 * {@link MiniStatisticsToolkit1L}.
 *
 * @author Ruoxi Zhang
 */
public class MiniStatisticsToolkitTest {

    /**
     * Helper: creates a toolkit from the given values in order.
     *
     * @param values values to insert
     * @return a new MiniStatisticsToolkit containing {@code values}
     */
    private MiniStatisticsToolkit createFromArgs(double... values) {
        MiniStatisticsToolkit t = new MiniStatisticsToolkit1L();
        for (double v : values) {
            t.addData(v);
        }
        return t;
    }

    /**
     * mean on a simple data set.
     */
    @Test
    public void testMeanSimple() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0, 3.0, 4.0);
        double mean = t.mean();
        assertEquals(2.5, mean, 1e-9);
        // mean should not modify this
        assertEquals(this.createFromArgs(1.0, 2.0, 3.0, 4.0), t);
    }

    /**
     * min on a data set with negative values.
     */
    @Test
    public void testMinWithNegatives() {
        MiniStatisticsToolkit t = this.createFromArgs(3.0, -1.0, 7.0, 0.5);
        double m = t.min();
        assertEquals(-1.0, m, 1e-9);
    }

    /**
     * max on a data set.
     */
    @Test
    public void testMaxSimple() {
        MiniStatisticsToolkit t = this.createFromArgs(3.0, -1.0, 7.0, 0.5);
        double m = t.max();
        assertEquals(7.0, m, 1e-9);
    }

    /**
     * stddev (population) for [1, 2, 3, 4].
     * mean = 2.5, variance = 1.25, stddev = sqrt(1.25).
     */
    @Test
    public void testStddevSimple() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0, 3.0, 4.0);
        double sd = t.stddev();
        assertEquals(Math.sqrt(1.25), sd, 1e-9);
    }

    /**
     * movingAverage with window size equal to length (just mean).
     */
    @Test
    public void testMovingAverageFullWindow() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0, 3.0, 4.0);
        double ma = t.movingAverage(4);
        assertEquals(2.5, ma, 1e-9);
    }

    /**
     * movingAverage with window size 2 (last two values).
     */
    @Test
    public void testMovingAverageWindowTwo() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0, 3.0, 4.0);
        double ma = t.movingAverage(2);
        // last two values: 3.0 and 4.0
        assertEquals(3.5, ma, 1e-9);
    }

    /**
     * toString on empty toolkit.
     */
    @Test
    public void testToStringEmpty() {
        MiniStatisticsToolkit t = new MiniStatisticsToolkit1L();
        assertEquals("[]", t.toString());
    }

    /**
     * toString on non-empty toolkit.
     */
    @Test
    public void testToStringNonEmpty() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.5, -3.0);
        String s = t.toString();
        assertEquals("[1.0, 2.5, -3.0]", s);
    }

    /**
     * equals: two toolkits with same values should be equal.
     */
    @Test
    public void testEqualsSameValues() {
        MiniStatisticsToolkit a = this.createFromArgs(1.0, 2.0, 3.0);
        MiniStatisticsToolkit b = this.createFromArgs(1.0, 2.0, 3.0);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    /**
     * equals: different length should not be equal.
     */
    @Test
    public void testEqualsDifferentLength() {
        MiniStatisticsToolkit a = this.createFromArgs(1.0, 2.0);
        MiniStatisticsToolkit b = this.createFromArgs(1.0, 2.0, 3.0);
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
    }

    /**
     * equals: same length but different values.
     */
    @Test
    public void testEqualsDifferentValues() {
        MiniStatisticsToolkit a = this.createFromArgs(1.0, 2.0, 3.0);
        MiniStatisticsToolkit b = this.createFromArgs(1.0, 2.0, 4.0);
        assertFalse(a.equals(b));
    }

    /**
     * equals with non-toolkit object.
     */
    @Test
    public void testEqualsDifferentType() {
        MiniStatisticsToolkit a = this.createFromArgs(1.0, 2.0, 3.0);
        assertFalse(a.equals("not a toolkit"));
    }

    /**
     * hashCode: equal toolkits must have same hashCode.
     */
    @Test
    public void testHashCodeConsistentWithEquals() {
        MiniStatisticsToolkit a = this.createFromArgs(1.0, 2.0, 3.0);
        MiniStatisticsToolkit b = this.createFromArgs(1.0, 2.0, 3.0);
        assertTrue(a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
    }
}