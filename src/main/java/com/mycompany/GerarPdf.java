package com.mycompany;

import java.io.Serializable;
import java.util.List;

import com.mycompany.domain.Aluno;

public class GerarPdf  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public GerarPdf(){}

	public void gerarPdf(List<Aluno> lista){
		try{
//			System.out.println("Gerando relatório...");
//			
//			JasperReport report = JasperCompileManager.compileReport("relatorio/relatorioUsuarios.jrxml");
//			JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(lista, false); // coloca false no jrxml para espaços vazios
//			JasperPrint print = JasperFillManager.fillReport(report, null, jrbcds);
//			JasperExportManager.exportReportToPdfFile(print, "relatorio/relatorioUsuarios.pdf"); 
//			
//			System.out.println("Relatório gerado.");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
