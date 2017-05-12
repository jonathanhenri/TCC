package com.mycompany.visao.cadastro;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.visao.comum.Menu;
public class Cadastro extends Menu{
	private static final long serialVersionUID = 1L;
	Aluno aluno = new Aluno();
	
	Form<Aluno> form;
	FeedbackPanel feedbackPanel;
	
	@SpringBean(name="alunoServico")
	private IAlunoServico alunoServico;
	
	@SpringBean(name="codigoAlunoServico")
	private ICodigoAlunoServico codigoAlunoServico;
	
	@SpringBean(name="cursoServico")
	private ICursoServico cursoServico;

	public Cadastro(){
		feedbackPanel = new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this));
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		form = new Form<Aluno>("form",new CompoundPropertyModel<Aluno>(aluno));
		
		final TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		
		final TextField<String> login = new TextField<String>("cpf");
		login.setOutputMarkupId(true);
		
		final PasswordTextField senha = new PasswordTextField("senha");
		senha.setOutputMarkupId(true);
		
		final TextField<String> email = new TextField<String>("email");
		email.setOutputMarkupId(true);
		
		form.setOutputMarkupId(true);
		
		
		form.add(nome);
		form.add(login);
		form.add(senha);
		form.add(email);
		
		
		form.add(new AjaxButton("submit") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form2) {
				super.onSubmit(target, form2);
					Curso curso = new Curso();
					curso.setNome("Sistemas de Informação");
					curso.setDuracao(4);
					curso.setModalidade(Curso.MODALIDADE_ANUAL);
					
					cursoServico.persist(curso);
					
					aluno.setCurso(curso);
					
					alunoServico.persist(aluno);
					aluno = new Aluno();
					nome.setDefaultModelObject("");
					target.add(form);
			}
		});
		addOrReplace(form);
	}
	
	
}

