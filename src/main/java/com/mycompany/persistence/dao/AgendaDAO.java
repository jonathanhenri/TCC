package com.mycompany.persistence.dao;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.DAOException;
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
    public Agenda consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM Agenda u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alua" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
            	Agenda saidaEstoque= (Agenda) resultado;
                return saidaEstoque;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar saida de estoque por id: " + id, e);
        }
        return null;
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
