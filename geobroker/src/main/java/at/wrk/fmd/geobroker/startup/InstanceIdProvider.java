package at.wrk.fmd.geobroker.startup;

import java.util.UUID;

public class InstanceIdProvider {

    private static final UUID INSTANCE_ID = UUID.randomUUID();

    public String get() {
        return INSTANCE_ID.toString();
    }
}
