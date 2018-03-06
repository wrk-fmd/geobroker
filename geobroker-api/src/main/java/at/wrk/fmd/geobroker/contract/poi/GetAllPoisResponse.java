/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.poi;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.incident.Incident;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class GetAllPoisResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<PointOfInterest> pointsOfInterest;

    public GetAllPoisResponse(final Collection<PointOfInterest> pointsOfInterest) {
        this.pointsOfInterest = ImmutableList.copyOf(pointsOfInterest);
    }

    public List<PointOfInterest> getPointsOfInterest() {
        return pointsOfInterest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("pointsOfInterest", pointsOfInterest)
                .toString();
    }
}
