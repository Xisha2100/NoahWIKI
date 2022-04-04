package top.nzhz.wiki.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.nzhz.wiki.service.SnapshotService;
import top.nzhz.wiki.utils.SnowFlake;

import javax.annotation.Resource;

@Component
public class SnapshotJob {

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotJob.class);

    @Resource
    private SnapshotService snapshotService;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 自定义cron表达式跑批
     * 只有等上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     */
    @Scheduled(cron = "0 0 4 * * ? ")
    public void cron() {
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        LOG.info("生成今日音乐快照");
        long start=System.currentTimeMillis();
        snapshotService.genSnapshot();
        LOG.info("快照生成结束，耗时：{}毫秒",System.currentTimeMillis()-start);
    }

}
