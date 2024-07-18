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
@RequestMapping("/disabled")
@Slf4j
@LoggingDisabled
public class DisabledController {

    @GetMapping("/resp")
    public ResponseEntity<String> testResp() {
        ThreadUtils.sleep(100);
        return ResponseEntity.ok().body("Request successful. Logging - disabled !");
    }

    @GetMapping("/map")
    public Map<String, String> testMap() {
        ThreadUtils.sleep(100);
        return Map.of("param1", "val1", "param2", "val2");
    }
}
