package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.feedback.Retorno;

public interface  ICursoServico extends IServiceComum<Curso> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<Curso> search(Search search);

	@Override
	public Curso searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(Curso curso);
	
	@Override
	public Retorno validaRegrasAntesAlterar(Curso curso);
	
	@Override
	public Retorno validaRegrasAntesRemover(Curso curso);
	
	public List<Materia> materiasPorCurso(Curso curso);
	
	public List<Curso> getcursos();
	

}
