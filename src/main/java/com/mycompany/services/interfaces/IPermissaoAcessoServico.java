package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Retorno;

public interface  IPermissaoAcessoServico extends IServiceComum<PermissaoAcesso> {
	static final int DEFAUL_TIMEOUT = 1200;
	
	
	public List<PermissaoAcesso> search(Search search);

	@Override
	public PermissaoAcesso searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(PermissaoAcesso permissaoAcesso);
	
	@Override
	public Retorno validaRegrasAntesAlterar(PermissaoAcesso permissaoAcesso);
	
	@Override
	public Retorno validaRegrasAntesRemover(PermissaoAcesso permissaoAcesso);
	
	public List<PermissaoAcesso> getPermissoesAcesso();
	

}
