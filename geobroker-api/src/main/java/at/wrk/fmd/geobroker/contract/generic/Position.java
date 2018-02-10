package at.wrk.fmd.geobroker.contract.generic;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;

public class Position extends Point {
    private static final long serialVersionUID = 1L;

    private final Instant timestamp;
    private final Double accuracy;
    private final Double heading;
    private final Double speed;

    public Position(
            final double latitude,
            final double longitude,
            final Instant timestamp,
            final Double accuracy,
            final Double heading,
            final Double speed) {
        super(latitude, longitude);
        this.timestamp = timestamp;
        this.accuracy = accuracy;
        this.heading = heading;
        this.speed = speed;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public Double getHeading() {
        return heading;
    }

    public Double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .appendSuper(super.toString())
                .append("timestamp", timestamp)
                .append("accuracy", accuracy)
                .append("heading", heading)
                .append("speed", speed)
                .toString();
    }
}
