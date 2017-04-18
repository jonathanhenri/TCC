package com.mycompany.persistence.interfaces;



import java.util.List;

import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;

public interface ICodigoAlunoDAO extends IDAOComum<CodigoAluno, Long>{
	
	public CodigoAluno verificarCodigoAtivo(String codigo);
	
	public List<CodigoAluno> gerarCodigosAluno(int quantidade,Curso curso);
	
	public CodigoAluno utilizarCodigoAluno(String codigo);
	
}
