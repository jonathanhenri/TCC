package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.feedback.Retorno;

public interface  IPerfilAcessoServico extends IServiceComum<PerfilAcesso> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<PerfilAcesso> search(Search search);
	

	@Override
	public PerfilAcesso searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(PerfilAcesso perfilAcesso);
	
	@Override
	public Retorno validaRegrasAntesAlterar(PerfilAcesso perfilAcesso);
	
	@Override
	public Retorno validaRegrasAntesRemover(PerfilAcesso perfilAcesso);
	
	
	

}
