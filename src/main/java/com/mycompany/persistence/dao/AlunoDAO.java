package com.mycompany.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.DAOException;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Arquivo;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.persistence.interfaces.IAlunoDAO;

public class AlunoDAO extends DAOComumHibernateImpl<Aluno, Long> implements IAlunoDAO{	

	public AlunoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(Aluno aluno) {
		return super.persist(aluno);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<Aluno> search(Search search) {
		return super.search(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
    public Aluno consultarPorIdFetch(Long id) throws DAOException {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatorio.");
        }
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" SELECT u" +
                       " FROM Aluno u" +
                       " JOIN FETCH u.administracao a" +
                       " LEFT JOIN FETCH u.perfilAcesso pf" +
                       " LEFT JOIN FETCH u.configuracao cf" +
                       " LEFT JOIN FETCH a.curso cur" +
                       " LEFT JOIN FETCH a.aluno alua" +
                       " WHERE u.id = :id");

            Query query = getSession().createQuery(hql.toString());

            query.setParameter("id", id);

            Object resultado = null;

            resultado = query.uniqueResult();

            if(resultado!=null){
                Aluno aluno = (Aluno) resultado;
                return aluno;
            }

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar saida de estoque por id: " + id, e);
        }
        return null;
    }
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(Aluno aluno) {
		return super.save(aluno);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public Boolean persist(Arquivo arquivo) {
		_persist(arquivo);
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public Boolean save(Arquivo arquivo) {
		_merge(arquivo);
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public Boolean remove(Arquivo arquivo) {
		_deleteById(Arquivo.class, arquivo.getIdentifier());
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(Aluno aluno) {
		return super.remove(aluno);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public void remove(List<RelacaoPeriodo> listaRelacaoPeriodos){
		super._deleteEntities(listaRelacaoPeriodos);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<Aluno> getalunos() {
		return super.findAll();
	}

	public Aluno alunoTrue(Aluno aluno){
		Search searchAluno = new Search(Aluno.class);
		searchAluno.addFilterEqual("login", aluno.getLogin());
		searchAluno.addFilterEqual("senha", aluno.getSenha());
		Aluno alunoAux =  (Aluno) _searchUnique(searchAluno);
	
		if(alunoAux!=null){
			return (Aluno) alunoAux; 
		}
		
		return null;
	}
	

	
	
}
