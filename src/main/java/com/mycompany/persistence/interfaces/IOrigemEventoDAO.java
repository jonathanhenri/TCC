package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.DAOException;
import com.mycompany.domain.OrigemEvento;

public interface IOrigemEventoDAO extends IDAOComum<OrigemEvento, Long>{
	
	public OrigemEvento consultarPorIdFetch(Long id) throws DAOException;
	
	public List<OrigemEvento> getOrigemEventos();
	
}
