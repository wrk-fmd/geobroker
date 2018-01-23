package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.data.Unit;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Scope("singleton")
@ThreadSafe
public class InMemoryUnitRepository implements UnitRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUnitRepository.class);

    private final Map<String, Unit> storage;

    public InMemoryUnitRepository() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isTokenAuthorized(final String unitId, final String token) {
        return getUnit(unitId)
                .map(Unit::getToken)
                .map(token::equals)
                .orElse(false);
    }

    @Override
    public void updateUnit(final Unit unit) {
        LOG.trace("Updating unit in storage to {}", unit);
        storage.put(unit.getUnitId(), unit);
    }

    @Override
    public void deleteUnit(final String unitId) {
        Unit removedUnit = storage.remove(unitId);

        if (removedUnit != null) {
            LOG.trace("Removed unit from storage: {}", removedUnit);
        } else {
            LOG.trace("Unit with id {} is not present in storage. Nothing to remove.", unitId);
        }
    }

    @Override
    public Optional<Unit> getUnit(final String unitId) {
        return Optional.ofNullable(storage.get(unitId));
    }

    @Override
    public Set<Unit> getAll() {
        return ImmutableSet.copyOf(storage.values());
    }
}
