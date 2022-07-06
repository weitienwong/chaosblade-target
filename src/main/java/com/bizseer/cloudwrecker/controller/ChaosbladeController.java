package com.bizseer.cloudwrecker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author wangweitian
 */
@RestController
@RequestMapping("chaos")
public class ChaosbladeController {

    @GetMapping("hello")
    public String hello(String name, int code) {
        if (name == null) {
            name = "friend";
        }
        return sayHello(name);
    }

    @GetMapping(value = "async")
    public String asyncHello(String name, long timeout) {
        if (timeout == 0) {
            timeout = 3000;
        }
        FutureTask<String> futureTask = new FutureTask<>(() -> sayHello(name));
        new Thread(futureTask).start();
        try {
            return futureTask.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return e.getMessage();
        }
    }

    private String sayHello(String name) {
        return  "你好， " + name;
    }
}
