package at.wrk.fmd.geobroker.api.stomp.scope;

import at.wrk.fmd.geobroker.contract.unit.LiveUnit;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class UnitsUpdatedEvent {
    private final List<LiveUnit> updatedUnits;

    public UnitsUpdatedEvent(final List<LiveUnit> updatedUnits) {
        this.updatedUnits = ImmutableList.copyOf(updatedUnits);
    }

    public List<LiveUnit> getUpdatedUnits() {
        return updatedUnits;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("updatedUnits", updatedUnits)
                .toString();
    }
}
