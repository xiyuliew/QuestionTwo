package com.example.QuestionTwo.job;

import java.util.Calendar;
import java.util.Iterator;
import java.sql.*;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@DisallowConcurrentExecution
public class CronJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	int totalgainer = 0;
    	int totalloser = 0;
        System.out.println("CronJob Start................");
        JSONParser parser = new JSONParser();
        try {
        	Object obj = parser.parse(new FileReader("data.json"));
        	JSONArray jsonArray  = (JSONArray) obj;
        	Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                Object obj1 = iterator.next();
                int i = (Integer) obj1;
            if(i >= 0) {
            	totalgainer += 1;
            }else {
            	totalloser += 1;
            }
            insertRecord(totalgainer,totalloser);
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("CronJob End................");
    }
    
    private void insertRecord(int totalgainer,int totalloser) {
    	final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:mem:testdb";

        //  Database credentials
        final String USER = "admin";
        final String PASS = "password";

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            Calendar today = Calendar.getInstance();
            String sql = "INSERT INTO DAILY_STOCK_REPORT " + "VALUES (" +  totalgainer + "," + totalloser + "," + today + ")";

            stmt.executeUpdate(sql);

            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            
            try {
                if (stmt!=null)
                    conn.close();
            } catch (SQLException se) {
            } 
            try {
                if (conn!=null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } 
        } 

    } 
} 
 
