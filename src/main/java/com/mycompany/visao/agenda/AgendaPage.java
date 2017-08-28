package com.mycompany.visao.agenda;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.Menu;

public class AgendaPage extends Menu {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="agendaServico")
	private IAgendaServico agendaServico;
	
	private Agenda agendaPesquisa;
	private WebMarkupContainer divAtualizar;
	private ModalWindow modalIncluirEditar;
	private ModalWindow modalCalendario;
	private ModalWindow modalExcluir;
	
	public AgendaPage() {
		inicializarPagina();
		adicionarCampos();
	}
	
	private void inicializarPagina(){
		agendaPesquisa = new Agenda();
	}
	
	private ModalWindow criarModalExcluir(){
		modalExcluir= new ModalWindow("modalExcluir");
		modalExcluir.setInitialHeight(250);
		modalExcluir.setInitialWidth(600);
		modalExcluir.setOutputMarkupId(true);
		
		return modalExcluir;
	}
	
	private ModalWindow criarModalCalendario() {
		modalCalendario = new ModalWindow("modalCalendario");
		modalCalendario.setOutputMarkupId(true);
		modalCalendario.setInitialHeight(800);
		modalCalendario.setInitialWidth(1500);
		
		modalCalendario.setCloseButtonCallback(null);
		
		return modalCalendario;
	}
	
	
	
	private ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(300);
		modalIncluirEditar.setInitialWidth(600);
		
		modalIncluirEditar.setCloseButtonCallback(null);
		
		return modalIncluirEditar;
	}
	
	private WebMarkupContainer criarDivAtualizar(){
		divAtualizar = new WebMarkupContainer("divAtualizar");
		divAtualizar.setOutputMarkupId(true);
		divAtualizar.add(criarListViewAgendas());
		return divAtualizar;
	}
	
	private void adicionarCampos(){
		Form<Agenda> form = new Form<Agenda>("form", new CompoundPropertyModel<Agenda>(agendaPesquisa	));
		form.setOutputMarkupId(true);
		
		form.add(criarDivAtualizar());
		form.add(criarButtonIncluir());
		form.add(criarModalExcluir());
		form.add(criarModalIncluirEditar());
		form.add(criarModalCalendario());
		add(form);
	}
	
	
	private ListView<Agenda> criarListViewAgendas(){
		LoadableDetachableModel<List<Agenda>> loadAgendas = new LoadableDetachableModel<List<Agenda>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Agenda> load() {
				return agendaServico.search(new Search(Agenda.class));
			}
		};
			
		ListView<Agenda> listViewPermissaoAcesso = new ListView<Agenda>("listAgendas",loadAgendas) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Agenda> item) {
				Agenda agenda = (Agenda) item.getModelObject();		
				
				item.add(new Label("nome", agenda.getNome()));
				item.add(criarButtonVisualizarCalendario(agenda));
			}
		};
		
		return listViewPermissaoAcesso;
	}
	
	private AjaxButton criarButtonVisualizarCalendario(final Agenda agenda){
		AjaxButton ajaxButton =  new AjaxButton("visualizarCalendario") {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				CalendarioPanel calendarioPanel = new CalendarioPanel(modalCalendario.getContentId(),(Agenda) agendaServico.searchFechId(agenda));
				calendarioPanel.setOutputMarkupId(true);
				form.add(calendarioPanel);
				modalCalendario.setContent(calendarioPanel);
				modalCalendario.show(target);
			}
		};
		ajaxButton.setOutputMarkupId(true);
		return ajaxButton;
	}
	
	private AjaxButton criarButtonIncluir(){
		AjaxButton ajaxButton =  new AjaxButton("incluir") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				AgendaPanel agendaPanel = new AgendaPanel(modalIncluirEditar.getContentId());
				agendaPanel.setOutputMarkupId(true);
				
				AgendaEditForm agendaEditForm = new AgendaEditForm(new Agenda(), agendaPanel, null, divAtualizar, modalIncluirEditar);
				agendaEditForm.setOutputMarkupId(true);
				agendaPanel.add(agendaEditForm);
				
				modalIncluirEditar.setContent(agendaPanel);
				modalIncluirEditar.show(target);
				
			}
			
			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(agendaServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_AGENDA_INCLUIR, PermissaoAcesso.OPERACAO_INCLUIR)){
					return false;
				}
				return true;
			}
		};
		
		return ajaxButton;
	}

}
