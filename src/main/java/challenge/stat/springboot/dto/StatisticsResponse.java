package challenge.stat.springboot.dto;

import java.util.DoubleSummaryStatistics;

public class StatisticsResponse {
    private long count;
    private double sum;
    private double avg;
    private double min;
    private double max;

    public StatisticsResponse(DoubleSummaryStatistics statistics) {
        this.count = statistics.getCount();
        this.sum = statistics.getSum();
        this.avg = statistics.getAverage();
        this.min = statistics.getMin();
        this.max = statistics.getMax();
    }

    public long getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    public double getAvg() {
        return avg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
