package com.emsolution.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.business.DiagnosticoBusiness;


public class JobUpdateDiagnostico implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			DiagnosticoBusiness business = new DiagnosticoBusiness(null);
			business.saveDiagnosticJob(-1L, "");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
