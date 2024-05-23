/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;
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
public class InMemoryPoiRepository implements PoiRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryPoiRepository.class);

    private final Map<String, PointOfInterest> storage;

    public InMemoryPoiRepository() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public void updatePoi(final PointOfInterest poi) {
        LOG.trace("Updating POI in storage to {}", poi);
        storage.put(poi.getId(), poi);
    }

    @Override
    public void deletePoi(final String poiId) {
        PointOfInterest removedPoi = storage.remove(poiId);

        if (removedPoi != null) {
            LOG.trace("Removed POI from storage: {}", removedPoi);
        } else {
            LOG.trace("POI with id {} is not present in storage. Nothing to remove.", poiId);
        }
    }

    @Override
    public Optional<PointOfInterest> getPoi(final String poiId) {
        return Optional.ofNullable(storage.get(poiId));
    }

    @Override
    public Set<PointOfInterest> getAll() {
        return ImmutableSet.copyOf(storage.values());
    }
}
