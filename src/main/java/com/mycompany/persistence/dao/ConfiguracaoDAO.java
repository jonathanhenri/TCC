package com.mycompany.persistence.dao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.Configuracao;
import com.mycompany.persistence.interfaces.IConfiguracaoDAO;


public class ConfiguracaoDAO extends DAOComumHibernateImpl<Configuracao, Long> implements IConfiguracaoDAO{	

	public ConfiguracaoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(Configuracao configuracao) {
		return super.persist(configuracao);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(Configuracao configuracao) {
		return super.save(configuracao);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(Configuracao configuracao) {
		return super.remove(configuracao);
	}
	
}
