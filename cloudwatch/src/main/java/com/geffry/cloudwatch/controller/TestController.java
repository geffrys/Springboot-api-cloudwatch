package com.geffry.cloudwatch.controller;


import com.geffry.cloudwatch.service.CloudWatchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@AllArgsConstructor
public class TestController {
    private final CloudWatchService cloudWatchService;

    @GetMapping
    public String health() {
        cloudWatchService.logMessageToCloudWatch("health: OK!");
        return "OK";
    }


}
