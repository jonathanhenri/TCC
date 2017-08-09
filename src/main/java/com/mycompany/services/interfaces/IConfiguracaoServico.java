package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Configuracao;
import com.mycompany.feedback.Retorno;

public interface  IConfiguracaoServico extends IServiceComum<Configuracao> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	
	public List<Configuracao> search(Search search);

	@Override
	public Configuracao searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(Configuracao configuracao);
	
	@Override
	public Retorno validaRegrasAntesAlterar(Configuracao configuracao);
	
	@Override
	public Retorno validaRegrasAntesRemover(Configuracao configuracao);

}
