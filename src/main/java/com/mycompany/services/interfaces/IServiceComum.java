package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.feedback.Retorno;

public interface IServiceComum<AbstractBean> {

	public Retorno persist(AbstractBean persist);
	
	public Retorno save(AbstractBean save);
	
	public Retorno remove(AbstractBean  remove);
	
	public AbstractBean searchUnique(Search search);
	
	public int count(Search search);
	
	public List<AbstractBean> search(Search search);
	
	public Retorno validaRegrasAntesIncluir(AbstractBean salvar);
	
	public Retorno validaRegrasAntesAlterar(AbstractBean alterar);
	
	public Retorno validaRegrasAntesRemover(AbstractBean remover);
	
}
