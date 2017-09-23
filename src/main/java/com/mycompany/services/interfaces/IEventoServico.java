package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Evento;
import com.mycompany.feedback.Retorno;

public interface  IEventoServico extends IServiceComum<Evento> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	
	public Retorno remove(List<Evento> listaEventos);
	
	public List<Evento> search(Search search);

	@Override
	public Evento searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(Evento evento);
	
	@Override
	public Retorno validaRegrasAntesAlterar(Evento evento);
	
	@Override
	public Retorno validaRegrasAntesRemover(Evento evento);
	
	
	public List<Evento> getEventos();
	

}
