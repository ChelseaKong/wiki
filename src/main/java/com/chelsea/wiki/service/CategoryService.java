package com.chelsea.wiki.service;

import com.chelsea.wiki.domain.Category;
import com.chelsea.wiki.domain.CategoryExample;
import com.chelsea.wiki.mapper.CategoryMapper;
import com.chelsea.wiki.req.CategoryQueryReq;
import com.chelsea.wiki.req.CategorySaveReq;
import com.chelsea.wiki.resp.CategoryQueryResp;
import com.chelsea.wiki.resp.PageResp;
import com.chelsea.wiki.util.CopyUtil;
import com.chelsea.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    @Resource // jdk
    //@Autowired spring
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    // 全部列出
    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        // list 复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);


        return respList;
    }

    // 分页
    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getName())) { // 不为空
            criteria.andNameLike("%" + req.getName() + "%");
        }

        // 支持分页
        PageHelper.startPage(req.getPage(), req.getSize()); // 请求参数
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList); // 返回参数：当前页的列表内容
        LOG.info("Total Line Number: {}", pageInfo.getTotal()); // 返回参数：总行数
        LOG.info("Total Page Number: {}", pageInfo.getPages());

        // list 复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    // 保存
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if ( ObjectUtils.isEmpty(req.getId()) ) {
            // 保存 新增的结果
            // 新增ID的算法：①自增 ②uuid ③雪花算法
            category.setId(snowFlake.nextId());
            //category.setDocCount(0);
            //category.setViewCount(0);
            //category.setVoteCount(0);
            categoryMapper.insert(category);
        } else {
            // 保存 Edit的结果
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    // 删除
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
