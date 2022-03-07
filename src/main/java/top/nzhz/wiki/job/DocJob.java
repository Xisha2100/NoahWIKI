package top.nzhz.wiki.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.nzhz.wiki.service.DocService;
import top.nzhz.wiki.utils.SnowFlake;

import javax.annotation.Resource;

@Component
public class DocJob {

    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Resource
    private DocService docService;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 自定义cron表达式跑批
     * 只有等上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void cron() {
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        LOG.info("更新每条音乐下的文档数据");
        long start=System.currentTimeMillis();
        docService.updateMusicInfo();
        LOG.info("更新文档数据，耗时：{}毫秒",System.currentTimeMillis()-start);
    }

}
