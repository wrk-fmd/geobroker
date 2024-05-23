/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.generic;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class Point implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Double latitude;
    private final Double longitude;

    public Point(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NotNull
    public Double getLatitude() {
        return latitude;
    }

    @NotNull
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Point)) {
            return false;
        }

        Point point = (Point) o;
        return Objects.equals(latitude, point.latitude) &&
                Objects.equals(longitude, point.longitude);
    }

    @Override
    public int hashCode() {

        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .toString();
    }
}
