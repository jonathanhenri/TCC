package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;
import com.mycompany.feedback.Retorno;

public interface  ICodigoAlunoServico extends IServiceComum<CodigoAluno> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<CodigoAluno> search(Search search);
	
	
	@Override
	public CodigoAluno searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(CodigoAluno codigoAluno);
	
	@Override
	public Retorno validaRegrasAntesAlterar(CodigoAluno codigoAluno);
	
	@Override
	public Retorno validaRegrasAntesRemover(CodigoAluno codigoAluno);
	
	public Retorno persist(List<CodigoAluno> listaCodigosAluno);
	
	public CodigoAluno verificarCodigoAtivo(String codigo,CodigoAluno codigoAluno);
	
	public List<CodigoAluno> gerarCodigosAluno(CodigoAluno codigoAluno);
}
