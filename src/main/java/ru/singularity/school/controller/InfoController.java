package ru.singularity.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.singularity.school.service.InfoServiceImpl;

@RestController
@RequestMapping(path = "/")
public class InfoController {
    @Value("${server.port}")
    private String port;

    private final InfoServiceImpl infoService;

    public InfoController(InfoServiceImpl infoService) {
        this.infoService = infoService;
    }

    @GetMapping(path = "/port")
    public String port() {
        return port;
    }

    @GetMapping(path = "/getArfSum/{num}")
    public long getArfSum(@PathVariable long num) {
        return infoService.getArithmeticSum(num);
    }
}
