package at.wrk.fmd.geobroker.startup.initial;

import at.wrk.fmd.geobroker.service.unit.UnitService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Component
public class InitialConfigLoader {
    private static final Logger LOG = LoggerFactory.getLogger(InitialConfigLoader.class);

    private final String initialConfigurationFilePath;
    private final Gson gson;
    private final UnitService unitService;

    @Autowired
    public InitialConfigLoader(
            @Value("${startup.initial.configuration:}") final String initialConfigurationFilePath,
            final Gson gson,
            final UnitService unitService) {
        this.initialConfigurationFilePath = initialConfigurationFilePath;
        this.gson = gson;
        this.unitService = unitService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void createInitialConfiguration() {
        String filePath = StringUtils.trimToNull(initialConfigurationFilePath);
        if (filePath != null) {
            LOG.info("Loading initial configuration from '{}'.", filePath);
            InitialConfiguration initialConfiguration = null;
            try (FileReader fileReader = new FileReader(filePath)) {
                initialConfiguration = gson.fromJson(fileReader, InitialConfiguration.class);
            } catch (IOException e) {
                LOG.warn("File was not found.", e);
            }

            if (initialConfiguration != null) {
                LOG.debug("Got {} units to load.", initialConfiguration.getInitialUnits().size());
                initialConfiguration.getInitialUnits().forEach(unitService::createOrUpdateUnit);
            }
        } else {
            LOG.info("No file path for initial configuration configured. Loading of file is skipped.");
        }
    }
}
