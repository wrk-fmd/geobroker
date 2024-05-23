/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.generic;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp of position must not be null.");
        this.accuracy = accuracy;
        this.heading = heading;
        this.speed = speed;
    }

    @NotNull
    public Instant getTimestamp() {
        return timestamp;
    }

    @Nullable
    public Double getAccuracy() {
        return accuracy;
    }

    @Nullable
    public Double getHeading() {
        return heading;
    }

    @Nullable
    public Double getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        Position position = (Position) o;
        return Objects.equals(timestamp, position.timestamp) &&
                Objects.equals(accuracy, position.accuracy) &&
                Objects.equals(heading, position.heading) &&
                Objects.equals(speed, position.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timestamp, accuracy, heading, speed);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("timestamp", timestamp)
                .append("accuracy", accuracy)
                .append("heading", heading)
                .append("speed", speed)
                .toString();
    }
}
