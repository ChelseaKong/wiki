package com.chelsea.wiki.service;

import com.chelsea.wiki.domain.Ebook;
import com.chelsea.wiki.domain.EbookExample;
import com.chelsea.wiki.mapper.EbookMapper;
import com.chelsea.wiki.req.EbookReq;
import com.chelsea.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    @Resource // jdk
    //@Autowired spring
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        // 把 ebookList 变成 ebookResp, 把 ebookResp 返回
        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) { // fori, iter
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp); // from ebook source copy to ebookResp target
            respList.add(ebookResp);
        }

        return respList;
    }

}
