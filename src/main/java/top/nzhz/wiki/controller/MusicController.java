package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.nzhz.wiki.req.MusicReq;
import top.nzhz.wiki.resp.CommonResp;
import top.nzhz.wiki.resp.MusicResp;
import top.nzhz.wiki.resp.PageResp;
import top.nzhz.wiki.service.MusicService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Resource
    private MusicService musicService;

    @GetMapping("/list")
    public CommonResp list(MusicReq req) {
        CommonResp<PageResp<MusicResp>> resp = new CommonResp<>();
        PageResp<MusicResp> list=musicService.list(req);
        resp.setContent(list);
        return resp;
    }
}
