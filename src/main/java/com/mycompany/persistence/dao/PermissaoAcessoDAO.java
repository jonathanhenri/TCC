package com.mycompany.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.DAOException;
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
    public PermissaoAcesso consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM PermissaoAcesso u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alu" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
                PermissaoAcesso permissaoAcesso = (PermissaoAcesso) resultado;
                return permissaoAcesso;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar saida de estoque por id: " + id, e);
        }
        return null;
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
