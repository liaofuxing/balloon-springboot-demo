package com.balloon.springboot.job;


import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @author liaofuxing
 * @date 2020/08/20 11:35
 **/
public class TestJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestJob.class);


    @Override
    protected void executeInternal(JobExecutionContext context) {

        LOGGER.info("TestJob 正在执行");


        LOGGER.info("TestJob 执行完成");
    }
}
