package com.example.QuestionTwo.controller;

import java.util.List;

import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuestionTwo.entity.Stock;
import com.example.QuestionTwo.entity.SchedulerJobInfo;
import com.example.QuestionTwo.service.SchedulerJobService;


@RestController
@RequestMapping("/api")
public class JobController {
	private final SchedulerJobService scheduleJobService = new SchedulerJobService();

	@RequestMapping(value = "/initalizeJob", method = { RequestMethod.GET, RequestMethod.POST })
	public void scheduleNewJob(SchedulerJobInfo job) {
		try {
			scheduleJobService.initalizeJob(job);
		} catch (Exception e) {
			System.out.printf("updateCron ex:", e);
		}
		return;
	}
}