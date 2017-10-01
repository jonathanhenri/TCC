package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.DAOException;
import com.mycompany.domain.Materia;

public interface IMateriaDAO extends IDAOComum<Materia, Long>{
	
	public List<Materia> getMaterias();
	
	public Materia consultarPorIdFetch(Long id) throws DAOException;
	
}
