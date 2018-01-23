package at.wrk.fmd.geobroker.service;

import at.wrk.fmd.geobroker.contract.ExternalUnit;
import at.wrk.fmd.geobroker.data.Unit;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SimpleUnitService implements UnitService {

    private final UnitRepository unitRepository;

    @Autowired
    public SimpleUnitService(final UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public void createOrUpdateUnit(final Unit unit) {
        Objects.requireNonNull(unit, "Unit to update must not be null");
        unitRepository.updateUnit(unit);
    }

    @Override
    public void removeUnit(final String unitId) {
        Objects.requireNonNull(unitId, "Unit id to remove must not be null!");
        unitRepository.deleteUnit(unitId);
    }

    @Override
    public Set<ExternalUnit> getAllUnits() {
        return unitRepository
                .getAll()
                .stream()
                .map(this::mapToExternalUnit)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ExternalUnit> getUnit(final String unitId) {
        return unitRepository.getUnit(unitId).map(this::mapToExternalUnit);
    }

    private ExternalUnit mapToExternalUnit(final Unit unit) {
        return new ExternalUnit(unit.getUnitId(), unit.getDisplayName(), null);
    }
}
