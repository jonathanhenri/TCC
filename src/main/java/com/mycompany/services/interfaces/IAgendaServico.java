package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Agenda;
import com.mycompany.feedback.Retorno;

public interface  IAgendaServico extends IServiceComum<Agenda> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	
	public List<Agenda> search(Search search);

	@Override
	public Agenda searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(Agenda agenda);
	
	@Override
	public Retorno validaRegrasAntesAlterar(Agenda agenda);
	
	@Override
	public Retorno validaRegrasAntesRemover(Agenda agenda);
}
