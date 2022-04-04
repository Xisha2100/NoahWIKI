package top.nzhz.wiki.mapper;

import top.nzhz.wiki.resp.StatisticResp;

import java.util.List;

public interface MySnapshotMapper {

    public void genSnapshot();

    public List<StatisticResp> getStatistic();
}
