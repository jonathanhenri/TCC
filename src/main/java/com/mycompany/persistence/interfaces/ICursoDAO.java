package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.DAOException;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;

public interface ICursoDAO extends IDAOComum<Curso, Long>{
	
	public List<Materia> materiasPorCurso(Curso curso);
	
	public List<Curso> getcursos();
	
	public Curso consultarPorIdFetch(Long id) throws DAOException;
	
}
