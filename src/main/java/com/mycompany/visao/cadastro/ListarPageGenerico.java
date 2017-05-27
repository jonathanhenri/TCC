package com.mycompany.visao.cadastro;

import java.util.Arrays;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.FiltroDinamicoAgrupador;
import com.mycompany.domain.FiltroDinamicoAgrupador;
import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IServiceComum;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.ListagemFiltrosDinamicosPanel;
import com.mycompany.visao.comum.MensagemExcluirPanel;
import com.mycompany.visao.comum.Menu;


public abstract class ListarPageGenerico extends Menu {
	private static final long serialVersionUID = 1L;
	protected IServiceComum serviceComum;
	
	private WebMarkupContainer divListaAtualizar;
	
	protected ModalWindow modalIncluirEditar;	
	protected ModalWindow modalExcluir;
	protected ModalWindow modalFiltros;
	protected Form<AbstractBean<?>> form;
	private AbstractBeanDataProvider dataDataProvider;
	private AbstractBean<?> abstractBean;
	protected IColumn[] columns;
	protected int quantidadeRegistrosVisiveis = 10;
	protected String nomeTituloListarPage;
	
	private FiltroDinamicoAgrupador filtroDinamicoAgrupador;
	
	protected ListarPageGenerico(AbstractBean<?> abstractBean){
		this.abstractBean = abstractBean;
		setServicoComum();
		adicionaCampos();
	}
	
	
	protected ListarPageGenerico(AbstractBean<?> abstractBean,int quantidadeRegistrosVisiveis){
		this.quantidadeRegistrosVisiveis = quantidadeRegistrosVisiveis;
		this.abstractBean = abstractBean;
		setServicoComum();
		adicionaCampos();
	}
	
	protected abstract void setServicoComum();
	
	protected abstract void getEditFormIncluir(AjaxRequestTarget target);
	
