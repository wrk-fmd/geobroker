package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.data.Unit;

import java.util.Optional;
import java.util.Set;

public interface UnitRepository {

    boolean isTokenAuthorized(final String unitId, final String token);

    void updateUnit(final Unit unit);

    void deleteUnit(final String unitId);

    Optional<Unit> getUnit(final String unitId);

    Set<Unit> getAll();
}
