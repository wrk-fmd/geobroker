package at.wrk.fmd.geobroker.service.publisher;

import at.wrk.fmd.geobroker.api.stomp.scope.UnitsUpdatedEvent;
import at.wrk.fmd.geobroker.service.event.UnitUpdatedEvent;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketPublisher {
    private final SimpMessagingTemplate messageSender;

    @Autowired
    public WebSocketPublisher(final SimpMessagingTemplate messageSender) {
        this.messageSender = messageSender;
    }

    @EventListener
    public void unitUpdated(final UnitUpdatedEvent event) {
        final String destination = "/topic/scope/" + event.getUnitId() + "/units";
        // TODO fill with data
        messageSender.convertAndSend(destination, new UnitsUpdatedEvent(ImmutableList.of()));
    }
}
