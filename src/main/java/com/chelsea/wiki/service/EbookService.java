package com.chelsea.wiki.service;

import com.chelsea.wiki.domain.Ebook;
import com.chelsea.wiki.domain.EbookExample;
import com.chelsea.wiki.mapper.EbookMapper;
import com.chelsea.wiki.req.EbookReq;
import com.chelsea.wiki.resp.EbookResp;
import com.chelsea.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    @Resource // jdk
    //@Autowired spring
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        // 写死的条件。不管传什么参数，都会根据这个name去模糊查找
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //criteria.andNameLike("%" + req.getName() + "%");
        // 改成动态的SQL：如果传入name参数，就根据name去查找；如果没有，就不加name这个条件
        if (!ObjectUtils.isEmpty(req.getName())) { // 不为空
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        // 把 ebookList 变成 ebookResp, 把 ebookResp 返回

        /*
        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) { // fori, iter

            // 单体对象复制
            // EbookResp ebookResp = new EbookResp();
            // BeanUtils.copyProperties(ebook, ebookResp); // from ebook source copy to ebookResp target
            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);

            respList.add(ebookResp);
        }
        */

        // list 复制
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);

        return respList;
    }

}
