/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.unit;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.generic.Point;
import at.wrk.fmd.geobroker.contract.generic.Position;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * The POJO for the public unit view.
 */
public class LiveUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final Point lastPoint;
    private final Point targetPoint;
    private final Position currentPosition;
    private final Boolean isAvailableForDispatching;

    public LiveUnit(
            final String id,
            final String name,
            final Point lastPoint,
            final Point targetPoint,
            final Position currentPosition,
            final Boolean isAvailableForDispatching) {
        this.id = id;
        this.name = name;
        this.lastPoint = lastPoint;
        this.targetPoint = targetPoint;
        this.currentPosition = currentPosition;
        this.isAvailableForDispatching = isAvailableForDispatching;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point getLastPoint() {
        return lastPoint;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Boolean getAvailableForDispatching() {
        return isAvailableForDispatching;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveUnit liveUnit = (LiveUnit) o;
        return Objects.equals(id, liveUnit.id) &&
                Objects.equals(name, liveUnit.name) &&
                Objects.equals(lastPoint, liveUnit.lastPoint) &&
                Objects.equals(targetPoint, liveUnit.targetPoint) &&
                Objects.equals(currentPosition, liveUnit.currentPosition) &&
                Objects.equals(isAvailableForDispatching, liveUnit.isAvailableForDispatching);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastPoint, targetPoint, currentPosition, isAvailableForDispatching);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("id", id)
                .append("name", name)
                .append("lastPoint", lastPoint)
                .append("targetPoint", targetPoint)
                .append("currentPosition", currentPosition)
                .append("isAvailableForDispatching", isAvailableForDispatching)
                .toString();
    }
}
