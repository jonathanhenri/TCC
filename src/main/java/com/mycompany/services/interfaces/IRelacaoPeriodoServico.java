package com.mycompany.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.RelacaoPeriodo;

@Controller
public interface  IRelacaoPeriodoServico extends IServiceComum<RelacaoPeriodo>{

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<RelacaoPeriodo> search(Search search);
	
	public RelacaoPeriodo searchUnique(Search search);
	
	public void remove(List<RelacaoPeriodo> listaRelacaoPeriodos);
	
	public void persist(List<RelacaoPeriodo> listaRelacaoPeriodos);
	
}
