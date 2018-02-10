package at.wrk.fmd.geobroker.contract.unit;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.generic.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * The POJO for the internal configured unit.
 */
public class ConfiguredUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String token;
    private final List<String> units;
    private final List<String> incidents;
    private final Point lastPoint;
    private final Point targetPoint;

    public ConfiguredUnit(
            final String id,
            final String name,
            final String token,
            final List<String> units,
            final List<String> incidents,
            final Point lastPoint,
            final Point targetPoint) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.units = units;
        this.incidents = incidents;
        this.lastPoint = lastPoint;
        this.targetPoint = targetPoint;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public List<String> getUnits() {
        return units;
    }

    public List<String> getIncidents() {
        return incidents;
    }

    public Point getLastPoint() {
        return lastPoint;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("id", id)
                .append("name", name)
                .append("token", token)
                .append("units", units)
                .append("incidents", incidents)
                .append("lastPoint", lastPoint)
                .append("targetPoint", targetPoint)
                .toString();
    }
}
