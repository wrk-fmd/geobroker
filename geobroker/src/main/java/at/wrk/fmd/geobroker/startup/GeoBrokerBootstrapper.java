package at.wrk.fmd.geobroker.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "at.wrk.fmd.geobroker")
public class GeoBrokerBootstrapper extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(GeoBrokerBootstrapper.class);
    }

    public static void main(String... args) {
        SpringApplication.run(GeoBrokerBootstrapper.class, args);
    }
}
