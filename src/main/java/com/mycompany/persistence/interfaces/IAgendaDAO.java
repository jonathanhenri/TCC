package com.mycompany.persistence.interfaces;


import com.mycompany.DAOException;
import com.mycompany.domain.Agenda;

public interface IAgendaDAO extends IDAOComum<Agenda, Long>{
	
	public Agenda consultarPorIdFetch(Long id) throws DAOException;
}
