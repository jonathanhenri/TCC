package com.mycompany.visao.cadastro.evento;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Evento;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class EventoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="eventoServico")
	static IEventoServico eventoServico;
	
	static Evento evento = new Evento();
	
	public EventoListarPage(){
		super(evento);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = eventoServico;
	}
	
	@Override
	protected Boolean addFilters(Search search) {
		search.addFilterNull("agenda");
		return super.addFilters(search);
	}
	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(600);
		modalIncluirEditar.setInitialWidth(900);
		modalIncluirEditar.setCloseButtonCallback(null);
		return modalIncluirEditar;
	}
	
	private void addFiltros(){
		campoDescricao();
	}

	private void campoDescricao(){
		final TextField<String> descricao = new TextField<String>("descricao");
		descricao.setOutputMarkupId(true);
		form.add(descricao);
	}
	

	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new Evento()));
		getModalIncluirEditar().show(target);		
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		EventoPanel editPanel = new EventoPanel(getModalIncluirEditar().getContentId());
		editPanel.setOutputMarkupId(true);
		getForm().add(editPanel);
		
		EventoEditForm cadastroAlunoEditForm = new EventoEditForm((Evento) abstractBean,editPanel,getFeedbackPanel(),getAtualizarListarPage(),getModalIncluirEditar());
		cadastroAlunoEditForm.setOutputMarkupId(true);
		editPanel.add(cadastroAlunoEditForm);
		
		return editPanel;
	}
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel((eventoServico.searchFechId(abstractBean))));
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
