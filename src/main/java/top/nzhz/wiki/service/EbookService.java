package top.nzhz.wiki.service;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.nzhz.wiki.domain.Ebook;
import top.nzhz.wiki.domain.EbookExample;
import top.nzhz.wiki.mapper.EbookMapper;
import top.nzhz.wiki.req.EbookReq;
import top.nzhz.wiki.resp.EbookResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

    @Resource
//    @Autowired
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = new ArrayList<>();

        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook,ebookResp);
            respList.add(ebookResp);
        }
        return respList;
    }
}

