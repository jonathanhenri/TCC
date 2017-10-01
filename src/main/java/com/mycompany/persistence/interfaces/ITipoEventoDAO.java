package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.DAOException;
import com.mycompany.domain.TipoEvento;

public interface ITipoEventoDAO extends IDAOComum<TipoEvento, Long>{
	
	public List<TipoEvento> getTipoEventos();
	
	public TipoEvento consultarPorIdFetch(Long id) throws DAOException;
	
}
