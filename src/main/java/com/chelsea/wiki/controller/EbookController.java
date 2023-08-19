package com.chelsea.wiki.controller;

// controller层不要出现 domain的实体类ebook
import com.chelsea.wiki.req.EbookReq;
import com.chelsea.wiki.resp.CommonResp;
import com.chelsea.wiki.resp.EbookResp;
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
    public CommonResp list(EbookReq req) {
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
