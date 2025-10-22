package challenge.stat.springboot.model;

import java.time.OffsetDateTime;

public class Transaction {
    private double value;
    private OffsetDateTime dateTime;

    public Transaction(double value, OffsetDateTime dateTime) {
        this.value = value;
        this.dateTime = dateTime;
    }

    public double getValue() {
        return value;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }
}
