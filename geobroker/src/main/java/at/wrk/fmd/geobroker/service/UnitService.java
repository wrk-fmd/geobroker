package at.wrk.fmd.geobroker.service;

import at.wrk.fmd.geobroker.contract.ExternalUnit;
import at.wrk.fmd.geobroker.data.Unit;

import java.util.Optional;
import java.util.Set;

public interface UnitService {

    void createOrUpdateUnit(Unit unit);

    void removeUnit(String unitId);

    Set<ExternalUnit> getAllUnits();

    Optional<ExternalUnit> getUnit(String unitId);
}
