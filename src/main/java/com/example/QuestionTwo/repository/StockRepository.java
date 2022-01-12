package com.example.QuestionTwo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuestionTwo.entity.SchedulerJobInfo;

@Repository
public interface StockRepository extends JpaRepository<SchedulerJobInfo, Long> {

	SchedulerJobInfo findByJobName(String jobName);

}