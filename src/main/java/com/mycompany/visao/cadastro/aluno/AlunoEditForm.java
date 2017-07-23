package com.mycompany.visao.cadastro.aluno;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class AlunoEditForm extends EditForm<Aluno> {
	@SpringBean(name="alunoServico")
	private static IAlunoServico alunoServico;
	
	private String senhaAux;
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private Aluno aluno;
	
	
	public AlunoEditForm(Aluno aluno,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", aluno,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		senhaAux = aluno.getSenha();
		this.aluno = aluno;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = alunoServico;
	}

	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		return textFieldNome;
	}
	
	
	private TextField<String> criarCampoLogin(){
		TextField<String> textField = new TextField<String>("login");
		textField.setOutputMarkupId(true);
		return textField;
	}
	
	
	private PasswordTextField criarCampoSenha(){
		PasswordTextField passwordTextField = new PasswordTextField("senha");
		passwordTextField.setOutputMarkupId(true);
		
		return passwordTextField;
	}
	
	private DropDownChoice<Curso> criarCampoCurso(){
		IChoiceRenderer<Curso> choiceRenderer = new ChoiceRenderer<Curso>("nome", "id");
		LoadableDetachableModel<List<Curso>> cursos = new LoadableDetachableModel<List<Curso>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Curso> load() {
				List<Curso> cursos = new ArrayList<Curso>();
				
				cursos = cursoServico.search(new Search(Curso.class));
				
				return cursos;
			}
		};
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("administracao.curso", cursos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	
	private NumberTextField<Integer> criarCampoPeriodo(){
		NumberTextField<Integer> periodo = new NumberTextField<Integer>("periodo");
		periodo.setOutputMarkupId(true);
		return periodo;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoLogin());
		add(criarCampoSenha());
		add(criarCampoPeriodo());
		add(criarCampoCurso());
	}
	
	private static final long serialVersionUID = 1L;

	
}
