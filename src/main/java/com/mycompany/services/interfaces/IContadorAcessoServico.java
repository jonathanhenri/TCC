package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.ContadorAcesso;
import com.mycompany.feedback.Retorno;

public interface  IContadorAcessoServico extends IServiceComum<ContadorAcesso> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	
	public List<ContadorAcesso> search(Search search);

	@Override
	public ContadorAcesso searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(ContadorAcesso contadorAcesso);
	
	@Override
	public Retorno validaRegrasAntesAlterar(ContadorAcesso contadorAcesso);
	
	@Override
	public Retorno validaRegrasAntesRemover(ContadorAcesso contadorAcesso);
	
	
	public List<ContadorAcesso> getAcessos(Aluno aluno);
	

}
