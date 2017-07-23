package com.mycompany.persistence.interfaces;



import java.util.List;

import com.mycompany.domain.CodigoAluno;

public interface ICodigoAlunoDAO extends IDAOComum<CodigoAluno, Long>{
	
	public CodigoAluno verificarCodigoAtivo(String codigo,CodigoAluno codigoAluno);
	
	public List<CodigoAluno> gerarCodigosAluno(CodigoAluno codigoAluno);
}
