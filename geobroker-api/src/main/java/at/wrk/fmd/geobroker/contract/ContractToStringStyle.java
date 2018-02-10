package at.wrk.fmd.geobroker.contract;

import org.apache.commons.lang3.builder.ToStringStyle;

public final class ContractToStringStyle extends ToStringStyle {
    public static ContractToStringStyle STYLE = new ContractToStringStyle();

    private ContractToStringStyle() {
        setUseShortClassName(true);
    }
}
