package com.chelsea.wiki.service;

import com.chelsea.wiki.domain.Test;
import com.chelsea.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {
    @Resource // jdk
    //@Autowired spring
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }

}
