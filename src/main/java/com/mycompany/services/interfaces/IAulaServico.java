package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aula;
import com.mycompany.feedback.Retorno;

public interface  IAulaServico extends IServiceComum<Aula> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<Aula> search(Search search);

	@Override
	public Aula searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(Aula aula);
	
	@Override
	public Retorno validaRegrasAntesAlterar(Aula aula);
	
	@Override
	public Retorno validaRegrasAntesRemover(Aula aula);
	
	public List<Aula> getAulas();
	

}
