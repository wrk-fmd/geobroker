/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.service.scope;

import at.wrk.fmd.geobroker.contract.scope.ScopeResponse;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleScopeServiceTest {

    private SimpleScopeService sut;
    private UnitRepository unitRepository;

    @Before
    public void init() {
        LiveUnitMapper mapper = mock(LiveUnitMapper.class);
        unitRepository = mock(UnitRepository.class);
        sut = new SimpleScopeService(unitRepository, mapper);
    }

    @Test
    public void tokenNotAuthorized_returnEmptyOptional() {
        when(unitRepository.isTokenAuthorized(any(), any())).thenReturn(false);

        Optional<ScopeResponse> scopeForUnit = sut.getScopeForUnit("unit id", "token");

        assertThat(scopeForUnit, isEmpty());
    }
}