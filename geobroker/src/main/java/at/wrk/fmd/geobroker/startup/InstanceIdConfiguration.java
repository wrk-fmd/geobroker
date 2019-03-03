package at.wrk.fmd.geobroker.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class InstanceIdConfiguration {
    @Bean
    @Scope("singleton")
    public InstanceIdProvider createInstanceIdProvider() {
        return new InstanceIdProvider();
    }
}
