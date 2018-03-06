/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.incident.Incident;
import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;

import java.util.Optional;
import java.util.Set;

public interface PoiRepository {

    void updatePoi(final PointOfInterest poi);

    void deletePoi(final String poiId);

    Optional<PointOfInterest> getPoi(final String poiId);

    Set<PointOfInterest> getAll();
}
