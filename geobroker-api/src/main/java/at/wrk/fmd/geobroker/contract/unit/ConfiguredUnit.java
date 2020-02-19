/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.unit;

import at.wrk.fmd.geobroker.contract.generic.OneTimeAction;
import at.wrk.fmd.geobroker.contract.generic.Point;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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
    private final List<OneTimeAction> availableOneTimeActions;

    /**
     * @deprecated Use {@link #ConfiguredUnit(Builder)} instead.
     */
    @Deprecated
    public ConfiguredUnit(
            final String id,
            final String name,
            final String token,
            final List<String> units,
            final List<String> incidents,
            final Point lastPoint,
            final Point targetPoint,
            final Boolean isAvailableForDispatching) {
        this.id = Objects.requireNonNull(id, "Unit identifier must not be null.");
        this.name = Objects.requireNonNull(name, "Display name of unit must not be null.");
        this.token = Objects.requireNonNull(token, "Token of unit must not be null.");
        this.units = units == null ? ImmutableList.of() : ImmutableList.copyOf(units);
        this.incidents = incidents == null ? ImmutableList.of() : ImmutableList.copyOf(incidents);
        this.lastPoint = lastPoint;
        this.targetPoint = targetPoint;
        this.isAvailableForDispatching = isAvailableForDispatching;
        this.availableOneTimeActions = ImmutableList.of();
    }

    public ConfiguredUnit(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "Unit identifier must not be null.");
        this.name = Objects.requireNonNull(builder.name, "Display name of unit must not be null.");
        this.token = Objects.requireNonNull(builder.token, "Token of unit must not be null.");
        this.units = builder.units == null ? ImmutableList.of() : ImmutableList.copyOf(builder.units);
        this.incidents = builder.incidents == null ? ImmutableList.of() : ImmutableList.copyOf(builder.incidents);
        this.lastPoint = builder.lastPoint;
        this.targetPoint = builder.targetPoint;
        this.isAvailableForDispatching = builder.isAvailableForDispatching;
        this.availableOneTimeActions = builder.availableOneTimeActions == null ? ImmutableList.of() : ImmutableList.copyOf(builder.availableOneTimeActions);
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

    @Nullable
    public Boolean getAvailableForDispatching() {
        return isAvailableForDispatching;
    }

    public List<OneTimeAction> getAvailableOneTimeActions() {
        return availableOneTimeActions;
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
                Objects.equals(isAvailableForDispatching, that.isAvailableForDispatching) &&
                Objects.equals(availableOneTimeActions, that.availableOneTimeActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, token, units, incidents, lastPoint, targetPoint, isAvailableForDispatching, availableOneTimeActions);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("token", token)
                .append("units", units)
                .append("incidents", incidents)
                .append("lastPoint", lastPoint)
                .append("targetPoint", targetPoint)
                .append("isAvailableForDispatching", isAvailableForDispatching)
                .append("availableOneTimeActions", availableOneTimeActions)
                .toString();
    }

    public static Builder builder(
            final String id,
            final String name,
            final String token) {
        return new Builder(id, name, token);
    }

    public static class Builder {
        private final String id;
        private final String name;
        private final String token;
        private List<String> units;
        private List<String> incidents;
        private Point lastPoint;
        private Point targetPoint;
        private Boolean isAvailableForDispatching;
        private List<OneTimeAction> availableOneTimeActions;

        private Builder(
                final String id,
                final String name,
                final String token) {
            this.id = id;
            this.name = name;
            this.token = token;
        }

        public Builder withUnits(final List<String> units) {
            this.units = units;
            return this;
        }

        public Builder withIncidents(final List<String> incidents) {
            this.incidents = incidents;
            return this;
        }

        public Builder withLastPoint(final Point lastPoint) {
            this.lastPoint = lastPoint;
            return this;
        }

        public Builder withTargetPoint(final Point targetPoint) {
            this.targetPoint = targetPoint;
            return this;
        }

        public Builder withAvailableForDispatching(final Boolean availableForDispatching) {
            isAvailableForDispatching = availableForDispatching;
            return this;
        }

        public Builder withAvailableOneTimeActions(final List<OneTimeAction> availableOneTimeActions) {
            this.availableOneTimeActions = availableOneTimeActions;
            return this;
        }

        public ConfiguredUnit build() {
            return new ConfiguredUnit(this);
        }
    }
}
