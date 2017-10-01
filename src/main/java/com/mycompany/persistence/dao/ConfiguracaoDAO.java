package com.mycompany.persistence.dao;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.DAOException;
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
    public Configuracao consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM Configuracao u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alua" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
                Configuracao configuracao= (Configuracao) resultado;
                return configuracao;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar saida de estoque por id: " + id, e);
        }
        return null;
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
