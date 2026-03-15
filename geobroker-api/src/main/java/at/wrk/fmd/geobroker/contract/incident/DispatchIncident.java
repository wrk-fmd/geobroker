package at.wrk.fmd.geobroker.contract.incident;

import at.wrk.fmd.geobroker.contract.generic.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;
import java.util.Objects;

public class DispatchIncident extends Incident {
    private final Map<String, Integer> availableUnitDistancesInMeters;

    public DispatchIncident(
            final String id,
            final String type,
            final Boolean priority,
            final Boolean blue,
            final String info,
            final Point location,
            final Point destination,
            final Map<String, String> assignedUnits,
            final Map<String, Integer> availableUnitDistancesInMeters) {
        super(id, type, priority, blue, info, location, destination, assignedUnits);
        this.availableUnitDistancesInMeters = availableUnitDistancesInMeters;
    }

    public Map<String, Integer> getAvailableUnitDistancesInMeters() {
        return availableUnitDistancesInMeters;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DispatchIncident that = (DispatchIncident) o;
        return Objects.equals(availableUnitDistancesInMeters, that.availableUnitDistancesInMeters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), availableUnitDistancesInMeters);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("availableUnitDistancesInMeters", availableUnitDistancesInMeters)
                .toString();
    }
}
