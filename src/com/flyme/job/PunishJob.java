package com.flyme.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 计划任务
 */
public class PunishJob extends QuartzJobBean {
	/*@Autowired
	private IPunishService punishService;*/

	/* 业务实现 */
	public void work() {
		/*punishService.baseUpdate("init", map);
		System.out.println("初始化曝光台数据");*/
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		/*punishService = (IPunishService) arg0.getJobDetail().getJobDataMap().get("punishService");
		this.work();*/
	}

}