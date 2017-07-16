package com.mycompany.persistence.interfaces;


import java.util.List;

import com.mycompany.domain.Aluno;
import com.mycompany.domain.ContadorAcesso;

public interface IContadorAcessoDAO extends IDAOComum<ContadorAcesso, Long>{
	
	public List<ContadorAcesso> getAcessos(Aluno aluno);
	
}
