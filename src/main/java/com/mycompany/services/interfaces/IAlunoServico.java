package com.mycompany.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Arquivo;
import com.mycompany.feedback.Retorno;

@Controller
public interface  IAlunoServico extends IServiceComum<Aluno> {

	static final int DEFAUL_TIMEOUT = 1200;
	
	public List<Aluno> search(Search search);

	
	@Override
	public Aluno searchUnique(Search search);
	
	@Override
	public Retorno validaRegrasAntesIncluir(Aluno aluno);
	
	@Override
	public Retorno validaRegrasAntesAlterar(Aluno aluno);
	
	@Override
	public Retorno validaRegrasAntesRemover(Aluno aluno);
	
	public Retorno persist(Arquivo arquivo);

	public Retorno save(Arquivo arquivo);
	
	public Retorno remove(Arquivo arquivo);
}
