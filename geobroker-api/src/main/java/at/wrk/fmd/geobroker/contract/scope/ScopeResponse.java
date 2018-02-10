/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.scope;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ScopeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<LiveUnit> units;
    private final List<Incident> incidents;

    public ScopeResponse(final List<LiveUnit> units, final List<Incident> incidents) {
        this.units = units;
        this.incidents = incidents;
    }

    public List<LiveUnit> getUnits() {
        return units;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("units", units)
                .append("incidents", incidents)
                .toString();
    }
}
