package com.mycompany.persistence.interfaces;

import java.util.List;

import com.mycompany.DAOException;
import com.mycompany.domain.RelacaoPeriodo;

public interface IRelacaoPeriodoDAO extends IDAOComum<RelacaoPeriodo, Long>{
	
	public void remove(List<RelacaoPeriodo> listaRelacaoPeriodos);
	
	public void persist(List<RelacaoPeriodo> listaRelacaoPeriodos);
	
	public RelacaoPeriodo consultarPorIdFetch(Long id) throws DAOException;
	
}
