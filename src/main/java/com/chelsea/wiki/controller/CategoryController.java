package com.chelsea.wiki.controller;

// controller层不要出现 domain的实体类category

import com.chelsea.wiki.req.CategoryQueryReq;
import com.chelsea.wiki.req.CategorySaveReq;
import com.chelsea.wiki.resp.CategoryQueryResp;
import com.chelsea.wiki.resp.CommonResp;
import com.chelsea.wiki.resp.PageResp;
import com.chelsea.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category") // this class is for category
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    // Query
    @GetMapping ("/list") // Category mybatis
    public CommonResp list(@Valid CategoryQueryReq req) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping ("/all") // Category mybatis
    public CommonResp all() {
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }

    // add
    @PostMapping ("/save") // 保存、更新 类的(接口)，都用PostMapper
    public CommonResp save(@Valid@RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    // delete
    @DeleteMapping ("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
