package top.nzhz.wiki.service;


import org.springframework.stereotype.Service;
import top.nzhz.wiki.domain.Demo;
import top.nzhz.wiki.mapper.DemoMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {

    @Resource
//    @Autowired
    private DemoMapper demoMapper;

    public List<Demo> list(){
        return demoMapper.selectByExample(null);
    }
}

