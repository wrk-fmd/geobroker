package at.wrk.fmd.geobroker.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double longitude;
    private final double latitude;
    private final int positionError;

    public Position(final double longitude, final double latitude, final int positionError) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.positionError = positionError;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    /**
     * The position error in meter.
     */
    public int getPositionError() {
        return positionError;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Double.compare(position.longitude, longitude) == 0 &&
                Double.compare(position.latitude, latitude) == 0 &&
                positionError == position.positionError;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude, positionError);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.STYLE)
                .append("longitude", longitude)
                .append("latitude", latitude)
                .append("positionError", positionError)
                .toString();
    }
}
