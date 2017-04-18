package com.mycompany.persistence.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Arquivo;
import com.mycompany.feedback.Retorno;
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
	public List<Aluno> getalunos() {
		return super.findAll();
	}

	public Aluno alunoTrue(Aluno aluno){
		Search searchAluno = new Search(Aluno.class);
		searchAluno.addFilterEqual("cpf", aluno.getCpf());
		searchAluno.addFilterEqual("senha", aluno.getSenha());
		Aluno alunoAux =  (Aluno) _searchUnique(searchAluno);
	
		if(alunoAux!=null){
			return (Aluno) alunoAux; 
		}
		
		return null;
	}
	

	
	
}
