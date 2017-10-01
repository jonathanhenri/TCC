package com.mycompany.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.DAOException;
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
    public TipoEvento consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM TipoEvento u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alua" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
                TipoEvento tipoevento = (TipoEvento) resultado;
                return tipoevento;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar saida de estoque por id: " + id, e);
        }
        return null;
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
