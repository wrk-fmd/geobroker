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
import javax.validation.constraints.NotNull;
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
    private final Boolean isAvailableForDispatching;

    public ConfiguredUnit(
            final String id,
            final String name,
            final String token,
            final List<String> units,
            final List<String> incidents,
            final Point lastPoint,
            final Point targetPoint, final Boolean isAvailableForDispatching) {
        this.id = Objects.requireNonNull(id, "Unit identifier must not be null.");
        this.name = Objects.requireNonNull(name, "Display name of unit must not be null.");
        this.token = Objects.requireNonNull(token, "Token of unit must not be null.");
        this.units = units == null ? ImmutableList.of() : ImmutableList.copyOf(units);
        this.incidents = incidents == null ? ImmutableList.of() : ImmutableList.copyOf(incidents);
        this.lastPoint = lastPoint;
        this.targetPoint = targetPoint;
        this.isAvailableForDispatching = isAvailableForDispatching;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getToken() {
        return token;
    }

    public List<String> getUnits() {
        return units == null ? ImmutableList.of() : ImmutableList.copyOf(units);
    }

    public List<String> getIncidents() {
        return incidents == null ? ImmutableList.of() : ImmutableList.copyOf(incidents);
    }

    @Nullable
    public Point getLastPoint() {
        return lastPoint;
    }

    @Nullable
    public Point getTargetPoint() {
        return targetPoint;
    }

    @Nullable
    public Boolean getAvailableForDispatching() {
        return isAvailableForDispatching;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfiguredUnit that = (ConfiguredUnit) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(token, that.token) &&
                Objects.equals(units, that.units) &&
                Objects.equals(incidents, that.incidents) &&
                Objects.equals(lastPoint, that.lastPoint) &&
                Objects.equals(targetPoint, that.targetPoint) &&
                Objects.equals(isAvailableForDispatching, that.isAvailableForDispatching);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, token, units, incidents, lastPoint, targetPoint, isAvailableForDispatching);
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
                .append("isAvailableForDispatching", isAvailableForDispatching)
                .toString();
    }
}
