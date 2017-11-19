package com.mycompany.visao.cadastro.aluno;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;

public class AlunoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="alunoServico")
	static IAlunoServico alunoServico;
	
	static Aluno aluno = new Aluno();
	
	public AlunoListarPage(){
		super(aluno,60);
		addFiltros();
	}

	@Override
	protected void setServicoComum() {
		serviceComum = alunoServico;
	}
	
	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(600);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
	}
	

	
	private void campoNome(){
		final TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		form.add(nome);
	}
	
	private void campoLogin(){
		final TextField<String> login = new TextField<String>("login");
		login.setOutputMarkupId(true);
		form.add(login);
	}
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel((alunoServico.searchFechId(abstractBean))));
		getModalIncluirEditar().show(target);
	}
	
	private void campoCurso(){
		final TextField<String> curso = new TextField<String>("administracao.curso.nome");
		curso.setOutputMarkupId(true);
		form.add(curso);
	}
	
	
	private void addFiltros(){
		campoLogin();
		campoNome();
		campoCurso();
	}


	private Panel criarPanel(AbstractBean<?> abstractBean){
		AlunoPanel editPanel = new AlunoPanel(getModalIncluirEditar().getContentId());
		editPanel.setOutputMarkupId(true);
		getForm().add(editPanel);
		
		AlunoEditForm cadastroAlunoEditForm = new AlunoEditForm((Aluno) abstractBean,editPanel,getFeedbackPanel(),getAtualizarListarPage(),getModalIncluirEditar());
		cadastroAlunoEditForm.setOutputMarkupId(true);
		editPanel.add(cadastroAlunoEditForm);
		
		return editPanel;
	}
	
	
	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new Aluno()));
		getModalIncluirEditar().show(target);
	}

	@Override
	protected ModalWindow criarModalFiltros() {
		modalFiltros = new ModalWindow("modalFiltros");
		modalFiltros.setOutputMarkupId(true);
		modalFiltros.setInitialHeight(400);
		modalFiltros.setInitialWidth(500);
		
		modalFiltros.setCloseButtonCallback(null);
		
		return modalFiltros;
	}
}
