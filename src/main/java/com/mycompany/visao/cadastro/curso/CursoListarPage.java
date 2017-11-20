package com.mycompany.visao.cadastro.curso;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class CursoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="cursoServico")
	static ICursoServico cursoServico;
	
	
	public CursoListarPage(){
		super(new Curso());
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = cursoServico;
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

	
	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(350);
		modalIncluirEditar.setInitialWidth(600);
		
		modalIncluirEditar.setCloseButtonCallback(null);
		
		return modalIncluirEditar;
	}
	
	private void campoDuracao(){
		final NumberTextField<Integer> duracao = new NumberTextField<Integer>("duracao");
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
		
		CursoEditForm cadastroCursoEditForm = new CursoEditForm((Curso) abstractBean,panel,getFeedbackPanel(),getAtualizarListarPage(),getModalIncluirEditar());
		cadastroCursoEditForm.setOutputMarkupId(true);
		panel.add(cadastroCursoEditForm);
		return panel;
	}
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel((cursoServico.searchFechId(abstractBean))));
		getModalIncluirEditar().show(target);
	}

	
}
