package com.mycompany.persistence.dao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Agenda;
import com.mycompany.persistence.interfaces.IAgendaDAO;


public class AgendaDAO extends DAOComumHibernateImpl<Agenda, Long> implements IAgendaDAO{	

	public AgendaDAO() {
	}
	
	
//	private void fetchAdministracao(Agenda agenda){
//		Search search = new Search(Administracao.class);
//		search.addFetch("aluno");
//		search.addFetch("curso");
//		search.addFilterEqual("id", agenda.getAdministracao().getId());
//		
//		Administracao administracao = _searchUnique(search);
//	}
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
