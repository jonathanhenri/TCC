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
	
	private ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(350);
		modalIncluirEditar.setInitialWidth(600);
		
		modalIncluirEditar.setCloseButtonCallback(null);
		
		return modalIncluirEditar;
	}
	
	private WebMarkupContainer criarDivAtualizar(){
		divAtualizar = new WebMarkupContainer("divAtualizar");
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
				Agenda permissao = (Agenda) item.getModelObject();		
				
				item.add(new Label("nome", permissao.getNome()));
			}
		};
		
		return listViewPermissaoAcesso;
	}
	
	
	private AjaxButton criarButtonIncluir(){
		AjaxButton ajaxButton =  new AjaxButton("incluir") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(divAtualizar);
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
