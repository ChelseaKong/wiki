package com.chelsea.wiki.controller;

import com.chelsea.wiki.domain.Ebook;
import com.chelsea.wiki.resp.CommonResp;
import com.chelsea.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook") // this class is for ebook
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping ("/list") // Ebook mybatis
    public CommonResp list() {
        CommonResp<List<Ebook>> resp = new CommonResp<>();
        List<Ebook> list = ebookService.list();
        resp.setContent(list);
        return resp;
    }
}
