package com.emsolution.readcombustivel;

import java.text.ParseException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Application Lifecycle Listener implementation class StartReadBufferPL
 *
 */
public class StartCombReadBufferPL implements ServletContextListener {
	private static Scheduler combSched;
    /**
     * Default constructor. 
     */
    public StartCombReadBufferPL() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	SchedulerFactory sf=new StdSchedulerFactory();
  	    try {
			combSched = sf.getScheduler();
			JobDetail jd=new JobDetail("jobCombPl","groupCombPl",ReadCombustivelPlJob.class);
			//CronTrigger ct=new CronTrigger("cronTriggerPl","groupPl","0 0 4,12,20 * * ?");
			CronTrigger ct=new CronTrigger("cronCombTriggerPl","groupCombPl","0 30 1,2 * * ?");
			//CronTrigger ct=new CronTrigger("cronTriggerPl","groupPl","0 33 11 * * ?");
			combSched.scheduleJob(jd,ct);
			combSched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	try {
			combSched.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
   
	
}
