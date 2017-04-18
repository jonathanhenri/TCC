package com.mycompany.persistence.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.OrigemEvento;
import com.mycompany.persistence.interfaces.IOrigemEventoDAO;


public class OrigemEventoDAO extends DAOComumHibernateImpl<OrigemEvento, Long> implements IOrigemEventoDAO{	

	public OrigemEventoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(OrigemEvento origemEvento) {
		return super.persist(origemEvento);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(OrigemEvento origemEvento) {
		return super.save(origemEvento);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(OrigemEvento origemEvento) {
		return super.remove(origemEvento);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<OrigemEvento> getOrigemEventos() {
		return super.findAll();
	}

	
}
