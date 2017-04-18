package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Materia;
import com.mycompany.feedback.Retorno;

public interface  IMateriaServico extends IServiceComum<Materia> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<Materia> search(Search search);

	@Override
	public Materia searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(Materia materia);
	
	@Override
	public Retorno validaRegrasAntesAlterar(Materia materia);
	
	@Override
	public Retorno validaRegrasAntesRemover(Materia materia);
	
	public List<Materia> getMaterias();
	

}
