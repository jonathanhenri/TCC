package com.mycompany.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.DAOException;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.persistence.interfaces.IRelacaoPeriodoDAO;

public class RelacaoPeriodoDAO extends DAOComumHibernateImpl<RelacaoPeriodo, Long> implements IRelacaoPeriodoDAO{	

	public RelacaoPeriodoDAO() {
	}
	
	@Override
	public void remove(List<RelacaoPeriodo> listaRelacaoPeriodos) {
		if(listaRelacaoPeriodos!=null && listaRelacaoPeriodos.size()>0){
			for(RelacaoPeriodo relacaoPeriodo:listaRelacaoPeriodos){
				super.remove(relacaoPeriodo);
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
    public RelacaoPeriodo consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM RelacaoPeriodo u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alua" +
                       " LEFT JOIN FETCH u.aluno aluu" +
                       " LEFT JOIN FETCH u.materia mat" +
                       " LEFT JOIN FETCH u.evento av" +
                       " LEFT JOIN FETCH u.agenda ag" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
                RelacaoPeriodo relacaoPeriodo = (RelacaoPeriodo) resultado;
                return relacaoPeriodo;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar registro por id: " + id, e);
        }
        return null;
    }
	
	@Override
	public boolean persist(RelacaoPeriodo relacaoPeriodo) {
		return super.persist(relacaoPeriodo);
	};
	
	@Override
	public void persist(List<RelacaoPeriodo> listaRelacaoPeriodos) {
		if(listaRelacaoPeriodos!=null && listaRelacaoPeriodos.size()>0){
			for(RelacaoPeriodo relacaoPeriodo:listaRelacaoPeriodos){
				super.persist(relacaoPeriodo);
			}
		}
	}
}
