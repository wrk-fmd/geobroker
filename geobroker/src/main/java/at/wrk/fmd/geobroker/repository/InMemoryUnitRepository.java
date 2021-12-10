/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Scope("singleton")
@ThreadSafe
public class InMemoryUnitRepository implements UnitRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUnitRepository.class);

    private final Map<String, ConfiguredUnit> storage;

    public InMemoryUnitRepository() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isTokenAuthorized(final String unitId, final String token) {
        return getUnit(unitId)
                .map(ConfiguredUnit::getToken)
                .map(token::equals)
                .orElse(false);
    }

    @Override
    public void updateUnit(final ConfiguredUnit unit) {
        LOG.trace("Updating unit in storage to {}", unit);
        storage.put(unit.getId(), unit);
    }

    @Override
    public void deleteUnit(final String unitId) {
        ConfiguredUnit removedUnit = storage.remove(unitId);

        if (removedUnit != null) {
            LOG.debug("Removed unit from storage: unitId: '{}', name: '{}'", unitId, removedUnit.getName());
        } else {
            LOG.debug("Unit with id {} is not present in storage. Nothing to remove.", unitId);
        }
    }

    @Override
    public Optional<ConfiguredUnit> getUnit(final String unitId) {
        return Optional.ofNullable(storage.get(unitId));
    }

    @Override
    public Set<String> getUnitsWithAccessTo(final String unitIdToCheck) {
        Objects.requireNonNull(unitIdToCheck, "Unit ID to check must not be null.");
        return storage.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getUnits().contains(unitIdToCheck))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ConfiguredUnit> getAll() {
        return ImmutableSet.copyOf(storage.values());
    }
}
