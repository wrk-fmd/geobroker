package at.wrk.fmd.geobroker.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

public class Unit implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String unitId;
    private final String token;
    private final String displayName;

    public Unit(final String unitId, final String token, final String displayName) {
        this.unitId = Objects.requireNonNull(unitId, "Unit id must not be null.");
        this.token = Objects.requireNonNull(token, "Token must not be null.");
        this.displayName = Objects.requireNonNull(displayName, "Display name must not be null.");
    }

    /**
     * The unique identifier of the unit. This identifier is published to other units, if they are allowed to retrieve data of the unit.
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * The token proves that the sender is allowed to change data of the unit.
     */
    public String getToken() {
        return token;
    }

    /**
     * A changeable display name of the unit.
     */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Unit unit = (Unit) o;
        return Objects.equals(unitId, unit.unitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.STYLE)
                .append("unitId", unitId)
                .append("token", token)
                .append("displayName", displayName)
                .toString();
    }
}
