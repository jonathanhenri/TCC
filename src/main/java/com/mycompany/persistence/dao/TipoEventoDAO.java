package com.mycompany.persistence.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.TipoEvento;
import com.mycompany.persistence.interfaces.ITipoEventoDAO;


public class TipoEventoDAO extends DAOComumHibernateImpl<TipoEvento, Long> implements ITipoEventoDAO{	

	public TipoEventoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(TipoEvento tipoEvento) {
		return super.persist(tipoEvento);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(TipoEvento tipoEvento) {
		return super.save(tipoEvento);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(TipoEvento tipoEvento) {
		return super.remove(tipoEvento);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<TipoEvento> getTipoEventos() {
		return super.findAll();
	}
	
}
