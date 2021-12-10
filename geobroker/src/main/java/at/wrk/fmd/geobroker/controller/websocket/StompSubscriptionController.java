package at.wrk.fmd.geobroker.controller.websocket;

import at.wrk.fmd.geobroker.api.stomp.scope.UnitsUpdatedEvent;
import com.google.common.collect.ImmutableList;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StompSubscriptionController {
    @SubscribeMapping("/topic/scope/{unit-id}/units")
    public UnitsUpdatedEvent handleAddedUnitsSubscriber() {
        // TODO return initial state
        return new UnitsUpdatedEvent(ImmutableList.of());
    }
}
