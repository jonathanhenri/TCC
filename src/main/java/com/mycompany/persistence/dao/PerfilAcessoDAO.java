package com.mycompany.persistence.dao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.PerfilAcesso;
import com.mycompany.persistence.interfaces.IPerfilAcessoDAO;


public class PerfilAcessoDAO extends DAOComumHibernateImpl<PerfilAcesso, Long> implements IPerfilAcessoDAO{	

	public PerfilAcessoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(PerfilAcesso perfilAcesso) {
		return super.persist(perfilAcesso);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(PerfilAcesso perfilAcesso) {
		return super.save(perfilAcesso);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(PerfilAcesso perfilAcesso) {
		return super.remove(perfilAcesso);
	}
	
}
