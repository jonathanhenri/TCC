package com.mycompany.visao.cadastro.perfilAcesso;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.services.interfaces.IPerfilAcessoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class PerfilAcessoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="perfilAcessoServico")
	static IPerfilAcessoServico perfilAcessoServico;
	
	static PerfilAcesso perfilAcesso = new PerfilAcesso();
	
	public PerfilAcessoListarPage(){
		super(perfilAcesso);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = perfilAcessoServico;
	}
	
	private void addFiltros(){
		form.add(criarCampoNome());
	}
	

	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(800);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
	}
	
	private TextField<String> criarCampoNome(){
		final TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		return nome;
	}
	
	
	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new PerfilAcesso()));
		getModalIncluirEditar().show(target);
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		PerfilAcessoPanel perfilAcessoPanel = new PerfilAcessoPanel(getModalIncluirEditar().getContentId());
		perfilAcessoPanel.setOutputMarkupId(true);
		getForm().add(perfilAcessoPanel);
		
		PerfilAcessoEditForm editForm = new PerfilAcessoEditForm((PerfilAcesso)abstractBean, perfilAcessoPanel, getFeedbackPanel(), getAtualizarListarPage(), getModalIncluirEditar());
		editForm.setOutputMarkupId(true);
		perfilAcessoPanel.add(editForm);
		return perfilAcessoPanel;
	}
	
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel((perfilAcessoServico.searchFechId(abstractBean))));
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
	
	@Override
	protected Boolean isVisibleBotaoMaisFiltros() {
		return false;
	}
}
