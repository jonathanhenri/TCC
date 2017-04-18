package com.mycompany.visao.login;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.BadCredentialsException;

import com.mycompany.domain.Aluno;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.visao.cadastro.Cadastro;

public class Login extends WebPage {
	private static final long serialVersionUID = 1L;

	Aluno usuario = new Aluno();
	FeedbackPanel feedbackPanel;
	@SpringBean(name = "alunoDAO")
	IAlunoDAO alunoDAO;
	
	
	public Login() {
		adicionaCampos();		
	}

	private void adicionaCampos(){
		add(criarFormularioLogin());
		add(new FeedbackPanel("feedback"));
	}
	
	private Form<Aluno> criarFormularioLogin(){
		
		Form<Aluno> form = new Form<Aluno>("form"){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				Login.this.onFormSubmit();
			}
			
		};
		add(form);
		
		form.add(criarCampoLogin());
		form.add(criarCampoSenha());
		return form;
	}
	
	private TextField<String> criarCampoLogin(){		
		TextField<String> username = new TextField<String>("cpf", new PropertyModel<String>(usuario, "cpf"));
		username.setOutputMarkupId(true);
		username.setRequired(true);
		
		return username;
	}
	
	private PasswordTextField criarCampoSenha(){
		
		PasswordTextField senha = new PasswordTextField("senha", new PropertyModel<String>(usuario, "senha"));
		senha.setOutputMarkupId(true);
		senha.setRequired(true);
		return senha;
		
	}
	protected void onFormSubmit() {
		AuthenticatedWebSession session = AuthenticatedWebSession.get(); 
        try{
	        if(session.signIn(usuario.getCpf(), usuario.getSenha())) {
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

	}

	protected void setDefaultResponsePageIfNecessary() {
		redirectToInterceptPage(new Cadastro());
    	continueToOriginalDestination();
    } 
}


