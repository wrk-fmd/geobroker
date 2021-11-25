package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.status.StatusResponse;
import at.wrk.fmd.geobroker.startup.InstanceIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("${api.url.base}/private/status")
public class StatusController {
    private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

    private final InstanceIdProvider instanceIdProvider;

    @Autowired
    public StatusController(final InstanceIdProvider instanceIdProvider) {
        this.instanceIdProvider = instanceIdProvider;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<StatusResponse> getStatus() {
        StatusResponse response = new StatusResponse(instanceIdProvider.get());
        LOG.trace("Sending status response: {}", response);
        return ResponseEntity.ok(response);
    }
}
