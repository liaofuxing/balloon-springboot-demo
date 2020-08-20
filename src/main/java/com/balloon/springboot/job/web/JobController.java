package com.balloon.springboot.job.web;

import com.balloon.springboot.core.rules.ResultVO;
import com.balloon.springboot.core.rules.ResultVOUtils;
import com.balloon.springboot.quartz.entity.QuartzJob;
import com.balloon.springboot.quartz.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {
	private final static Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

	@PostMapping("/add")
	public ResultVO<Object> save(QuartzJob quartz){
		logger.info("新增任务");
        jobService.saveJob(quartz);
		return ResultVOUtils.success(null);
	}

	@PostMapping("/edit")
	public ResultVO<Object> edit(QuartzJob quartz){
		logger.info("编辑任务");
		jobService.updateJob(quartz);
        return ResultVOUtils.success(null);
	}
//	@PostMapping("/list")
//	public PageInfo list(String jobName,Integer pageNo,Integer pageSize){
//		LOGGER.info("任务列表");
//		PageInfo pageInfo = jobService.listQuartzJob(jobName, pageNo, pageSize);
//		return pageInfo;
//	}

	@PostMapping("/trigger")
	public  ResultVO<Object> trigger(String jobName, String jobGroup) {
		logger.info("触发任务");
		jobService.triggerJob(jobName, jobGroup);
        return ResultVOUtils.success(null);
	}

	@PostMapping("/pause")
	public ResultVO<Object> pause(String jobName, String jobGroup) {
		logger.info("停止任务");
		jobService.pauseJob(jobName, jobGroup);
        return ResultVOUtils.success(null);
	}

	@PostMapping("/resume")
	public ResultVO<Object> resume(String jobName, String jobGroup) {
		logger.info("恢复任务");
		jobService.resumeJob(jobName, jobGroup);
        return ResultVOUtils.success(null);
	}

	@PostMapping("/remove")
	public  ResultVO<Object> remove(String jobName, String jobGroup) {
		logger.info("移除任务");
        jobService.removeJob(jobName, jobGroup);
        return ResultVOUtils.success(null);
	}
}
