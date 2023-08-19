package com.chelsea.wiki.service;

import com.chelsea.wiki.domain.Ebook;
import com.chelsea.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    @Resource // jdk
    //@Autowired spring
    private EbookMapper ebookMapper;

    public List<Ebook> list() {
        return ebookMapper.selectByExample(null);
    }

}
