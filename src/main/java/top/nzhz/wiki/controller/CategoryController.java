package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.*;
import top.nzhz.wiki.req.CategoryQueryReq;
import top.nzhz.wiki.req.CategorySaveReq;
import top.nzhz.wiki.resp.CommonResp;
import top.nzhz.wiki.resp.CategoryQueryResp;
import top.nzhz.wiki.resp.PageResp;
import top.nzhz.wiki.service.CategoryService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list=categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp all(CategoryQueryReq req) {
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> list=categoryService.all(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {//注意json需要加注解
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }

}
