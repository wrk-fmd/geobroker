/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.poi;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.generic.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

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
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("id", id)
                .append("type", type)
                .append("info", info)
                .append("location", location)
                .toString();
    }
}
