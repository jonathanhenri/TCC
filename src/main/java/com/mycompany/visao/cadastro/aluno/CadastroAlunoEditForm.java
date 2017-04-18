package com.mycompany.visao.cadastro.aluno;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.Aluno;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.visao.comum.EditForm;

public class CadastroAlunoEditForm extends EditForm {
	@SpringBean(name="alunoServico")
	private static IAlunoServico alunoServico;
	
	private Aluno aluno;
	public CadastroAlunoEditForm(String id, Aluno aluno) {
		super(id, aluno,alunoServico);
		this.aluno = aluno;
		adicionarCampos();
	}

	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.setRequired(true);
		return textFieldNome;
	}
	
	
	private TextField<String> criarCampoLogin(){
		TextField<String> textFieldLogin = new TextField<String>("login");
		textFieldLogin.setOutputMarkupId(true);
		textFieldLogin.setRequired(true);
		return textFieldLogin;
	}
	
	private PasswordTextField criarCampoSenha(){
		PasswordTextField passwordTextField = new PasswordTextField("senha");
		passwordTextField.setOutputMarkupId(true);
		passwordTextField.setRequired(true);
		
		return passwordTextField;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoLogin());
		add(criarCampoSenha());
	}
	
	private static final long serialVersionUID = 1L;

	
}
