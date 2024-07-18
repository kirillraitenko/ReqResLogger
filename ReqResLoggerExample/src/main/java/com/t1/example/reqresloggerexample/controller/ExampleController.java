package com.t1.example.reqresloggerexample.controller;

import com.t1.example.reqresloggerexample.utils.ThreadUtils;
import com.t1.reqreslogger.annotations.LoggingDisabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/example")
@Slf4j
public class ExampleController {

    @GetMapping("/resp")
    public ResponseEntity<String> testResp() {
        ThreadUtils.sleep(100);
        return ResponseEntity.ok().body("Success request");
    }

    @GetMapping("/map")
    public Map<String, String> getMap() {
        ThreadUtils.sleep(100);
        return Map.of("param1", "val1", "param2", "val2");
    }

    @LoggingDisabled
    @GetMapping("/map_disabled")
    public Map<String, String> getMapDisabled() {
        return Map.of("param1", "val1", "param2", "val2");
    }
}
