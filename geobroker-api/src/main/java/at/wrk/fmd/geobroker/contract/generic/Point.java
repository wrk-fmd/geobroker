package at.wrk.fmd.geobroker.contract.generic;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import static at.wrk.fmd.geobroker.contract.ContractToStringStyle.STYLE;

public class Point implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Double latitude;
    private final Double longitude;

    public Point(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, STYLE)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .toString();
    }
}
