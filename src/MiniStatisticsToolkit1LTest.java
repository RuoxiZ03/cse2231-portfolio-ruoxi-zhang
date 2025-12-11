import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for the kernel implementation MiniStatisticsToolkit1L.
 *
 * These tests focus mainly on the kernel and Standard methods:
 * constructor, addData, removeLast, length, clear, newInstance,
 * transferFrom, and the iterator contract.
 *
 * @author Ruoxi Zhang
 */
public class MiniStatisticsToolkit1LTest {

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
     * Constructor should create an empty toolkit.
     */
    @Test
    public void testConstructor() {
        MiniStatisticsToolkit t = new MiniStatisticsToolkit1L();
        assertEquals(0, t.length());
        assertEquals("[]", t.toString());
    }

    /**
     * addData on an empty toolkit.
     */
    @Test
    public void testAddDataToEmpty() {
        MiniStatisticsToolkit expected = this.createFromArgs(1.0);
        MiniStatisticsToolkit t = new MiniStatisticsToolkit1L();

        t.addData(1.0);

        assertEquals(expected, t);
    }

    /**
     * addData on a non-empty toolkit.
     */
    @Test
    public void testAddDataToNonEmpty() {
        MiniStatisticsToolkit expected = this.createFromArgs(1.0, 2.0, 3.0);
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0);

        t.addData(3.0);

        assertEquals(expected, t);
    }

    /**
     * removeLast with a single element.
     */
    @Test
    public void testRemoveLastSingle() {
        MiniStatisticsToolkit expected = this.createFromArgs();
        MiniStatisticsToolkit t = this.createFromArgs(42.0);

        t.removeLast();

        assertEquals(expected, t);
        assertEquals(0, t.length());
    }

    /**
     * removeLast with multiple elements.
     */
    @Test
    public void testRemoveLastMultiple() {
        MiniStatisticsToolkit expected = this.createFromArgs(1.0, 2.0);
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0, 3.0);

        t.removeLast();

        assertEquals(expected, t);
        assertEquals(2, t.length());
    }

    /**
     * length on empty toolkit.
     */
    @Test
    public void testLengthEmpty() {
        MiniStatisticsToolkit t = new MiniStatisticsToolkit1L();
        assertEquals(0, t.length());
    }

    /**
     * length on non-empty toolkit.
     */
    @Test
    public void testLengthNonEmpty() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, -2.5, 3.3);
        assertEquals(3, t.length());
    }

    /**
     * entries() should iterate in insertion order and not modify this.
     */
    @Test
    public void testEntriesIterationOrder() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0, 3.0);
        MiniStatisticsToolkitKernel.Entries it = t.entries();

        double[] seen = new double[3];
        int i = 0;
        while (it.hasNext()) {
            seen[i] = it.next();
            i++;
        }

        assertEquals(3, i);
        assertEquals(1.0, seen[0], 1e-9);
        assertEquals(2.0, seen[1], 1e-9);
        assertEquals(3.0, seen[2], 1e-9);
        // iteration should not modify the toolkit
        assertEquals(this.createFromArgs(1.0, 2.0, 3.0), t);
    }

    /**
     * clear should remove all elements.
     */
    @Test
    public void testClear() {
        MiniStatisticsToolkit t = this.createFromArgs(5.0, 6.0);
        t.clear();
        assertEquals(0, t.length());
        assertEquals("[]", t.toString());
    }

    /**
     * newInstance should return an empty, independent instance.
     */
    @Test
    public void testNewInstance() {
        MiniStatisticsToolkit original = this.createFromArgs(1.0, 2.0);
        MiniStatisticsToolkit fresh = original.newInstance();

        assertEquals(0, fresh.length());
        assertFalse(original == fresh);

        // modifying original should not affect fresh
        original.clear();
        assertEquals(0, fresh.length());
    }

    /**
     * transferFrom should move all contents and clear the source.
     */
    @Test
    public void testTransferFromNonEmpty() {
        MiniStatisticsToolkit1L target = new MiniStatisticsToolkit1L();
        MiniStatisticsToolkit1L source = new MiniStatisticsToolkit1L();
        source.addData(1.0);
        source.addData(2.0);

        target.transferFrom(source);

        assertEquals(this.createFromArgs(1.0, 2.0), target);
        assertEquals(0, source.length());
        assertEquals("[]", source.toString());
    }

    /**
     * transferFrom from empty source.
     */
    @Test
    public void testTransferFromEmpty() {
        MiniStatisticsToolkit1L target = new MiniStatisticsToolkit1L();
        target.addData(9.0);

        MiniStatisticsToolkit1L source = new MiniStatisticsToolkit1L();

        target.transferFrom(source);

        // target should now be empty, source stays empty
        assertEquals(0, target.length());
        assertEquals(0, source.length());
    }

    /**
     * equals: same object is equal to itself.
     */
    @Test
    public void testEqualsReflexive() {
        MiniStatisticsToolkit t = this.createFromArgs(1.0, 2.0);
        assertTrue(t.equals(t));
    }
}