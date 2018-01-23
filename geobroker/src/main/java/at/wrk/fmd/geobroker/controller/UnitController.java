package at.wrk.fmd.geobroker.controller;

import at.wrk.fmd.geobroker.contract.ExternalUnit;
import at.wrk.fmd.geobroker.contract.Response;
import at.wrk.fmd.geobroker.data.Unit;
import at.wrk.fmd.geobroker.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/private/unit")
public class UnitController {
    private final UnitService unitService;

    @Autowired
    public UnitController(final UnitService unitService) {
        this.unitService = unitService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response updateUnit(
            @RequestParam("unitId") final String unitId,
            @RequestParam("token") final String token,
            @RequestParam(value = "displayName", required = false, defaultValue = "") final String displayName) {
        Unit updatedUnit = new Unit(unitId, token, displayName);
        unitService.createOrUpdateUnit(updatedUnit);
        return Response.Builder
                .createSuccessful()
                .withDescription("Unit successfully updated.")
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Response removeUnit(@RequestParam("unitId") final String unitId) {
        unitService.removeUnit(unitId);
        return Response.Builder
                .createSuccessful()
                .withDescription("Unit successfully deleted.")
                .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> getUnit(@RequestParam("unitId") final String unitId) {
        ResponseEntity<Response> entity;
        Optional<ExternalUnit> loadedUnit = unitService.getUnit(unitId);

        if (loadedUnit.isPresent()) {
            Response response = Response.Builder
                    .createSuccessful()
                    .withPayload(loadedUnit.get())
                    .build();
            entity = ResponseEntity.ok().body(response);
        } else {
            Response response = Response.Builder
                    .createError()
                    .withDescription("Unit with the given id was not found.")
                    .build();
            entity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return entity;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Response getAllUnits() {
        Set<ExternalUnit> allUnits = unitService.getAllUnits();
        return Response.Builder
                .createSuccessful()
                .withPayload(new ArrayList<>(allUnits))
                .build();
    }
}
