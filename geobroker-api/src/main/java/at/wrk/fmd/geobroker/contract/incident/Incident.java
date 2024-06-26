/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.incident;

import at.wrk.fmd.geobroker.contract.generic.Point;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class Incident implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String type;
    private final Boolean priority;
    private final Boolean blue;
    private final String info;
    private final Point location;
    private final Point destination;
    private final Map<String, String> assignedUnits;

    public Incident(
            final String id,
            final String type,
            final Boolean priority,
            final Boolean blue,
            final String info,
            final Point location,
            final Point destination,
            final Map<String, String> assignedUnits) {
        this.id = Objects.requireNonNull(id);
        this.type = type;
        this.priority = priority;
        this.blue = blue;
        this.info = info;
        this.location = location;
        this.destination = destination;
        this.assignedUnits = assignedUnits == null ? ImmutableMap.of() : ImmutableMap.copyOf(assignedUnits);
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

    public Point getDestination() {
        return destination;
    }

    public Map<String, String> getAssignedUnits() {
        return assignedUnits;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incident incident = (Incident) o;
        return Objects.equals(id, incident.id) &&
                Objects.equals(type, incident.type) &&
                Objects.equals(priority, incident.priority) &&
                Objects.equals(blue, incident.blue) &&
                Objects.equals(info, incident.info) &&
                Objects.equals(location, incident.location) &&
                Objects.equals(destination, incident.destination) &&
                Objects.equals(assignedUnits, incident.assignedUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, priority, blue, info, location, destination, assignedUnits);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("type", type)
                .append("priority", priority)
                .append("blue", blue)
                .append("info", info)
                .append("location", location)
                .append("destination", destination)
                .append("assignedUnits", assignedUnits)
                .toString();
    }
}
