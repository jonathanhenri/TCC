package com.mycompany.persistence.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.RelacaoPeriodo;

public interface IRelacaoPeriodoDAO extends IDAOComum<RelacaoPeriodo, Long>{
	
	public List<RelacaoPeriodo> search(Search search);
	
	public void remove(List<RelacaoPeriodo> listaRelacaoPeriodos);
	
	public void persist(List<RelacaoPeriodo> listaRelacaoPeriodos);
	
}
