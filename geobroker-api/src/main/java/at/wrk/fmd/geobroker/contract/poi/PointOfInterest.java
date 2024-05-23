/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.poi;

import at.wrk.fmd.geobroker.contract.generic.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PointOfInterest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String type;
    private final String info;
    private final Point location;

    public PointOfInterest(
            final String id,
            final String type,
            final String info,
            final Point location) {
        this.id = Objects.requireNonNull(id);
        this.type = type;
        this.info = info;
        this.location = location;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointOfInterest that = (PointOfInterest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(info, that.info) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, info, location);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("type", type)
                .append("info", info)
                .append("location", location)
                .toString();
    }
}
