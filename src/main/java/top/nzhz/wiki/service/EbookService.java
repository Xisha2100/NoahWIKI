package top.nzhz.wiki.service;


import org.springframework.stereotype.Service;
import top.nzhz.wiki.domain.Ebook;
import top.nzhz.wiki.domain.EbookExample;
import top.nzhz.wiki.mapper.EbookMapper;
import top.nzhz.wiki.req.EbookReq;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
//    @Autowired
    private EbookMapper ebookMapper;

    public List<Ebook> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+req.getName()+"%");
        return ebookMapper.selectByExample(ebookExample);
    }
}

