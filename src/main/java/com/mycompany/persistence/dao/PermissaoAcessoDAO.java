package com.mycompany.persistence.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.persistence.interfaces.IPermissaoAcessoDAO;


public class PermissaoAcessoDAO extends DAOComumHibernateImpl<PermissaoAcesso, Long> implements IPermissaoAcessoDAO{	

	public PermissaoAcessoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(PermissaoAcesso permissaoAcesso) {
		return super.persist(permissaoAcesso);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(PermissaoAcesso permissaoAcesso) {
		return super.save(permissaoAcesso);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(PermissaoAcesso permissaoAcesso) {
		return super.remove(permissaoAcesso);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<PermissaoAcesso> getPermissoesAcesso() {
		return super.findAll();
	}

	
}
