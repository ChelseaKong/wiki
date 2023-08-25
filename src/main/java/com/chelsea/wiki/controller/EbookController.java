package com.chelsea.wiki.controller;

// controller层不要出现 domain的实体类ebook

import com.chelsea.wiki.req.EbookQueryReq;
import com.chelsea.wiki.req.EbookSaveReq;
import com.chelsea.wiki.resp.CommonResp;
import com.chelsea.wiki.resp.EbookQueryResp;
import com.chelsea.wiki.resp.PageResp;
import com.chelsea.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook") // this class is for ebook
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping ("/list") // Ebook mybatis
    public CommonResp list(EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping ("/save") // 保存、更新 类的(接口)，都用PostMapper
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping ("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}
