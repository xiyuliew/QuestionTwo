package com.example.QuestionTwo.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.example.QuestionTwo.job.CronJob;

@Configuration
public class SchedulerConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private QuartzProperties quartzProperties;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {

		SchedulerJobFactory jobFactory = new SchedulerJobFactory();
		jobFactory.setApplicationContext(applicationContext);

		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());

		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setDataSource(dataSource);
		factory.setQuartzProperties(properties);
		factory.setJobFactory(jobFactory);
		return factory;
	}
	
}