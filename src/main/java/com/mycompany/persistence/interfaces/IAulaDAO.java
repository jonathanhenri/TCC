package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.domain.Aula;

public interface IAulaDAO extends IDAOComum<Aula, Long>{
	
	public List<Aula> getAulas();
	
}
