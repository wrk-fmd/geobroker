package at.wrk.fmd.geobroker.contract;

import at.wrk.fmd.geobroker.data.ToStringStyle;
import at.wrk.fmd.geobroker.data.UnitPosition;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

public class ExternalUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String unitId;
    private final String displayName;
    private final List<UnitPosition> positionTrack;

    public ExternalUnit(
            final String unitId,
            final String displayName,
            @Nullable final List<UnitPosition> positionTrack) {
        this.unitId = unitId;
        this.displayName = displayName;
        this.positionTrack = positionTrack == null ? null : ImmutableList.copyOf(positionTrack);
    }

    public String getUnitId() {
        return unitId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<UnitPosition> getPositionTrack() {
        return positionTrack;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.STYLE)
                .append("unitId", unitId)
                .append("displayName", displayName)
                .append("positionTrack", positionTrack)
                .toString();
    }
}
