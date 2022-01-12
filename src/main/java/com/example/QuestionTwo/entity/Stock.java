package com.example.QuestionTwo.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="DailyStockReport")
public class Stock implements Serializable{
	private @Id @GeneratedValue Long id;
	private int num_gainers;
	private int num_losers;
	private Calendar record_datetime;
	
	
	public Stock(){}
	
	public Stock(int num_gainers, int num_losers,Calendar record_datetime){
		this.num_gainers = num_gainers;
		this.num_losers = num_losers;
		this.record_datetime = record_datetime;
	}
	
	public int getNumGainers() {
		return this.num_gainers;
	}
	
	public void setNumGainers(int num_gainers) {
		this.num_gainers = num_gainers;
	}
	
	public int getNumLosers() {
		return this.num_losers;
	}
	
	public void setNumLosers(int num_losers) {
		this.num_losers = num_losers;
	}
	
	public Calendar getRecordDateTime() {
		return this.record_datetime;
	}
	
	public void setRecordDateTime(Calendar record_datetime) {
		this.record_datetime = record_datetime;
	}

}