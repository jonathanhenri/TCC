package com.mycompany.visao.cadastro.tipoEvento;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class TipoEventoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="tipoEventoServico")
	static ITipoEventoServico tipoEventoServico;
	
	static TipoEvento tipoEvento = new TipoEvento();
	
	public TipoEventoListarPage(){
		super(tipoEvento);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = tipoEventoServico;
	}
	
	@Override
	protected String getNomeTituloListarPage() {
		return "Listagem de Tipo de evento";
	}
	
	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(350);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
	}
	
	private void addFiltros(){
		form.add(criarCampoNome());
	}
	
	private TextField<String> criarCampoNome(){
		final TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		return nome;
	}

	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new TipoEvento()));
		getModalIncluirEditar().show(target);
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		TipoEventoPanel materiaPanel = new TipoEventoPanel(getModalIncluirEditar().getContentId());
		materiaPanel.setOutputMarkupId(true);
		getForm().add(materiaPanel);
		
		TipoEventoEditForm editForm = new TipoEventoEditForm((TipoEvento)abstractBean, materiaPanel, getFeedbackPanel(), getAtualizarListarPage(), getModalIncluirEditar());
		editForm.setOutputMarkupId(true);
		materiaPanel.add(editForm);
		return materiaPanel;
	}
	
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel(abstractBean));
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
