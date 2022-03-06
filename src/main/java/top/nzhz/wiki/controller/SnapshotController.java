package top.nzhz.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.nzhz.wiki.resp.CommonResp;
import top.nzhz.wiki.resp.StatisticResp;
import top.nzhz.wiki.service.SnapshotService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/music-snapshot")
public class SnapshotController {

    @Resource
    private SnapshotService snapshotService;

    @GetMapping("/get-statistic")
    public CommonResp getStatistic(){
        List<StatisticResp> statisticResps=snapshotService.getStatistic();
        CommonResp<List<StatisticResp>> commonResp=new CommonResp<>();
        commonResp.setContent(statisticResps);
        return commonResp;
    }

}
