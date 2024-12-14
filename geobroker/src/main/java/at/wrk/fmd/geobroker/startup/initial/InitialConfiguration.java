package at.wrk.fmd.geobroker.startup.initial;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class InitialConfiguration {
    private final List<ConfiguredUnit> initialUnits;

    public InitialConfiguration(final List<ConfiguredUnit> initialUnits) {
        this.initialUnits = initialUnits == null ? List.of() : List.copyOf(initialUnits);
    }

    public List<ConfiguredUnit> getInitialUnits() {
        return initialUnits;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("initialUnits", initialUnits)
                .toString();
    }
}
