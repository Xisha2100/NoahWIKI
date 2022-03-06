package top.nzhz.wiki.service;


import org.springframework.stereotype.Service;
import top.nzhz.wiki.mapper.MySnapshotMapper;

import javax.annotation.Resource;

@Service
public class SnapshotService {
    //    @Autowired
    @Resource
    private MySnapshotMapper mySnapshotMapper;

    public void genSnapshot(){
        mySnapshotMapper.genSnapshot();
    }

}

