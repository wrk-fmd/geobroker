package at.wrk.fmd.geobroker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/public/position")
public class PositionController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPosition(final @RequestParam("unitId") String unitId, final @RequestParam("token") String token) {

        return "Hello World";
    }
}
