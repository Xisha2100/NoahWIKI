package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//    @RequestMapping("/hello")
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
