package at.wrk.fmd.geobroker.service.unit;

import at.wrk.fmd.geobroker.contract.unit.ConfiguredUnit;
import at.wrk.fmd.geobroker.repository.UnitRepository;
import at.wrk.fmd.geobroker.service.event.UnitUpdatedEvent;
import at.wrk.fmd.geobroker.util.ConfiguredUnits;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimpleUnitServiceTest {
    private UnitService sut;
    private UnitRepository unitRepository;
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void init() {
        unitRepository = mock(UnitRepository.class);
        eventPublisher = mock(ApplicationEventPublisher.class);
        sut = new SimpleUnitService(unitRepository, eventPublisher);
    }

    @Test
    public void createOrUpdateUnit_validUnit_updateUnitInRepository() {
        ConfiguredUnit configuredUnit = ConfiguredUnits.randomUnit("unit id", "some token");

        sut.createOrUpdateUnit(configuredUnit);

        verify(unitRepository).updateUnit(configuredUnit);
    }

    @Test
    public void createOrUpdateUnit_validUnit_sendUnitUpdatedEvent() {
        ConfiguredUnit configuredUnit = ConfiguredUnits.randomUnit("unit id", "some token");

        sut.createOrUpdateUnit(configuredUnit);

        verify(eventPublisher).publishEvent(new UnitUpdatedEvent(configuredUnit.getId()));
    }
}
