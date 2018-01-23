package at.wrk.fmd.geobroker.repository;

import at.wrk.fmd.geobroker.data.Unit;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class InMemoryUnitRepositoryTest {
    private UnitRepository sut;

    @Before
    public void init() {
        sut = new InMemoryUnitRepository();
    }

    @Test
    public void storeUnit_unitCanBeRetrieved() {
        String unitId = "unit-id";
        Unit unit = new Unit(unitId, "token", "Fancy Unit");
        sut.updateUnit(unit);

        Optional<Unit> retrievedUnit = sut.getUnit(unitId);
        assertThat(retrievedUnit, hasValue(samePropertyValuesAs(unit)));
    }

    @Test
    public void storeUnit_validTokenIsAuthorized() {
        String unitId = "unit-id";
        String token = "token";
        Unit unit = new Unit(unitId, token, "Fancy Unit");
        sut.updateUnit(unit);

        boolean isAuthorized = sut.isTokenAuthorized(unitId, token);
        assertThat(isAuthorized, is(true));
    }

    @Test
    public void storeUnit_invalidTokenIsNotAuthorized() {
        String unitId = "unit-id";
        String token = "token";
        Unit unit = new Unit(unitId, token, "Fancy Unit");
        sut.updateUnit(unit);

        boolean isAuthorized = sut.isTokenAuthorized(unitId, "another token");
        assertThat(isAuthorized, is(false));
    }

    @Test
    public void deleteStoredUnit_unitCannotBeRetrieved() {
        String unitId = "unit-id";
        Unit unit = new Unit(unitId, "token", "Fancy Unit");
        sut.updateUnit(unit);
        sut.deleteUnit(unitId);

        Optional<Unit> retrievedUnit = sut.getUnit(unitId);
        assertThat(retrievedUnit, isEmpty());
    }
}