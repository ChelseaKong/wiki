package com.chelsea.wiki.service;

import com.chelsea.wiki.domain.Demo;
import com.chelsea.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {
    @Resource // jdk
    //@Autowired spring
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }

}
