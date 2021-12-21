package top.nzhz.wiki.service;


import org.springframework.stereotype.Service;
import top.nzhz.wiki.domain.Test;
import top.nzhz.wiki.mapper.TestMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
//    @Autowired
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list();
    }
}

