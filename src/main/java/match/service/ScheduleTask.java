package match.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author wangjian
 */
@Configuration
@EnableScheduling
public class ScheduleTask {
    @Scheduled(cron = "0/10 * * * * ? ")
    public void match(){

    }
}
