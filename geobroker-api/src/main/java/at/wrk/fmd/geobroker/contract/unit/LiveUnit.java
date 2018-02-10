package at.wrk.fmd.geobroker.contract.unit;

import at.wrk.fmd.geobroker.contract.ContractToStringStyle;
import at.wrk.fmd.geobroker.contract.generic.Point;
import at.wrk.fmd.geobroker.contract.generic.Position;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * The POJO for the public unit view.
 */
public class LiveUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final Point lastPoint;
    private final Point targetPoint;
    private final Position currentPosition;

    public LiveUnit(
            final String id,
            final String name,
            final Point lastPoint,
            final Point targetPoint,
            final Position currentPosition) {
        this.id = id;
        this.name = name;
        this.lastPoint = lastPoint;
        this.targetPoint = targetPoint;
        this.currentPosition = currentPosition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point getLastPoint() {
        return lastPoint;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ContractToStringStyle.STYLE)
                .append("id", id)
                .append("name", name)
                .append("lastPoint", lastPoint)
                .append("targetPoint", targetPoint)
                .append("currentPosition", currentPosition)
                .toString();
    }
}
