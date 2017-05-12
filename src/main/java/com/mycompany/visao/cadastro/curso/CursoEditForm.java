package com.mycompany.visao.cadastro.curso;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.visao.comum.EditForm;

public class CursoEditForm extends EditForm {
	
	@SpringBean(name="cursoServico")
	private static ICursoServico cursoServico;
	
	private Curso curso;
	public CursoEditForm(String id, Curso curso,Panel editPanel) {
		super(id, curso,cursoServico,editPanel);
		this.curso = curso;
	}
	
	public CursoEditForm(Curso curso,Panel editPanel) {
		super("formCadastro", curso,cursoServico,editPanel);
		this.curso = curso;
	}

	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.setRequired(true);
		return textFieldNome;
	}
	
	
	private NumberTextField<Integer> criarCampoDuracao(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("duracao");
		duracao.setOutputMarkupId(true);
		duracao.setRequired(true);
		return duracao;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoDuracao());
	}
	
	private static final long serialVersionUID = 1L;

	
}
