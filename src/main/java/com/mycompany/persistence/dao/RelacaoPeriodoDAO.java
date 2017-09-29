package com.mycompany.persistence.dao;

import java.util.List;

import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.persistence.interfaces.IRelacaoPeriodoDAO;

public class RelacaoPeriodoDAO extends DAOComumHibernateImpl<RelacaoPeriodo, Long> implements IRelacaoPeriodoDAO{	

	public RelacaoPeriodoDAO() {
	}
	
	@Override
	public void remove(List<RelacaoPeriodo> listaRelacaoPeriodos) {
		if(listaRelacaoPeriodos!=null && listaRelacaoPeriodos.size()>0){
			for(RelacaoPeriodo relacaoPeriodo:listaRelacaoPeriodos){
				super.remove(relacaoPeriodo);
			}
		}
	}
	
	@Override
	public boolean persist(RelacaoPeriodo relacaoPeriodo) {
		return super.persist(relacaoPeriodo);
	};
	
	@Override
	public void persist(List<RelacaoPeriodo> listaRelacaoPeriodos) {
		if(listaRelacaoPeriodos!=null && listaRelacaoPeriodos.size()>0){
			for(RelacaoPeriodo relacaoPeriodo:listaRelacaoPeriodos){
				super.persist(relacaoPeriodo);
			}
		}
	}
}
