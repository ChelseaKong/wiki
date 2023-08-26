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

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        // 写死的条件。不管传什么参数，都会根据这个name去模糊查找
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        //criteria.andNameLike("%" + req.getName() + "%");
        // 改成动态的SQL：如果传入name参数，就根据name去查找；如果没有，就不加name这个条件

        if (!ObjectUtils.isEmpty(req.getName())) { // 不为空
            criteria.andNameLike("%" + req.getName() + "%");
        }

        // 支持分页
        PageHelper.startPage(req.getPage(), req.getSize()); // 请求参数
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList); // 返回参数：当前页的列表内容
        LOG.info("Total Line Number: {}", pageInfo.getTotal()); // 返回参数：总行数
        LOG.info("Total Page Number: {}", pageInfo.getPages());

        // 把 categoryList 变成 categoryResp, 把 categoryResp 返回

        /*
        List<CategoryResp> respList = new ArrayList<>();
        for (Category category : categoryList) { // fori, iter

            // 单体对象复制
            // CategoryResp categoryResp = new CategoryResp();
            // BeanUtils.copyProperties(category, categoryResp); // from category source copy to categoryResp target
            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);

            respList.add(categoryResp);
        }
        */

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
