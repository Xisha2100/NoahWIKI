package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.*;
import top.nzhz.wiki.req.MusicQueryReq;
import top.nzhz.wiki.req.MusicSaveReq;
import top.nzhz.wiki.resp.CommonResp;
import top.nzhz.wiki.resp.MusicQueryResp;
import top.nzhz.wiki.resp.PageResp;
import top.nzhz.wiki.service.MusicService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Resource
    private MusicService musicService;

    @GetMapping("/list")
    public CommonResp list(MusicQueryReq req) {
        CommonResp<PageResp<MusicQueryResp>> resp = new CommonResp<>();
        PageResp<MusicQueryResp> list=musicService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp all(MusicQueryReq req) {
        CommonResp<List<MusicQueryResp>> resp = new CommonResp<>();
        List<MusicQueryResp> list=musicService.all(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody MusicSaveReq req) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        musicService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        musicService.delete(id);
        return resp;
    }

}
