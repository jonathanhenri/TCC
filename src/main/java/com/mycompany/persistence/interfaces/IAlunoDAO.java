package com.mycompany.persistence.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.DAOException;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Arquivo;

public interface IAlunoDAO extends IDAOComum<Aluno, Long>{
	
	public List<Aluno> search(Search search);
	
	public List<Aluno> getalunos();
	
	public Aluno alunoTrue(Aluno aluno);
	
	public Boolean persist(Arquivo arquivo);

	public Boolean save(Arquivo arquivo);
	
	public Boolean remove(Arquivo arquivo);
	
	public Aluno consultarPorIdFetch(Long id) throws DAOException;
}
