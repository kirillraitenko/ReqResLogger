package com.t1.reqreslogger.controller;

import com.t1.reqreslogger.annotations.LoggingDisabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    /*@GetMapping("g/")
    public Map<String, String> test() {
        return Map.of("2", "1");
    }*/
    @GetMapping("/")
    public String test() {
        throw new RuntimeException("RuntimeException");
        //return "ssdads";
    }

    @GetMapping("/map")
    public Map<String, String> testMap() {
        return Map.of("testK", "testV");
    }

    @GetMapping("/resp")
    public ResponseEntity<Integer> testResp() {
        return ResponseEntity.ok().body(11114444);
        //return "ssdads";
    }
}
