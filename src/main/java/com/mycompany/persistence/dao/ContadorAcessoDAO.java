package com.mycompany.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.DAOException;
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
    public ContadorAcesso consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM ContadorAcesso u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alua" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
                ContadorAcesso contadorAcesso = (ContadorAcesso) resultado;
                return contadorAcesso;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar saida de estoque por id: " + id, e);
        }
        return null;
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