	protected abstract void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean);
	
	protected void inicializarFiltrosDinamicos() {
	}
	
	protected abstract ModalWindow criarModalIncluirEditar();
	
	protected abstract ModalWindow criarModalFiltros();
	
	private void criarModalExcluir(){
		modalExcluir= new ModalWindow("modalExcluir");
		modalExcluir.setInitialHeight(250);
		modalExcluir.setInitialWidth(600);
		modalExcluir.setOutputMarkupId(true);
		add(modalExcluir);
	}
	
	
	private AjaxButton criarButtonPesquisar(){
		AjaxButton ajaxButton =  new AjaxButton("pesquisar") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				
//				FiltroDinamicoAtributo filtroDinamicoAtributo = new FiltroDinamicoAtributo(Reflexao.nomesAtributosFiltros(abstractBean));
				
				target.add(divListaAtualizar);
			}
		};
		
		return ajaxButton;
	}
	
	private AjaxButton criarButtonListagemFiltrosDinamicos(){
		AjaxButton ajaxButton =  new AjaxButton("buttonListagemFiltrosDinamicos") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				ListagemFiltrosDinamicosPanel filtrosDinamicosPanel = new ListagemFiltrosDinamicosPanel(getModalFiltros().getContentId(),getModalFiltros(),getFiltroDinamicoAgrupador(),Reflexao.nomesAtributosFiltros(abstractBean)) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void executarAoPesquisar(AjaxRequestTarget target) {
						getModalFiltros().close(target);
						target.add(getAtualizarListarPage());
						
					}
				};
				getForm().add(filtrosDinamicosPanel);
				
				filtrosDinamicosPanel.setOutputMarkupId(true);
				getModalFiltros().setContent(filtrosDinamicosPanel);
				getModalFiltros().show(target);
			}
		};
		
		return ajaxButton;
	}
	
	private AjaxButton criarButtonIncluir(){
		AjaxButton ajaxButton =  new AjaxButton("incluir") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				getEditFormIncluir(target);
				target.add(divListaAtualizar);
			}
		};
		
		return ajaxButton;
	}
	
	public void adicionaCampos(){
		add(criarModalIncluirEditar());
		add(criarModalFiltros());
		criarModalExcluir();
		inicializarFiltrosDinamicos();
		criarForm();
		createColumns();
		criarDataProvider();
		criarDataTable();
		add(criarCampoTituloPage());
	}
	
	protected Form<?> getForm(){
		return form;
	}
	private void criarForm(){
		form = new Form<AbstractBean<?>>("formListagem",new CompoundPropertyModel<AbstractBean<?>>(abstractBean));
		form.add(criarButtonPesquisar());
		form.add(criarButtonIncluir());
		form.add(criarButtonListagemFiltrosDinamicos());
		add(form);
	}
	
	private void criarDataTable(){
		divListaAtualizar = new WebMarkupContainer("theContainer");
		divListaAtualizar.setOutputMarkupId(true);
		add(divListaAtualizar);
		
		
		DefaultDataTable dataTable = new DefaultDataTable("tabela", Arrays.asList(columns), dataDataProvider, getQuantidadeRegistrosVisiveis());
		dataTable.setOutputMarkupId(true);
		divListaAtualizar.add(dataTable);
		divListaAtualizar.setVersioned(false);
		divListaAtualizar.add(new AjaxPagingNavigator("navegacao", dataTable).add(new AttributeModifier("class", "pagination")));
	}
	
	private void criarDataProvider(){
		 dataDataProvider = new AbstractBeanDataProvider(serviceComum,new String[6]){
			private static final long serialVersionUID = 1L;
			@Override
			public void addFilters() {
				setColPropertyExpression(new String[6]);
				this.pesquisaPadrao = ListarPageGenerico.this.addFilters(search);
				atualizarFiltrosGenericos(search);
				super.addFilters();
			}
		};
		dataDataProvider.setFilterState(abstractBean);
	}
	
	private void atualizarFiltrosGenericos(Search search){
		if(filtroDinamicoAgrupador!=null && filtroDinamicoAgrupador.getListaFiltroDinamicoAtributos()!=null && filtroDinamicoAgrupador.getListaFiltroDinamicoAtributos().size() >0){
			for(FiltroDinamicoAtributo pesquisa:filtroDinamicoAgrupador.getListaFiltroDinamicoAtributos()){
				search.addFilterEqual(pesquisa.getNomeCampo(), pesquisa.getValorCampo());
			}
		}
		
		filtroDinamicoAgrupador = new FiltroDinamicoAgrupador();
	}
	
	protected Boolean addFilters(Search search) {
		return true;
	}
	
	
	protected WebMarkupContainer getAtualizarListarPage(){
		return divListaAtualizar;
	}
	
	
	
	protected void createColumns(){
		Map<String, String> hashMapColunas = Reflexao.colunaListarPage(abstractBean);
		
		if(hashMapColunas.size()> 0){
			columns = new IColumn[hashMapColunas.size() + 1];
			
			int i = 0;
			for(String nomeColunaPage:hashMapColunas.keySet()){
				String campoEntiy = hashMapColunas.get(nomeColunaPage);
				columns[i] = new PropertyColumn<AbstractBean<?>,String>(new Model<String>(nomeColunaPage), campoEntiy, campoEntiy);
				i++;
			}
			
			columns[hashMapColunas.size()] = criarColunaAcao();
		}
	}
	
	protected AbstractColumn<AbstractBean<?>, String> criarColunaAcao() {
		return new AbstractColumn<AbstractBean<?>, String>(new ResourceModel("acao")) {
			private static final long serialVersionUID = 1L;
			public void populateItem(Item<ICellPopulator<AbstractBean<?>>> cellItem, final String componentId, final IModel<AbstractBean<?>> model) {
				cellItem.add(new PainelAcao(componentId,criarLinkEditar(model.getObject()),criarLinkExcluir(model.getObject())));
			}
			@Override
			public IModel<String> getDisplayModel() {
				return new ResourceModel("acao");
			}
		};
	}
	
	protected AjaxLink<AbstractBean<?>> criarLinkEditar(final AbstractBean<?> abstractBean) {
		AjaxLink<AbstractBean<?>> linkEditar = new AjaxLink<AbstractBean<?>>("linkAlterar", new Model<AbstractBean<?>>(abstractBean)) {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				getEditFormEditar(target,abstractBean);
			}
		};
		linkEditar.setOutputMarkupId(true);
		return linkEditar;
	}
	
	protected Label criarCampoTituloPage(){
		Label titulo = new Label("nomeTitulo",getNomeTituloListarPage());
		titulo.setOutputMarkupId(true);
		return titulo;
	}
	
	protected String getNomeTituloListarPage(){
		return "Listagem de "+Util.firstToUpperCase(Util.separarToUpperCase(abstractBean.getClass().getSimpleName()));
	}
	
	protected AjaxLink<AbstractBean<?>> criarLinkExcluir(final AbstractBean<?> abstractBean) {
		AjaxLink<AbstractBean<?>> linkExcluir = new AjaxLink<AbstractBean<?>>("linkExcluir", new Model<AbstractBean<?>>(abstractBean)) {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				MensagemExcluirPanel excluirPanel = new MensagemExcluirPanel(getModalExcluir().getContentId(), Util.getMensagemExclusao(abstractBean)){
					private static final long serialVersionUID = 1L;

					protected void executarAoClicarNao(AjaxRequestTarget target) {
						target.add(getAtualizarListarPage());
						getModalExcluir().close(target);
					};
					
					protected void executarAoClicarSim(AjaxRequestTarget target) {
						Retorno retorno = null;
						try{
							retorno = serviceComum.remove(abstractBean);
							
							 for(Mensagem mensagem:retorno.getListaMensagem()){
								 Util.notify(target, mensagem.toString(), mensagem.getTipo());
							 }
							target.add(getAtualizarListarPage());
							getModalExcluir().close(target);
						}catch(Exception e){
							Util.notifyError(target, "Erro ao tentar realizar a ação");
							e.printStackTrace();
						}
					};
				};
				
				getForm().add(excluirPanel);
				getModalExcluir().setContent(excluirPanel);
				getModalExcluir().show(target);
				
			}
		};
		linkExcluir.setOutputMarkupId(true);
		return linkExcluir;
	}
	
	public void setAbstractBean(AbstractBean<?> abstractBean) {
		this.abstractBean = abstractBean;
	}
	
	public void setQuantidadeRegistrosVisiveis(int quantidadeRegistrosVisiveis) {
		this.quantidadeRegistrosVisiveis = quantidadeRegistrosVisiveis;
	}
	
	public int getQuantidadeRegistrosVisiveis() {
		return quantidadeRegistrosVisiveis;
	}
	
	public void setModalExcluir(ModalWindow modalExcluir) {
		this.modalExcluir = modalExcluir;
	}
	
	public ModalWindow getModalExcluir() {
		return modalExcluir;
	}
	
	public void setModalIncluirEditar(ModalWindow modalIncluirEditar) {
		this.modalIncluirEditar = modalIncluirEditar;
	}
	
	public ModalWindow getModalIncluirEditar() {
		return modalIncluirEditar;
	}


	public ModalWindow getModalFiltros() {
		return modalFiltros;
	}


	public void setModalFiltros(ModalWindow modalFiltros) {
		this.modalFiltros = modalFiltros;
	}


	public FiltroDinamicoAgrupador getFiltroDinamicoAgrupador() {
		if(filtroDinamicoAgrupador == null){
			filtroDinamicoAgrupador = new FiltroDinamicoAgrupador();
		}
		return filtroDinamicoAgrupador;
	}


	public void setFiltroDinamicoAgrupador(FiltroDinamicoAgrupador filtroDinamicoAgrupador) {
		this.filtroDinamicoAgrupador = filtroDinamicoAgrupador;
	}
	
	
}
