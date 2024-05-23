package at.wrk.fmd.geobroker.service.position;

import at.wrk.fmd.geobroker.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class PositionCleanupTask {
    private static final long EVERY_TEN_MINUTES = 600000;

    private final PositionRepository positionRepository;
    private final Clock clock;
    private final int retentionTimeInHours;

    @Autowired
    public PositionCleanupTask(
            final PositionRepository positionRepository,
            final Clock clock,
            @Value("${retention.max.hours:12}") final int retentionTimeInHours) {
        this.positionRepository = positionRepository;
        this.clock = clock;
        this.retentionTimeInHours = retentionTimeInHours;
    }

    @Scheduled(fixedRate = EVERY_TEN_MINUTES)
    public void cleanupOutdatedPositionData() {
        Instant deletionThreshold = clock.instant().minus(retentionTimeInHours, ChronoUnit.HOURS);
        positionRepository.cleanupOutdatedPositions(deletionThreshold);
    }
}
