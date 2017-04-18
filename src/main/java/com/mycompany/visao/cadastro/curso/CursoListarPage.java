package com.mycompany.visao.cadastro.curso;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class CursoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="cursoServico")
	static ICursoServico cursoServico;
	
	static Curso curso = new Curso();
	
	public CursoListarPage(){
		super(curso,10,cursoServico);
		addFiltros();
	}
	
	private void campoDuracao(){
		final TextField<String> duracao = new TextField<String>("duracao");
		duracao.setOutputMarkupId(true);
		form.add(duracao);
	}
	
	private void campoNome(){
		final TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		form.add(nome);
	}
	
	
	private void addFiltros(){
		campoNome();
		campoDuracao();
	}
	
}
