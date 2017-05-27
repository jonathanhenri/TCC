package com.mycompany.visao.comum;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.mycompany.domain.FiltroDinamicoAgrupador;
import com.mycompany.domain.FiltroDinamicoAtributo;

public abstract class ListagemFiltrosDinamicosPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private List<FiltroDinamicoAtributo> listaNomesCamposAbstractBean;
	private List<FiltroDinamicoAtributo> listaAtributos;
	private WebMarkupContainer divListagem;
	private FiltroDinamicoAgrupador filtroDinamicoAgrupador;
	private ModalWindow modalFiltros;
	private ModalWindow modalIncluirFiltros;
	private Form<Object> form;
	
	public ListagemFiltrosDinamicosPanel(String id,ModalWindow modalFiltros,FiltroDinamicoAgrupador filtroDinamicoAgrupado,List<FiltroDinamicoAtributo> listaNomesCamposAbstractBean) {
		super(id);
		this.modalFiltros = modalFiltros;
		setFiltroDinamicoAgrupador(filtroDinamicoAgrupado);
		this.listaAtributos = new ArrayList<FiltroDinamicoAtributo>();
		this.listaNomesCamposAbstractBean = listaNomesCamposAbstractBean;
		add(criarForm());
		
	}
	
	
	protected ModalWindow criarModalIncluiFiltros() {
		modalIncluirFiltros = new ModalWindow("modalIncluirFiltros");
		modalIncluirFiltros.setOutputMarkupId(true);
		modalIncluirFiltros.setInitialHeight(350);
		modalIncluirFiltros.setInitialWidth(600);
		
		modalIncluirFiltros.setCloseButtonCallback(null);
		
		return modalIncluirFiltros;
	}
	
	private AjaxLink<String> criarBotaoIncluirNovoFiltro(){
		 AjaxLink<String> voltar = new  AjaxLink<String>("incluirNovoFiltro"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				IncluirFiltroDinamicoAtributoPanel filtroDinamicoAtributoPanel = new IncluirFiltroDinamicoAtributoPanel(modalIncluirFiltros.getContentId(),modalIncluirFiltros,listaNomesCamposAbstractBean) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void executarAoVoltar(AjaxRequestTarget target) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					protected void executarAoSalvar(AjaxRequestTarget target,FiltroDinamicoAtributo filtroDinamicoAtributo) {
						listaAtributos.add(filtroDinamicoAtributo);
						modalIncluirFiltros.close(target);
						target.add(divListagem);
						
					}
				};
				
				form.add(filtroDinamicoAtributoPanel);
				modalIncluirFiltros.setContent(filtroDinamicoAtributoPanel);
				modalIncluirFiltros.show(target);
			}
			 
		 };
		 return voltar;
	}
	
	
	private WebMarkupContainer criarDivListagem(){
		divListagem = new WebMarkupContainer("divListagem");
		divListagem.setOutputMarkupId(true);
		
		LoadableDetachableModel<List<FiltroDinamicoAtributo>> atributos = new LoadableDetachableModel<List<FiltroDinamicoAtributo>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<FiltroDinamicoAtributo> load() {
				List<FiltroDinamicoAtributo> listaAtributosAux = new ArrayList<FiltroDinamicoAtributo>();
				
				if(listaAtributos!=null && listaAtributos.size() > 0 ){
					listaAtributosAux.addAll(listaAtributos);
				}
				
				return listaAtributosAux;
			}
		};
		
		ListView<FiltroDinamicoAtributo> listaAtributos = new ListView<FiltroDinamicoAtributo>("listaAtributos",atributos) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<FiltroDinamicoAtributo> item) {
				FiltroDinamicoAtributo atributo = item.getModelObject();
				
				Label nomeCampo = null;
				
				if(atributo.getNomeCampoPersonalidado()!=null){
					nomeCampo = new Label("nomeCampo",atributo.getNomeCampoPersonalidado());
				}else if(atributo.getNomeCampo()!=null){
					nomeCampo = new Label("nomeCampo", atributo.getNomeCampo());
				}
				Label valorCampo = new Label("valorCampo", String.valueOf(atributo.getValorCampo()));

				
				nomeCampo.setOutputMarkupId(true);
				valorCampo.setOutputMarkupId(true);
				item.add(nomeCampo);
				item.add(valorCampo);
			}
		};
		
		divListagem.add(listaAtributos);
		return divListagem;
	}
	
	private Form<Object> criarForm(){
		form  = new Form<Object>("formListagemFiltro");
		form.setOutputMarkupId(true);
		form.add(criarButtonPesquisar());
		form.add(criarDivListagem());
		form.add(criarBotaoVoltar());
		form.add(criarModalIncluiFiltros());
		form.add(criarBotaoIncluirNovoFiltro());
		return form;
	}

	private AjaxLink<String> criarBotaoVoltar(){
		 AjaxLink<String> voltar = new  AjaxLink<String>("voltar"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalFiltros.close(target);
			}
			 
		 };
		 return voltar;
	}
	
	

	private AjaxButton criarButtonPesquisar(){
		AjaxButton ajaxButton =  new AjaxButton("pesquisar") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				filtroDinamicoAgrupador.setListaFiltroDinamicoAtributos(listaAtributos);
				executarAoPesquisar(target);
			}
		};
		
		return ajaxButton;
	}
	
	public List<FiltroDinamicoAtributo> getListaAtributos() {
		return listaAtributos;
	}

	public void setListaAtributos(List<FiltroDinamicoAtributo> listaAtributos) {
		this.listaAtributos = listaAtributos;
	}
	
	public FiltroDinamicoAgrupador getFiltroDinamicoAgrupador() {
		return filtroDinamicoAgrupador;
	}

	public void setFiltroDinamicoAgrupador(FiltroDinamicoAgrupador filtroDinamicoHashMap) {
		this.filtroDinamicoAgrupador = filtroDinamicoHashMap;
	}

	protected abstract void executarAoPesquisar(AjaxRequestTarget target);
	
}
