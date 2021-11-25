package at.wrk.fmd.geobroker.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class InstanceIdProvider {
    private static final Logger LOG = LoggerFactory.getLogger(InstanceIdProvider.class);

    private static final UUID INSTANCE_ID = UUID.randomUUID();
    private static final AtomicReference<Instant> LAST_LOG_OUTPUT = new AtomicReference<>(Instant.EPOCH);
    private static final Duration LOG_SUPPRESSION_DURATION = Duration.ofMinutes(30);

    public String get() {
        logInstanceIdWithTimedSuppression();
        return INSTANCE_ID.toString();
    }

    private void logInstanceIdWithTimedSuppression() {
        Instant now = Instant.now();
        Instant updatedTimestamp = LAST_LOG_OUTPUT.accumulateAndGet(
                now,
                (previous, updated) -> Duration.between(previous, updated).compareTo(LOG_SUPPRESSION_DURATION) > 0
                        ? updated
                        : previous);
        if (updatedTimestamp == now) {
            LOG.info("Geobroker instance running with instance ID: {}", INSTANCE_ID);
        }
    }
}
