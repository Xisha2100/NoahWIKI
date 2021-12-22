package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.nzhz.wiki.domain.Ebook;
import top.nzhz.wiki.req.EbookReq;
import top.nzhz.wiki.resp.CommonResp;
import top.nzhz.wiki.service.EbookService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<List<Ebook>> resp = new CommonResp<>();
        List<Ebook> list=ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
