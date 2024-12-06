package ru.andr.homeworkcicd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/test")
    public String test() {
        System.out.println("Test");
        return "Test";
    }
}
