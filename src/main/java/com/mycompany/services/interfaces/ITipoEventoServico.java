package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.TipoEvento;
import com.mycompany.feedback.Retorno;

public interface  ITipoEventoServico extends IServiceComum<TipoEvento> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<TipoEvento> search(Search search);

	@Override
	public TipoEvento searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(TipoEvento tipoEvento);
	
	@Override
	public Retorno validaRegrasAntesAlterar(TipoEvento tipoEvento);
	
	@Override
	public Retorno validaRegrasAntesRemover(TipoEvento tipoEvento);
	
	
	public List<TipoEvento> getTipoEventos();
	

}
