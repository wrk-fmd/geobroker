/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.unit;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.generic.Point;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The POJO for the internal configured unit.
 */
public class ConfiguredUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String token;
    private final List<String> units;
    private final List<String> incidents;
    private final Point lastPoint;
    private final Point targetPoint;

    public ConfiguredUnit(
            final String id,
            final String name,
            final String token,
            final List<String> units,
            final List<String> incidents,
            final Point lastPoint,
            final Point targetPoint) {
        this.id = Objects.requireNonNull(id, "Unit identifier must not be null.");
        this.name = Objects.requireNonNull(name, "Display name of unit must not be null.");
        this.token = Objects.requireNonNull(token, "Token of unit must not be null.");
        this.units = units == null ? ImmutableList.of() : ImmutableList.copyOf(units);
        this.incidents = incidents == null ? ImmutableList.of() : ImmutableList.copyOf(incidents);
        this.lastPoint = lastPoint;
        this.targetPoint = targetPoint;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public List<String> getUnits() {
        return units;
    }

    public List<String> getIncidents() {
        return incidents;
    }

    @Nullable
    public Point getLastPoint() {
        return lastPoint;
    }

    @Nullable
    public Point getTargetPoint() {
        return targetPoint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("id", id)
                .append("name", name)
                .append("token", token)
                .append("units", units)
                .append("incidents", incidents)
                .append("lastPoint", lastPoint)
                .append("targetPoint", targetPoint)
                .toString();
    }
}
