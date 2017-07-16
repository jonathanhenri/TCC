package com.mycompany.persistence.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.Aluno;
import com.mycompany.domain.ContadorAcesso;
import com.mycompany.persistence.interfaces.IContadorAcessoDAO;


public class ContadorAcessoDAO extends DAOComumHibernateImpl<ContadorAcesso, Long> implements IContadorAcessoDAO{	

	public ContadorAcessoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(ContadorAcesso contadorAcesso) {
		return super.persist(contadorAcesso);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(ContadorAcesso contadorAcesso) {
		return super.save(contadorAcesso);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(ContadorAcesso contadorAcesso) {
		return super.remove(contadorAcesso);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<ContadorAcesso> getAcessos(Aluno aluno) {
		//TODO FAZER SEARCH
		return super.findAll();
	}

	
}
