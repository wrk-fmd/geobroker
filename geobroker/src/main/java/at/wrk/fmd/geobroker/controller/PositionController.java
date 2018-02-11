/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/public/positions")
public class PositionController {


    @RequestMapping(value = "/{unitId}", method = RequestMethod.POST)
    @ResponseBody
    public String getPosition(final @PathVariable("unitId") String unitId, final @RequestParam("token") String token) {

        return "Hello World";
    }
}
