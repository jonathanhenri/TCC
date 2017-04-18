package com.mycompany.persistence.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.persistence.interfaces.ICursoDAO;


public class CursoDAO extends DAOComumHibernateImpl<Curso, Long> implements ICursoDAO{	

	public CursoDAO() {
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(Curso curso) {
		return super.persist(curso);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(Curso curso) {
		return super.save(curso);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(Curso curso) {
		return super.remove(curso);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<Curso> getcursos() {
		return super.findAll();
	}



	@Override
	public List<Materia> materiasPorCurso(Curso curso) {
		Search search = new Search(Materia.class);
		search.addFilterEqual("curso.id", curso.getId());
		return _search(search);
	}
	
	
}
