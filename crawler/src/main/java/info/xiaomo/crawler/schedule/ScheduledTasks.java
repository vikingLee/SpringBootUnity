package info.xiaomo.crawler.schedule;

import info.xiaomo.crawler.model.ShikigamiModel;
import info.xiaomo.crawler.service.ShikigamaService;
import info.xiaomo.crawler.spider.OnnmyoujiSpider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 小莫 (https://xiaomo.info) (https://github.com/syoubaku)
 * @created : 2016/12/24 15:59
 */
@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private final ShikigamaService shikigamaService;

    @Autowired
    public ScheduledTasks(ShikigamaService shikigamaService) {
        this.shikigamaService = shikigamaService;
    }


    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime() {
        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat().format(new Date()));
    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron() {
        List<ShikigamiModel> shikigamiModel = OnnmyoujiSpider.getShikigamiModel();
        shikigamiModel.forEach(shikigamaService::save);
        LOGGER.debug("开始执行任务：");
    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

}
