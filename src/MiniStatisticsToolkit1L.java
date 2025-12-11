/**
 * Kernel implementation for {@code MiniStatisticsToolkit} using a resizable
 * {@code double[]} array as the internal representation.
 *
 * @convention rep != null
 *             and 0 <= size <= rep.length
 *             and the logical sequence is rep[0..size - 1]
 * @correspondence length = size
 *                 and this[i] = rep[i] for 0 <= i < size
 *
 * @author Ruoxi Zhang
 */
public final class MiniStatisticsToolkit1L extends MiniStatisticsToolkitSecondary {

    /** Initial capacity of the array. */
    private static final int DEFAULT_CAPACITY = 4;

    /** Underlying array storing the values. */
    private double[] rep;

    /** Number of actual stored values. */
    private int size;

    /**
     * Creates a fresh, empty representation.
     */
    private void createNewRep() {
        this.rep = new double[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Constructor: initializes this as an empty toolkit.
     */
    public MiniStatisticsToolkit1L() {
        this.createNewRep();
    }

    /**
     * Ensures {@code rep} has capacity for at least {@code minCapacity}.
     *
     * @param minCapacity minimum required capacity
     */
    private void ensureCapacity(int minCapacity) {
        if (this.rep.length < minCapacity) {
            int newLength = Math.max(this.rep.length * 2, minCapacity);
            double[] newRep = new double[newLength];
            for (int i = 0; i < this.size; i++) {
                newRep[i] = this.rep[i];
            }
            this.rep = newRep;
        }
    }

    @Override
    public void addData(double value) {
        this.ensureCapacity(this.size + 1);
        this.rep[this.size] = value;
        this.size++;
    }

    @Override
    public void removeLast() {
        assert this.size > 0 : "Violation of: length() > 0";
        this.size--;
    }

    @Override
    public int length() {
        return this.size;
    }

    /**
     * Iterator over {@code rep[0..size-1]}.
     */
    private final class ArrayEntries implements MiniStatisticsToolkitKernel.Entries {

        /** Current index in the iteration. */
        private int index;

        /** Constructor. */
        private ArrayEntries() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return this.index < MiniStatisticsToolkit1L.this.size;
        }

        @Override
        public double next() {
            assert this.hasNext() : "Violation of: hasNext()";
            double x = MiniStatisticsToolkit1L.this.rep[this.index];
            this.index++;
            return x;
        }
    }

    @Override
    public MiniStatisticsToolkitKernel.Entries entries() {
        return new ArrayEntries();
    }

    @Override
    public void clear() {
        this.createNewRep();
    }

    @Override
    public MiniStatisticsToolkit newInstance() {
        return new MiniStatisticsToolkit1L();
    }

    @Override
    public void transferFrom(MiniStatisticsToolkit source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source != this";
        assert source instanceof MiniStatisticsToolkit1L
                : "Violation of: dynamic type must be MiniStatisticsToolkit1L";

        MiniStatisticsToolkit1L s = (MiniStatisticsToolkit1L) source;

        this.rep = s.rep;
        this.size = s.size;

        s.createNewRep();
    }
}