package com.emsolution.alerta;

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
public class StartAlertaReadBufferPL implements ServletContextListener {
	private static Scheduler combSched;
    /**
     * Default constructor. 
     */
    public StartAlertaReadBufferPL() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	SchedulerFactory sf=new StdSchedulerFactory();
  	    try {
			combSched = sf.getScheduler();
			JobDetail jd=new JobDetail("jobCombPl","groupCombPlAlerta",ReadAlertaPlJob.class);
			CronTrigger ct=new CronTrigger("cronTriggerPlAlerta","groupPlAlerta","0 0 6,12,20,13 * * ?");
			//CronTrigger ct=new CronTrigger("cronCombTriggerPlPl","groupCombPlPl","0 25 * * * ?");
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
