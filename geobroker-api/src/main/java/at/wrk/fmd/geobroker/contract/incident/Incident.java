/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.incident;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.generic.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Incident implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String type;
    private final Boolean priority;
    private final Boolean blue;
    private final String info;
    private final Point location;

    private final Map<String, String> assignedUnits;

    public Incident(
            final String id,
            final String type,
            final Boolean priority,
            final Boolean blue,
            final String info,
            final Point location,
            final Map<String, String> assignedUnits) {
        this.id = Objects.requireNonNull(id);
        this.type = type;
        this.priority = priority;
        this.blue = blue;
        this.info = info;
        this.location = location;
        this.assignedUnits = assignedUnits;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Boolean getPriority() {
        return priority;
    }

    public Boolean getBlue() {
        return blue;
    }

    public String getInfo() {
        return info;
    }

    public Point getLocation() {
        return location;
    }

    public Map<String, String> getAssignedUnits() { return assignedUnits; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("id", id)
                .append("type", type)
                .append("priority", priority)
                .append("blue", blue)
                .append("info", info)
                .append("location", location)
                .append("assignedUnits", assignedUnits)
                .toString();
    }
}
