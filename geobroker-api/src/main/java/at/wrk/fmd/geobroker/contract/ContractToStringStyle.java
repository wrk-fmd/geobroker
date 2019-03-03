/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.contract;

import org.apache.commons.lang3.builder.ToStringStyle;

public final class ContractToStringStyle extends ToStringStyle {
    public static ContractToStringStyle STYLE = new ContractToStringStyle();

    private ContractToStringStyle() {
        setUseShortClassName(true);
        setUseIdentityHashCode(false);
    }
}
