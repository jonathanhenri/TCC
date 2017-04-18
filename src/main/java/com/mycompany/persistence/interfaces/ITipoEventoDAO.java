package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.domain.TipoEvento;

public interface ITipoEventoDAO extends IDAOComum<TipoEvento, Long>{
	
	public List<TipoEvento> getTipoEventos();
	
}
