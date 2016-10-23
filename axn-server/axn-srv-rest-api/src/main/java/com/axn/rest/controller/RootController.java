package com.axn.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings({"unused", "SameReturnValue"})
@Controller
@PropertySource("classpath:version.properties")
@RequestMapping(value = "/*")
public final class RootController {
    private final Environment environment;

    @Autowired
    public RootController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public final ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("version", environment.getProperty("server.version"));
        modelAndView.addObject("build_date", environment.getProperty("server.build_date"));
        return modelAndView;
    }
}
