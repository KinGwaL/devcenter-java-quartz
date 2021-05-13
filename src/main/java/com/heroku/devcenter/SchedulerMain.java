package com.heroku.devcenter;


import com.rabbitmq.client.MessageProperties;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerMain {

    final static Logger logger = LoggerFactory.getLogger(SchedulerMain.class);
    
    public static void main(String[] args) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        JobDetail jobDetail = newJob(DailyScheduleJob.class).build();
        
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(repeatSecondlyForever(5))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static class DailyScheduleJob implements Job {
        
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date();
            date.setTime(timestamp.getTime());
            String formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);
            logger.info("DailyScheduleJob: Create CSV + SFTP = Executed - %s", formattedDate);
            // try {
                
            // }
            // catch (Exception e) {
                
            // }

        }
        
    }

}
