package com.emsolution.util;

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
 * Application Lifecycle Listener implementation class ListenerEmSolution
 *
 */
public class ListenerEmSolution implements ServletContextListener {
	//private static Scheduler  schedJobRemoverCotacao;
	private static Scheduler schedFindOrcamento;
	private static Scheduler schedJobSubTributariaDBS;
	private static Scheduler schedJobImportarConsultor;
	private static Scheduler schedJobAtualizarModelo;
	private static Scheduler schedJobUpdateDiagnostico;
	private static Scheduler schedJobBuscarRejeitadoZoho;
    /**
     * Default constructor. 
     */
    public ListenerEmSolution() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	SchedulerFactory sf=new StdSchedulerFactory();
    	
    	try {
//			schedJobRemoverCotacao = sf.getScheduler(); //verifica se a Cotacao foi removida do DBS
//			JobDetail removerCotacaoDBS=new JobDetail("jobRemoverCotacao","removerCotacaoDBS",JobRemoverCotacao.class);
//			CronTrigger removerCot=new CronTrigger("cronTriggerSchedJobRemoverCotacao","removerCotDBS","30 0/1 * * * ?");
//			schedJobRemoverCotacao.scheduleJob(removerCotacaoDBS,removerCot);
//			schedJobRemoverCotacao.start();
			
			schedFindOrcamento = sf.getScheduler();
			JobDetail jd=new JobDetail("jobOrcamento","groupOrcamento",JobFindOrcamento.class);
			CronTrigger ct=new CronTrigger("cronTriggerOrcamento","groupOrcamento","0 0/1 * * * ?");
			schedFindOrcamento.scheduleJob(jd,ct);
			schedFindOrcamento.start();
			
    		//Verifica se a substiyuição tributária já retornou do DBS DBS
    		schedJobSubTributariaDBS = sf.getScheduler();
    		JobDetail subTribuDbs=new JobDetail("jobsubTribuDbs","vsubTribuDbs",JobSubTributariaDbs.class);
    		CronTrigger subTribuCDBS=new CronTrigger("cronTriggersubTribuDbs","subTribuDbs","20 0/1 * * * ?");
    		schedJobSubTributariaDBS.scheduleJob(subTribuDbs,subTribuCDBS);
    		schedJobSubTributariaDBS.start();
    		
    		schedJobImportarConsultor = sf.getScheduler();
    		JobDetail importConsultor=new JobDetail("jobImportarConsultor","jobImportar",JobImportarConsultor.class);
    		CronTrigger importConsultorDBS=new CronTrigger("cronTriggerImportarConsultor","importarDbs","0 30 3 * * ?");
    		schedJobImportarConsultor.scheduleJob(importConsultor,importConsultorDBS);
    		schedJobImportarConsultor.start();
    		
    		schedJobAtualizarModelo = sf.getScheduler();
    		JobDetail atualizarModelo = new JobDetail("jobAtualizarModelo","jobIAtualizar",JobAtualizarModelo.class);
    		CronTrigger atualizarModeloDBS=new CronTrigger("cronTriggerAtualizarModelo","atualizarDbs","0 10 4,5 * * ?");
    		//CronTrigger atualizarModeloDBS = new CronTrigger("cronTriggerAtualizarModelo","atualizarDbs","0 37 9 * * ?");
    		schedJobAtualizarModelo.scheduleJob(atualizarModelo,atualizarModeloDBS);
    		schedJobAtualizarModelo.start();

    		schedJobUpdateDiagnostico = sf.getScheduler();
    		JobDetail atualizarUpdateDiagnostico = new JobDetail("jobAtualizarUpdateDiagnostico","jobIUpdateDiagnostico",JobUpdateDiagnostico.class);
    		//CronTrigger atualizarUpdateDiagnosticoDBS = new CronTrigger("cronTriggerAtualizarUpdateDiagnostico","atualizarUpdateDiagnosticoDbs","0 30 3 * * ?");
    		CronTrigger atualizarUpdateDiagnosticoDBS = new CronTrigger("cronTriggerAtualizarUpdateDiagnostico","atualizarUpdateDiagnosticoDbs","0 38 10 * * ?");
    		
    		//CronTrigger atualizarUpdateDiagnosticoDBS = new CronTrigger("cronTriggerAtualizarModelo","atualizarDbs","0 32 * * * ?");
    		schedJobUpdateDiagnostico.scheduleJob(atualizarUpdateDiagnostico,atualizarUpdateDiagnosticoDBS);
    		schedJobUpdateDiagnostico.start();
    		
    		
    		schedJobBuscarRejeitadoZoho = sf.getScheduler();
    		JobDetail buscarRejeitadoZoho = new JobDetail("jobBuscarRejeitadoZoho","jobIBuscarRejeitadoZoho",JobBuscarRejeitadoZoho.class);
    		CronTrigger BuscarRejeitadoZohoDBS = new CronTrigger("cronTriggerBuscarRejeitadoZoho","BuscarRejeitadoZohoDbs","0 0/5 * * * ?");
    		//CronTrigger atualizarUpdateDiagnosticoDBS = new CronTrigger("cronTriggerAtualizarModelo","atualizarDbs","0 32 * * * ?");
    		schedJobBuscarRejeitadoZoho.scheduleJob(buscarRejeitadoZoho,BuscarRejeitadoZohoDBS);
    		schedJobBuscarRejeitadoZoho.start();
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	try {
			//schedJobRemoverCotacao.shutdown();
			schedFindOrcamento.shutdown();
			schedJobSubTributariaDBS.shutdown();
			schedJobImportarConsultor.shutdown();
			schedJobAtualizarModelo.shutdown();
			schedJobUpdateDiagnostico.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
	
}
