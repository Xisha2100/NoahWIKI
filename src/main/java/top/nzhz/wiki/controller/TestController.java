package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.nzhz.wiki.domain.Test;
import top.nzhz.wiki.service.TestService;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private TestService testService;
    //    @RequestMapping
//    @RostMapping
//    @DeleteMapping
//    @RequestMapping("/hello")
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "Hello,Post,"+name;
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }
}
