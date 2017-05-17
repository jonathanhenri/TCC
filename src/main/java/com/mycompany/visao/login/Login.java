package com.mycompany.visao.login;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.BadCredentialsException;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.visao.cadastro.Cadastro;

public class Login extends WebPage {
	private static final long serialVersionUID = 1L;

	Aluno aluno = new Aluno();
	FeedbackPanel feedbackPanel;
	
	@SpringBean(name = "alunoServico")
	IAlunoServico alunoServico;
	
	public Login() {
		adicionaCampos();		
	}

	private void adicionaCampos(){
		add(criarFormularioLogin());
		add(criarFeedbackPanel());
	}
	
	private FeedbackPanel criarFeedbackPanel(){
		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		
		return feedbackPanel;
	}
		
	
	private AjaxButton criarBotaoLogin(){
		AjaxButton ajaxButton = new AjaxButton("login") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				AuthenticatedWebSession session = AuthenticatedWebSession.get(); 
		        try{
			        if(session.signIn(aluno.getCpf(), aluno.getSenha())) {
			        	try{
			        		session.replaceSession();
			        	}catch(Exception e){
			        		error("Erro ao tentar logar, tente novamente mais tarde");
			        		e.printStackTrace();
			        		return;
			        	}
			            setDefaultResponsePageIfNecessary(); 
			        } else { 
			            error(getString("ERRO! Senha ou Username não encontrado"));
			        }
		        }catch(BadCredentialsException e){
		        	error(getString(e.getLocalizedMessage()));
		        }catch(AccessDeniedException e){
		        	getSession().error(getString("ERRO! O usuário já está logado"));
		        	redirectToInterceptPage(new Login());
				}
		        for(FeedbackMessage feedbackMessage:getFeedbackMessages()){
		        	target.appendJavaScript("$.notify('"+feedbackMessage.getMessage()+"', \"error\");");
		        }
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			  for(FeedbackMessage feedbackMessage:getFeedbackMessages()){
		        	target.appendJavaScript("$.notify('"+feedbackMessage.getMessage()+"', \"error\");");
		        }
			  	target.add(feedbackPanel);
				super.onError(target, form);
			}
		};
		
		
		return ajaxButton;
	}
	
	private Form<Aluno> criarFormularioLogin(){
		
		Form<Aluno> form = new Form<Aluno>("form");
		form.setOutputMarkupId(true);
		add(form);
		
		form.add(criarCampoLogin());
		form.add(criarCampoSenha());
		form.add(criarBotaoLogin());
		return form;
	}
	
	private TextField<String> criarCampoLogin(){		
		TextField<String> username = new TextField<String>("cpf", new PropertyModel<String>(aluno, "cpf"));
		username.setOutputMarkupId(true);
		username.setRequired(true);
		
		return username;
	}
	
	private PasswordTextField criarCampoSenha(){
		
		PasswordTextField senha = new PasswordTextField("senha", new PropertyModel<String>(aluno, "senha"));
		senha.setOutputMarkupId(true);
		senha.setRequired(true);
		return senha;
		
	}

	protected void setDefaultResponsePageIfNecessary() {
		Search search = new Search(Aluno.class);
		search.addFilterEqual("cpf", aluno.getCpf());
		aluno = alunoServico.searchUnique(search);
		aluno.addContadorAcesso();
    	alunoServico.save(aluno);
		redirectToInterceptPage(new Cadastro());
    	continueToOriginalDestination();
    
    } 
}


