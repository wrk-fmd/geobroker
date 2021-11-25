/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.poi;

import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;
import at.wrk.fmd.geobroker.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class SimplePoiService implements PoiService {

    private final PoiRepository poiRepository;

    @Autowired
    public SimplePoiService(final PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    @Override
    public void createOrUpdatePoi(final PointOfInterest poi) {
        Objects.requireNonNull(poi, "POI to update must not be null");
        poiRepository.updatePoi(poi);
    }

    @Override
    public void removePoi(final String poiId) {
        Objects.requireNonNull(poiId, "POI id to remove must not be null!");
        poiRepository.deletePoi(poiId);
    }

    @Override
    public Set<PointOfInterest> getAllPois() {
        return poiRepository.getAll();
    }

    @Override
    public Optional<PointOfInterest> getPoi(final String poiId) {
        return poiRepository.getPoi(poiId);
    }
}
