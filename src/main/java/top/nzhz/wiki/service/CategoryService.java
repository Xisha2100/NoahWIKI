package top.nzhz.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.nzhz.wiki.domain.Category;
import top.nzhz.wiki.domain.CategoryExample;
import top.nzhz.wiki.mapper.CategoryMapper;
import top.nzhz.wiki.req.CategoryQueryReq;
import top.nzhz.wiki.req.CategorySaveReq;
import top.nzhz.wiki.resp.CategoryQueryResp;
import top.nzhz.wiki.resp.PageResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    //    @Autowired
    @Resource
    private CategoryMapper categoryMapper;

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);


    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        if (!ObjectUtils.isEmpty(req.getName())) {
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<CategoryQueryResp> respList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryQueryResp categoryQueryResp = new CategoryQueryResp();
            BeanUtils.copyProperties(category, categoryQueryResp);
            respList.add(categoryQueryResp);
        }

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);


        return pageResp;
    }

    public List<CategoryQueryResp> all(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        if (!ObjectUtils.isEmpty(req.getName())) {
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> respList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryQueryResp categoryQueryResp = new CategoryQueryResp();
            BeanUtils.copyProperties(category, categoryQueryResp);
            respList.add(categoryQueryResp);
        }
        return respList;
    }

    public void save(CategorySaveReq req) {
        Category category = new Category();
        BeanUtils.copyProperties(req, category);
        if (ObjectUtils.isEmpty(req.getId())) {
            categoryMapper.insert(category);
        } else {
            categoryMapper.updateByPrimaryKey(category);
        }
    }


    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}

