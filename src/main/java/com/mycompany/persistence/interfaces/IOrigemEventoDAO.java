package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.domain.OrigemEvento;

public interface IOrigemEventoDAO extends IDAOComum<OrigemEvento, Long>{
	
	
	public List<OrigemEvento> getOrigemEventos();
	
}
