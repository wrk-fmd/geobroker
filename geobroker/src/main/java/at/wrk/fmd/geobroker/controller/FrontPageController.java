package at.wrk.fmd.geobroker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FrontPageController {

    private final String applicationVersion;

    public FrontPageController(@Value("${application.version}") final String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcomePage(final Model model) {
        model.addAttribute("version", applicationVersion);
        return "index";
    }
}
