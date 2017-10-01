package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.DAOException;
import com.mycompany.domain.PermissaoAcesso;

public interface IPermissaoAcessoDAO extends IDAOComum<PermissaoAcesso, Long>{
	
	public List<PermissaoAcesso> getPermissoesAcesso();
	
	public PermissaoAcesso consultarPorIdFetch(Long id) throws DAOException;
	
	
}
