package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.*;
import top.nzhz.wiki.req.DocQueryReq;
import top.nzhz.wiki.req.DocSaveReq;
import top.nzhz.wiki.resp.CommonResp;
import top.nzhz.wiki.resp.DocQueryResp;
import top.nzhz.wiki.resp.PageResp;
import top.nzhz.wiki.service.DocService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list=docService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content=docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    @GetMapping("/all/{musicId}")
    public CommonResp all(@PathVariable Long musicId) {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list=docService.all(musicId);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        docService.vote(id);
        return resp;
    }
}
