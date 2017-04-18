package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.feedback.Retorno;

public interface  IOrigemEventoServico extends IServiceComum<OrigemEvento> {

	static final int DEFAUL_TIMEOUT = 1200;
	public List<OrigemEvento> search(Search search);

	@Override
	public OrigemEvento searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(OrigemEvento origemEvento);
	
	@Override
	public Retorno validaRegrasAntesAlterar(OrigemEvento origemEvento);
	
	@Override
	public Retorno validaRegrasAntesRemover(OrigemEvento origemEvento);
	
	
	public List<OrigemEvento> getOrigemEventos();
	

}
