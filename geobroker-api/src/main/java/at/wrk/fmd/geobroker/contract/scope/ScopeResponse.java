/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.scope;

import at.wrk.fmd.geobroker.contract.generic.OneTimeAction;
import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class ScopeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<LiveUnit> units;
    private final List<Incident> incidents;
    private final List<OneTimeAction> availableOneTimeActions;

    public ScopeResponse(
        final List<LiveUnit> units,
        final List<Incident> incidents) {
        this(units, incidents, ImmutableList.of());
    }

    public ScopeResponse(
            final List<LiveUnit> units,
            final List<Incident> incidents,
            final List<OneTimeAction> availableOneTimeActions) {
        this.units = ImmutableList.copyOf(units);
        this.incidents = ImmutableList.copyOf(incidents);
        this.availableOneTimeActions = availableOneTimeActions;
    }

    public List<LiveUnit> getUnits() {
        return units;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public List<OneTimeAction> getAvailableOneTimeActions() {
        return availableOneTimeActions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("units", units)
                .append("incidents", incidents)
                .append("availableOneTimeActions", availableOneTimeActions)
                .toString();
    }
}
