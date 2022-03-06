package top.nzhz.wiki.service;


import org.springframework.stereotype.Service;
import top.nzhz.wiki.mapper.MySnapshotMapper;
import top.nzhz.wiki.resp.StatisticResp;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SnapshotService {
    //    @Autowired
    @Resource
    private MySnapshotMapper mySnapshotMapper;

    public void genSnapshot(){
        mySnapshotMapper.genSnapshot();
    }

    public List<StatisticResp> getStatistic(){
        return mySnapshotMapper.getStatistic();
    }

}

