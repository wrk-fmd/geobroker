/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract.unit;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class GetAllUnitsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<ConfiguredUnit> configuredUnits;

    public GetAllUnitsResponse(final Collection<ConfiguredUnit> configuredUnits) {
        this.configuredUnits = ImmutableList.copyOf(configuredUnits);
    }

    public List<ConfiguredUnit> getConfiguredUnits() {
        return configuredUnits;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("configuredUnits", configuredUnits)
                .toString();
    }
}
