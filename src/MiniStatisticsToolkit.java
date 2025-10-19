import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Implementation of a simple statistics toolkit.
 * Demonstrates core component design.
 * 
 * @author Ruoxi Zhang
 */
public class MiniStatisticsToolkit implements MiniStatisticsToolkitKernel {

    private Sequence<Double> data;

    /**
     * Default constructor.
     */
    public MiniStatisticsToolkit() {
        data = new Sequence1L<>();
    }

    @Override
    public void addData(double value) {
        data.add(data.length(), value);
    }

    @Override
    public void removeLast() {
        if (data.length() > 0) {
            data.remove(data.length() - 1);
        }
    }

    @Override
    public int length() {
        return data.length();
    }

    /**
     * Secondary method — computes the mean of all data points.
     * 
     * @return mean value
     */
    public double mean() {
        double sum = 0;
        for (int i = 0; i < data.length(); i++) {
            sum += data.entry(i);
        }
        return (data.length() > 0) ? sum / data.length() : 0.0;
    }
}

