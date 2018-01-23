package at.wrk.fmd.geobroker.service;

import at.wrk.fmd.geobroker.data.UnitPosition;

import java.time.Duration;
import java.util.Optional;

public interface PositionService {
    Optional<UnitPosition> getOwnPosition(String unitId, String token, Duration maximumDataAge);
}
