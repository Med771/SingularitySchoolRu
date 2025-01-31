package ru.singularity.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class InfoController {
    @Value("${server.port}")
    private String port;

    @GetMapping(path = "/port")
    public String port() {
        return port;
    }
}
