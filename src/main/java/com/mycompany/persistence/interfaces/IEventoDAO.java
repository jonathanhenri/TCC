package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.domain.Evento;

public interface IEventoDAO extends IDAOComum<Evento, Long>{
	
	public List<Evento> getEventos();
	
}
