package com.emsolution.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javassist.compiler.ast.Stmnt;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.entity.CrmProposta;
import com.emsolution.entity.EmsProposta;
import com.emsolution.entity.EmsStatusOportunidade;
import com.emsolution.entity.TwFilial;
import com.emsolution.entity.TwFuncionario;



public class JobBuscarRejeitadoZoho implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		ResultSet rs = null;
		PreparedStatement prstmtUpdate = null;
		try {
			manager = JpaUtil.getInstance();
			con = ConnectionPostgre.getConnecton();
			String SQL = "SELECT "+
					 " documento_numero,"+
					 " nome_cliente,"+
					 " cpf_cnpj,"+
					 " local,"+
					 " aos_cuidados,"+
					 " telefone,"+
					 " email,"+
					 " objeto_proposta,"+
					 " modelo,"+
					 " serie,"+
					 " horimetro,"+
					 " id_equipamento,"+
					 " equip_periodo_garantia,"+
					 " valor_reparo_apos_falha_inicial,"+
					 " total_servicos_inicial,"+
					 " total_pecas_inicial,"+
					 " total_geral_inicial,"+
					 " valor_reparo_apos_falha,"+
					 " total_servicos,"+
					 " total_pecas,"+
					 " total_geral,"+
					 " status,"+
					 " consultor,"+
					 " email_consultor,"+
					 " filial,"+
					 " motivo_venda_perdida,"+
					 " id_beleti_negocio,"+
					 " data_criacao,"+
					 " data_exportacao_crm,"+
					 " data_fechamento,"+
					 " data_update_beleti,"+
					 " data_update_crm,"+
					 " id_negocio_crm,"+
					 " url_proposta,"+
					 " analista,"+
					 " email_analista,"+
					 " origem_negocio,"+
					 " motivo_rejeicao"+
					" FROM "+
					 " beleti_negocio  where status = 10";
			prstmt = con.createStatement();
			rs = prstmt.executeQuery(SQL);
			Query query = null;
			while(rs.next()){
				Integer idCrm = rs.getInt("documento_numero");
				String motivoRejeicao = rs.getString("motivo_rejeicao");
				CrmProposta crmProposta = manager.find(CrmProposta.class, idCrm.longValue());
				EmsProposta proposta = manager.find(EmsProposta.class, crmProposta.getIdEmsProposta());
				SQL = "update beleti_negocio set status = 11  where documento_numero = "+idCrm;
				prstmtUpdate = con.prepareStatement(SQL);
				prstmtUpdate.executeUpdate();
				//prstmtUpdate.executeUpdate(SQL);
				query = manager.createQuery("from EmsStatusOportunidade where sigla = 'REJ'");
				EmsStatusOportunidade emsStatusOportunidade = (EmsStatusOportunidade)query.getSingleResult();
				manager.getTransaction().begin();
				proposta.setIdStatusOpt(emsStatusOportunidade);
				proposta.setMotivoRejeicao(motivoRejeicao);
				manager.merge(proposta);
				query = manager.createNativeQuery("update EMS_PROPOSTA set ESTIMATE_BY_FUNCIONARIO_LOCK = null where NUM_SERIE = '"+proposta.getNumSerie()+"'");
				query.executeUpdate();
				manager.getTransaction().commit();
				TwFilial filial = manager.find(TwFilial.class, proposta.getFilial());
				TwFuncionario funcionario = manager.find(TwFuncionario.class, proposta.getIdFuncionario());
				new EmailHelper().sendSimpleMail(funcionario.getEplsnm()+" a proposta "+proposta.getId()+" do equipamento de serie "+proposta.getNumSerie()+" de modelo "+proposta.getModelo()+" foi rejeitada pelo Zoho.", filial.getStnm()+" Proposta Rejeitada Zoho. "+rs.getString("motivo_rejeicao"), funcionario.getEmail());
			}


	} catch (Exception e) {
		if(manager != null && manager.getTransaction().isActive()){
			manager.getTransaction().rollback();
		}
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		new EmailHelper().sendSimpleMail("Erro ao rodar rotina de buscar rejeitado no zoho "+writer.toString(), "Erro rotina de buscar rejeitado no zoho", "rodrigo@rdrsistemas.com.br");

	}finally{
		try {
			con.close();
			prstmt.close();
			prstmtUpdate.close();
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
public static void main(String[] args) {
	System.out.println("0CJN02888".substring(0,4));
}
}
