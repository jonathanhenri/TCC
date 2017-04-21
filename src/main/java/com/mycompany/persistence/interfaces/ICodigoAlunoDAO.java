package com.mycompany.persistence.interfaces;



import java.util.List;

import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;

public interface ICodigoAlunoDAO extends IDAOComum<CodigoAluno, Long>{
	
	public CodigoAluno verificarCodigoAtivo(String codigo,Curso curso);
	
	public List<CodigoAluno> gerarCodigosAluno(int quantidade,Curso curso);
	
	public CodigoAluno utilizarCodigoAluno(String codigo,Curso curso);
	
}
