/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.incident;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class GetAllIncidentsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Incident> configuredIncidents;

    public GetAllIncidentsResponse(final List<Incident> configuredIncidents) {
        this.configuredIncidents = ImmutableList.copyOf(configuredIncidents);
    }

    public List<Incident> getConfiguredIncidents() {
        return configuredIncidents;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("configuredIncidents", configuredIncidents)
                .toString();
    }
}
