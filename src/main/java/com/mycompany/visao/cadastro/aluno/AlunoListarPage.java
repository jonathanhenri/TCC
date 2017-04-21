package com.mycompany.visao.cadastro.aluno;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;
import com.mycompany.visao.comum.MensagemExcluirPanel;

public class AlunoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="alunoServico")
	static IAlunoServico alunoServico;
	
	static Aluno aluno = new Aluno();
	
	public AlunoListarPage(){
		super(aluno,10,alunoServico);
		addFiltros();
	}
	
	private void campoNome(){
		final TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		form.add(nome);
	}
	
	private void campoCpf(){
		final TextField<String> cpf = new TextField<String>("cpf");
		cpf.setOutputMarkupId(true);
		form.add(cpf);
	}
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel(abstractBean));
		getModalIncluirEditar().show(target);
	}
	
	private void campoCurso(){
		final TextField<String> cpf = new TextField<String>("curso.nome");
		cpf.setOutputMarkupId(true);
		form.add(cpf);
	}
	
	
	private void addFiltros(){
		campoCpf();
		campoNome();
		campoCurso();
	}


	private Panel criarPanel(AbstractBean<?> abstractBean){
		CadastroAlunoPanel editPanel = new CadastroAlunoPanel(getModalIncluirEditar().getContentId());
		editPanel.setOutputMarkupId(true);
		getForm().add(editPanel);
		
		CadastroAlunoEditForm cadastroAlunoEditForm = new CadastroAlunoEditForm((Aluno) abstractBean,editPanel){
			private static final long serialVersionUID = 1L;

			protected void executarAoEditar(AjaxRequestTarget target) {
				target.add(getAtualizarListarPage());
				getModalIncluirEditar().close(target);
			};
			
			protected void executarAoSalvar(AjaxRequestTarget target) {
				target.add(getAtualizarListarPage());
				getModalIncluirEditar().close(target);
			};
		};
		cadastroAlunoEditForm.setOutputMarkupId(true);
		editPanel.add(cadastroAlunoEditForm);
		
		return editPanel;
	}
	
	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new Aluno()));
		getModalIncluirEditar().show(target);
	}
}
