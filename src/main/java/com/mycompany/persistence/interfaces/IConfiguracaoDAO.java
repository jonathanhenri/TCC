package com.mycompany.persistence.interfaces;


import com.mycompany.DAOException;
import com.mycompany.domain.Configuracao;

public interface IConfiguracaoDAO extends IDAOComum<Configuracao, Long>{
	
	public Configuracao consultarPorIdFetch(Long id) throws DAOException;
}
