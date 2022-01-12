package com.example.QuestionTwo.service;

import java.util.Date;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.example.QuestionTwo.component.JobScheduleCreator;
import com.example.QuestionTwo.entity.SchedulerJobInfo;
import com.example.QuestionTwo.job.CronJob;
import com.example.QuestionTwo.repository.StockRepository;


@Transactional
@Service
public class SchedulerJobService {
	
	private static final String GROUP_NAME = "DailyCronJob";	
	
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private JobScheduleCreator scheduleCreator;

	public void initalizeJob(SchedulerJobInfo scheduleJob) throws Exception {
		scheduleJob.setJobClass(CronJob.class.getName());
		scheduleJob.setCronJob(true);
		scheduleNewJob(scheduleJob);
		scheduleJob.setInterfaceName("interface_" + scheduleJob.getJobId());
		System.out.printf(">>>>> jobName = [ CronJob ]" + " created.");
	}
	
	
	private void scheduleNewJob(SchedulerJobInfo jobInfo) {
		try {
			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
			Scheduler scheduler = schedFact.getScheduler();
			jobInfo.setJobName("CronJob");
			JobDetail jobDetail = JobBuilder
					.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
					.withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
			if (!scheduler.checkExists(jobDetail.getKey())) {

				scheduler.start();
				
				// define the job and tie it to our HelloJob class
				JobBuilder jobBuilder = JobBuilder.newJob(CronJob.class);
				
				JobDetail jobDetail1 = jobBuilder.usingJobData("CronJob", "com.example.QuestionTwo.job")	
						.withIdentity("CronJob", "group1")
						.build();
				
				scheduler.scheduleJob(jobDetail1, fireEverydayMidnight());
				jobInfo.setJobStatus("SCHEDULED");
				stockRepository.save(jobInfo);
				System.out.printf(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled.");
			} else {
				System.out.printf("scheduleNewJobRequest.jobAlreadyExist");
			}
		} catch (ClassNotFoundException e) {
			System.out.printf("Class Not Found - {}", jobInfo.getJobClass(), e);
		} catch (SchedulerException e) {
			System.out.printf(e.getMessage(), e);
		}
	}
	
	
	public static Trigger fireEverydayMidnight() {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("fireEverydayMidnight", GROUP_NAME)
				.withSchedule(cronSchedule("Fire 12AM every day",
						"0 0 0 * * ?"))		
				.build();
		return trigger;
	}
	
	private static CronScheduleBuilder cronSchedule(String desc, String cronExpression) {
		System.out.println(desc + "->(" + cronExpression + ")");
		return CronScheduleBuilder.cronSchedule(cronExpression);
	}
}
	
	
	
	
	
	