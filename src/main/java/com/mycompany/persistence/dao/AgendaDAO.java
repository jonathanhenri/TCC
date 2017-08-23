package com.mycompany.persistence.dao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.Agenda;
import com.mycompany.persistence.interfaces.IAgendaDAO;


public class AgendaDAO extends DAOComumHibernateImpl<Agenda, Long> implements IAgendaDAO{	

	public AgendaDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(Agenda agenda) {
		return super.persist(agenda);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(Agenda agenda) {
		return super.save(agenda);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(Agenda agenda) {
		return super.remove(agenda);
	}
}
