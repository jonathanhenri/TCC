package com.mycompany.services.interfaces;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Aluno;
import com.mycompany.feedback.Retorno;

public interface IServiceComum<T extends AbstractBean<?>> {

	public Retorno persist(T persist);
	
	public Retorno save(T save);
	
	public Retorno remove(T  remove) ;
	
	public T searchUnique(Search search);
	
	public int count(Search search);
	
	public List<T> search(Search search);
	
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean);
	
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado);
	
	public Retorno validaRegrasAntesIncluir(T salvar);
	
	public Retorno validaRegrasAntesAlterar(T alterar);
	
	public Retorno validaRegrasAntesRemover(T remover);
	
	public Retorno validaRegrasComuns(T object);

	public void searchComum(Search search);
	
	
	
}
