package top.nzhz.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.nzhz.wiki.domain.Content;
import top.nzhz.wiki.domain.Doc;
import top.nzhz.wiki.domain.DocExample;
import top.nzhz.wiki.exception.BusinessException;
import top.nzhz.wiki.exception.BusinessExceptionCode;
import top.nzhz.wiki.mapper.ContentMapper;
import top.nzhz.wiki.mapper.DocMapper;
import top.nzhz.wiki.mapper.MyDocMapper;
import top.nzhz.wiki.req.DocQueryReq;
import top.nzhz.wiki.req.DocSaveReq;
import top.nzhz.wiki.resp.DocQueryResp;
import top.nzhz.wiki.resp.PageResp;
import top.nzhz.wiki.utils.RedisUtil;
import top.nzhz.wiki.utils.RequestContext;
import top.nzhz.wiki.websocket.WebSocketServer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocService {
    //    @Autowired
    @Resource
    private DocMapper docMapper;

    @Resource
    private MyDocMapper myDocMapper;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    public RedisUtil redisUtil;

    @Resource
    public WebSocketService webSocketService;

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

    public List<DocQueryResp> all(Long musicId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andMusicIdEqualTo(musicId);
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
        doc.setViewCount(0);
        doc.setVoteCount(0);
        Content content = new Content();
        BeanUtils.copyProperties(req,content);
        if (ObjectUtils.isEmpty(req.getId())) {
            docMapper.insert(doc);
            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            docMapper.updateByPrimaryKey(doc);
            int count=contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if(count==0) {
                contentMapper.insert(content);
            }
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

    public String findContent(Long id){
        Content content=contentMapper.selectByPrimaryKey(id);
        //文档阅读+1
        myDocMapper.increaseViewCount(id);
        if(ObjectUtils.isEmpty(content)){
            return "";
        }
        return content.getContent();
    }

    public void vote(Long id){
        //点赞数+1
//        myDocMapper.increaseVoteCount(id);
        String ip = RequestContext.getRemoteAddr();
        if(redisUtil.validateRepeat("DOC_VOTE_"+id+"_"+ip,3600)){
            myDocMapper.increaseVoteCount(id);
        }else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

        Doc docDB=docMapper.selectByPrimaryKey(id);
        String logID= MDC.get("LOG_ID");
        webSocketService.sendInfo("["+docDB.getName()+"]被点赞",logID);
    }

    public void updateMusicInfo(){
        myDocMapper.updateMusicInfo();
    }
}

