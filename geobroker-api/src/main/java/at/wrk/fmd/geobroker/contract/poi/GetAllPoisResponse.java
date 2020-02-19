/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.poi;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("pointsOfInterest", pointsOfInterest)
                .toString();
    }
}
