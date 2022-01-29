package top.nzhz.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.nzhz.wiki.domain.Doc;
import top.nzhz.wiki.domain.DocExample;
import top.nzhz.wiki.mapper.DocMapper;
import top.nzhz.wiki.req.DocQueryReq;
import top.nzhz.wiki.req.DocSaveReq;
import top.nzhz.wiki.resp.DocQueryResp;
import top.nzhz.wiki.resp.PageResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocService {
    //    @Autowired
    @Resource
    private DocMapper docMapper;

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);


    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
//        if (!ObjectUtils.isEmpty(req.getName())) {
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<DocQueryResp> respList = new ArrayList<>();

        for (Doc doc : docList) {
            DocQueryResp docQueryResp = new DocQueryResp();
            BeanUtils.copyProperties(doc, docQueryResp);
            respList.add(docQueryResp);
        }

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);


        return pageResp;
    }

    public List<DocQueryResp> all() {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");

        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> respList = new ArrayList<>();

        for (Doc doc : docList) {
            DocQueryResp docQueryResp = new DocQueryResp();
            BeanUtils.copyProperties(doc, docQueryResp);
            respList.add(docQueryResp);
        }
        return respList;
    }

    public void save(DocSaveReq req) {
        Doc doc = new Doc();
        BeanUtils.copyProperties(req, doc);
        if (ObjectUtils.isEmpty(req.getId())) {
            docMapper.insert(doc);
        } else {
            docMapper.updateByPrimaryKey(doc);
        }
    }


    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids){
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }
}

