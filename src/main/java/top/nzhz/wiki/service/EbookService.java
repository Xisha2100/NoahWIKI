package top.nzhz.wiki.service;


import org.springframework.stereotype.Service;
import top.nzhz.wiki.domain.Ebook;
import top.nzhz.wiki.mapper.EbookMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
//    @Autowired
    private EbookMapper ebookMapper;

    public List<Ebook> list(){
        return ebookMapper.selectByExample(null);
    }
}

