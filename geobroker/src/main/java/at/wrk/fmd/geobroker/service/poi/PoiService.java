/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.poi;

import at.wrk.fmd.geobroker.contract.poi.PointOfInterest;

import java.util.Optional;
import java.util.Set;

public interface PoiService {

    void createOrUpdatePoi(PointOfInterest poi);

    void removePoi(String poiId);

    Set<PointOfInterest> getAllPois();

    Optional<PointOfInterest> getPoi(String poiId);
}
