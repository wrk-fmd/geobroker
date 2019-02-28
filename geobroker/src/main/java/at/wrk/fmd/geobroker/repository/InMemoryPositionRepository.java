/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.generic.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPositionRepository implements PositionRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryPositionRepository.class);

    private final Map<String, Position> storage;
    private final Clock clock;

    @Autowired
    public InMemoryPositionRepository(final Clock clock) {
        this.clock = clock;
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public void storePosition(final String unitId, final Position lastPosition) {
        Objects.requireNonNull(unitId, "Unit identifier must not be null.");
        Objects.requireNonNull(lastPosition, "Position to update for unit must not be null.");

        storage.compute(unitId, (id, oldPosition) -> getMoreRecentPosition(oldPosition, lastPosition));
    }

    @Override
    public Optional<Position> getPosition(final String unitId) {
        return Optional.ofNullable(storage.get(unitId));
    }

    @Override
    public Optional<Position> getPosition(final String unitId, final int maximumDataAgeInMinutes) {
        Instant dataAgeThreshold = clock.instant().minus(maximumDataAgeInMinutes, ChronoUnit.MINUTES);
        return getPosition(unitId)
                .filter(position -> dataAgeThreshold.compareTo(position.getTimestamp()) <= 0);
    }

    @Override
    public void cleanupOutdatedPositions(final Instant deletionThreshold) {
        LOG.info("Cleaning up position data which is older than {}.", deletionThreshold);

        for (Map.Entry<String, Position> entry : storage.entrySet()) {
            if (deletionThreshold.compareTo(entry.getValue().getTimestamp()) > 0) {
                storage.remove(entry.getKey());
            }
        }
    }

    private static Position getMoreRecentPosition(@Nullable final Position oldPosition, final Position newPosition) {
        Position moreRecentPosition = newPosition;

        if (oldPosition != null) {
            int comparison = oldPosition.getTimestamp().compareTo(newPosition.getTimestamp());
            if (comparison > 0) {
                LOG.warn("Tried to update position to an older value. Update is discarded. Position update: {}", newPosition);
                moreRecentPosition = oldPosition;
            }
        }

        return moreRecentPosition;
    }
}
