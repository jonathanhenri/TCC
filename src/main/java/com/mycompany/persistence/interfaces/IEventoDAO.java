package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.DAOException;
import com.mycompany.domain.Evento;

public interface IEventoDAO extends IDAOComum<Evento, Long>{
	
	public List<Evento> getEventos();
	
    public Evento consultarPorIdFetch(Long id) throws DAOException;
	
}
