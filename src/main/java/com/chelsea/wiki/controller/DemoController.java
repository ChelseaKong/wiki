package com.chelsea.wiki.controller;

import com.chelsea.wiki.domain.Demo;
import com.chelsea.wiki.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/demo") // this class is for demo
public class DemoController {
    @Resource
    private DemoService demoService;

    @GetMapping ("/list") // Demo mybatis
    public List<Demo> list() {
        return demoService.list();
    }
}
