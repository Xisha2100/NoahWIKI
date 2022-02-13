package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.*;
import top.nzhz.wiki.req.UserQueryReq;
import top.nzhz.wiki.req.UserSaveReq;
import top.nzhz.wiki.resp.CommonResp;
import top.nzhz.wiki.resp.PageResp;
import top.nzhz.wiki.resp.UserQueryResp;
import top.nzhz.wiki.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list=userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp all(UserQueryReq req) {
        CommonResp<List<UserQueryResp>> resp = new CommonResp<>();
        List<UserQueryResp> list=userService.all(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

}
