package com.emsolution.readeid;

import java.text.ParseException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.emsolution.readeid.ReadDiagnosticPlJob;

/**
 * Application Lifecycle Listener implementation class StarteidReadBufferEid
 *
 */
public class StartEidReadBuffer implements ServletContextListener {
	private static Scheduler eidSchedl;
    /**
     * Default constructor. 
     */
    public StartEidReadBuffer() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	SchedulerFactory sf=new StdSchedulerFactory();
  	    try {
  	    	eidSchedl = sf.getScheduler();
			JobDetail jd=new JobDetail("jobeidEid","groupeidEid",ReadDiagnosticPlJob.class);
			//CronTrigger ct=new CronTrigger("cronTriggerPl","groupPl","0 17 9 * * ?");
			CronTrigger ct=new CronTrigger("croneidTriggerEid","groupeidEid","0 0 2,3 * * ?");
			//CronTrigger ct=new CronTrigger("cronTriggerPl","groupPl","0 33 11 * * ?");
			eidSchedl.scheduleJob(jd,ct);
			eidSchedl.start();
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
			eidSchedl.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
	
}
