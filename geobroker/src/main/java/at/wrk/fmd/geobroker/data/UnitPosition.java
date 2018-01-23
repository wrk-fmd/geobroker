package at.wrk.fmd.geobroker.data;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class UnitPosition implements Serializable, Comparable<UnitPosition> {
    private static final long serialVersionUID = 1L;

    private final Position position;
    private final Instant updatedAt;

    public UnitPosition(@Nullable final Position position, final Instant updatedAt) {
        this.position = position;
        this.updatedAt = updatedAt;
    }

    @Nullable
    public Position getPosition() {
        return position;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnitPosition that = (UnitPosition) o;
        return Objects.equals(position, that.position) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(position, updatedAt);
    }

    @Override
    public int compareTo(@SuppressWarnings("NullableProblems") final UnitPosition o) {
        return updatedAt.compareTo(o.updatedAt);
    }
}
