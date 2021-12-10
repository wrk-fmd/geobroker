package at.wrk.fmd.geobroker.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class AsyncEventHandlingConfiguration {
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster asyncMulticaster() {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("Event Handler #");
        taskExecutor.setConcurrencyLimit(Runtime.getRuntime().availableProcessors());
        multicaster.setTaskExecutor(taskExecutor);
        return multicaster;
    }
}
