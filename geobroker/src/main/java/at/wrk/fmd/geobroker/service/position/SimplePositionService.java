/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.position;

import at.wrk.fmd.geobroker.contract.generic.Position;
import at.wrk.fmd.geobroker.repository.PositionRepository;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class SimplePositionService implements PositionService {
    private static final Logger LOG = LoggerFactory.getLogger(SimplePositionService.class);

    private final UnitRepository unitRepository;
    private final PositionRepository positionRepository;
    private final boolean useClientTime;
    private final Clock clock;

    @Autowired
    public SimplePositionService(
            final UnitRepository unitRepository,
            final PositionRepository positionRepository,
            @Value("${position.update.use.client.time}") final boolean useClientTime,
            final Clock clock) {
        this.unitRepository = unitRepository;
        this.positionRepository = positionRepository;
        this.useClientTime = useClientTime;
        this.clock = clock;
    }

    @Override
    public PositionUpdateResult updatePosition(final String unitId, final String token, final Position updatedPosition) {
        PositionUpdateResult result = PositionUpdateResult.NOT_ALLOWED;

        if (unitRepository.isTokenAuthorized(unitId, token)) {
            Position positionWithCorrectTimestamp = correctTimestampIfNeeded(updatedPosition);
            positionRepository.storePosition(unitId, positionWithCorrectTimestamp);
            logUpdatedPosition(unitId, updatedPosition);
            result = PositionUpdateResult.UPDATED;
        }

        return result;
    }

    private void logUpdatedPosition(final String unitId, final Position updatedPosition) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("#positionupdate Position was successfully updated by unit '{}'. Position data: {}", unitId, updatedPosition);
        } else {
            LOG.info("#positionupdate Position was successfully updated by unit '{}'", unitId);
        }
    }

    private Position correctTimestampIfNeeded(final Position updatedPosition) {
        Position corrected = updatedPosition;
        if (!useClientTime) {
            corrected = getPositionWithCurrentTimestamp(updatedPosition);
        } else if (updatedPosition.getTimestamp().compareTo(clock.instant()) > 0) {
            LOG.info("Client provided time in the future. Current time is used as receive time. Provided timestamp: {}", updatedPosition.getTimestamp());
            corrected = getPositionWithCurrentTimestamp(updatedPosition);
        }

        return corrected;
    }

    private Position getPositionWithCurrentTimestamp(final Position updatedPosition) {
        return new Position(
                updatedPosition.getLatitude(),
                updatedPosition.getLongitude(),
                clock.instant(),
                updatedPosition.getAccuracy(),
                updatedPosition.getHeading(),
                updatedPosition.getSpeed());
    }
}
