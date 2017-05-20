package com.mycompany.visao.cadastro.curso;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IServiceComum;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class CursoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="cursoServico")
	static ICursoServico cursoServico;
	
	static Curso curso = new Curso();
	
	public CursoListarPage(){
		super(curso);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = cursoServico;
	}

	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(300);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
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

	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new Curso()));
		getModalIncluirEditar().show(target);
		
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		CursoPanel panel = new CursoPanel(getModalIncluirEditar().getContentId());
		panel.setOutputMarkupId(true);
		getForm().add(panel);
		
		CursoEditForm cadastroCursoEditForm = new CursoEditForm((Curso) abstractBean,panel){
			private static final long serialVersionUID = 1L;
			
			protected void executarAoSalvar(AjaxRequestTarget target) {
				target.add(getAtualizarListarPage());
				getModalIncluirEditar().close(target);
			};
			
			@Override
			protected void executarAoEditar(AjaxRequestTarget target) {
				target.add(getAtualizarListarPage());
				getModalIncluirEditar().close(target);
			}
		};
		cadastroCursoEditForm.setOutputMarkupId(true);
		panel.add(cadastroCursoEditForm);
		return panel;
	}
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel(abstractBean));
		getModalIncluirEditar().show(target);
	}

	
}
