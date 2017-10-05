package com.mycompany.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.DAOException;
import com.mycompany.domain.Evento;
import com.mycompany.persistence.interfaces.IEventoDAO;


public class EventoDAO extends DAOComumHibernateImpl<Evento, Long> implements IEventoDAO{	

	public EventoDAO() {
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(Evento evento) {
		return super.persist(evento);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
    public Evento consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM Evento u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH u.tipoEvento tp" +
                       " LEFT JOIN FETCH u.materia mt" +
                       " LEFT JOIN FETCH u.agenda ag" +
                       " LEFT JOIN FETCH u.origemEvento og" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alua" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
                Evento evento= (Evento) resultado;
                return evento;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar registro por id: " + id, e);
        }
        return null;
    }
    
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(Evento evento) {
		return super.save(evento);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(Evento evento) {
		return super.remove(evento);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<Evento> getEventos() {
		return super.findAll();
	}

	
}
