package com.mycompany.persistence.interfaces;


import com.mycompany.DAOException;
import com.mycompany.domain.PerfilAcesso;

public interface IPerfilAcessoDAO extends IDAOComum<PerfilAcesso, Long>{
	
	public PerfilAcesso consultarPorIdFetch(Long id) throws DAOException;
	
}
