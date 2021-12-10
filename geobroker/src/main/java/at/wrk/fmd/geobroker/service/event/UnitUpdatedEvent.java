package at.wrk.fmd.geobroker.service.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class UnitUpdatedEvent {
    private final String unitId;

    public UnitUpdatedEvent(final String unitId) {
        this.unitId = Objects.requireNonNull(unitId, "Unit ID must not be null.");
    }

    public String getUnitId() {
        return unitId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitUpdatedEvent that = (UnitUpdatedEvent) o;
        return Objects.equals(unitId, that.unitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("unitId", unitId)
                .toString();
    }
}
