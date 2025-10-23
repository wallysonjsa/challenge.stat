package challenge.stat.springboot.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class TransactionRequest {
    @NotNull
    private double value;

    @NotNull
    private OffsetDateTime dateTime;

    public double getValue() {
        return value;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }
}
