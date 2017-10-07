package com.mycompany.visao.cadastro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.domain.PermissaoAcesso;
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
	private Search searchFiltroDinamico;
	
	
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
	
	protected Boolean isVisibleBotaoMaisFiltros(){
		return true;
	}
	
	protected Boolean isVisibleBotaoIncluir(){
		return true;
	}
	
	protected Boolean isVisibleBotaoEditar(){
		return true;
	}
	
	protected Boolean isVisibleBotaoExcluir(){
		return true;
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
				List<FiltroDinamicoAtributo> listaAtributos = Reflexao.nomesAtributosFiltros(abstractBean);
				if(listaAtributos!=null && listaAtributos.size() > 0){
					 ListagemFiltrosDinamicosPanel dinamicosPage = new ListagemFiltrosDinamicosPanel(modalFiltros.getContentId(),modalFiltros,abstractBean) {
						private static final long serialVersionUID = 1L;
						@Override
						public void getListaFiltrosDinamicosSelecionados(AjaxRequestTarget target,List<FiltroDinamicoAtributo> listaFiltroDinamico) {
							searchFiltroDinamico = Util.montarSearchFiltroDinamico(listaFiltroDinamico);
							
							getModalFiltros().close(target);
							target.add(getAtualizarListarPage());
							
							if(Util.getAlunoLogado().getListaMensagensSistema()!=null && Util.getAlunoLogado().getListaMensagensSistema().size()>0){
								for(Mensagem mensagem:Util.getAlunoLogado().getListaMensagensSistema()){
									Util.notify(target, mensagem.toString(), mensagem.getTipo());
								}
								Util.getAlunoLogado().setListaMensagensSistema(new ArrayList<Mensagem>());
							}
						}
					};
					getForm().add(dinamicosPage);
					
					dinamicosPage.setOutputMarkupId(true);
					getModalFiltros().setContent(dinamicosPage);
					getModalFiltros().show(target);
				}else{
					Util.notifyInfo(target, "Não existe filtros adicionais.");
				}
			}
			@Override
			public boolean isVisible() {
				return isVisibleBotaoMaisFiltros();
			}
		};
		
		return ajaxButton;
	}
	
	private AjaxButton criarButtonIncluir(){
		AjaxButton ajaxButton =  new AjaxButton("incluir") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(!getModalIncluirEditar().isShown()){
					getEditFormIncluir(target);
					target.add(divListaAtualizar);
				}
			}
			
			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(serviceComum.searchFetchAlunoLogado(Util.getAlunoLogado()),abstractBean, PermissaoAcesso.OPERACAO_INCLUIR)){
					return false;
				}
				return isVisibleBotaoIncluir();
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
		
		divListaAtualizar.addOrReplace(new AjaxPagingNavigator("navegacao", dataTable){

			private static final long serialVersionUID = 1L;

			@Override
			protected void onAjaxEvent(AjaxRequestTarget target) {
				super.onAjaxEvent(target);
				target.add(divListaAtualizar);
			}
			@Override
			public boolean isVisible() {
				return dataDataProvider.size() > getQuantidadeRegistrosVisiveis();
			}
		});
	}
	
	private void criarDataProvider(){
		 dataDataProvider = new AbstractBeanDataProvider(serviceComum,new String[6]){
			private static final long serialVersionUID = 1L;
			@Override
			public void addFilters() {
				setColPropertyExpression(new String[6]);
				this.pesquisaPadrao = ListarPageGenerico.this.addFilters(search);
				
				 if(searchFiltroDinamico!=null && searchFiltroDinamico.getFilters().size()>0){
                    for(String fetch:searchFiltroDinamico.getFetches()){
                        search.addFetch(fetch);
                    }
                    for(Filter filter:searchFiltroDinamico.getFilters()){
                        search.addFilter(filter);
                    }
                    searchFiltroDinamico = null;
                  }
				 
				 
				super.addFilters();
			}
		};
		dataDataProvider.setFilterState(abstractBean);
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
				final String campoEntiy = hashMapColunas.get(nomeColunaPage);
				columns[i] = new PropertyColumn<AbstractBean<?>,String>(new Model<String>(nomeColunaPage), campoEntiy, campoEntiy){
					private static final long serialVersionUID = 1L;

					@Override
					public void populateItem(Item<ICellPopulator<AbstractBean<?>>> item,String componentId, IModel<AbstractBean<?>> rowModel) {
						super.populateItem(item, componentId, rowModel);
					}
				};
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
			
			@Override
			public boolean isVisible() {
				return isVisibleBotaoEditar();
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
		return "Listagem de "+Util.firstToUpperCase(Util.separarToUpperCase(abstractBean.getNomeClass()));
	}
	
	protected AjaxLink<AbstractBean<?>> criarLinkExcluir(final AbstractBean<?> abstractBean) {
		AjaxLink<AbstractBean<?>> linkExcluir = new AjaxLink<AbstractBean<?>>("linkExcluir", new Model<AbstractBean<?>>(abstractBean)) {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				if(!getModalExcluir().isShown()){
					MensagemExcluirPanel excluirPanel = new MensagemExcluirPanel(getModalExcluir().getContentId(), Util.getMensagemExclusao(abstractBean)){
						private static final long serialVersionUID = 1L;
	
						protected void executarAoClicarNao(AjaxRequestTarget target) {
							target.add(getAtualizarListarPage());
							getModalExcluir().close(target);
						};
						
						protected void executarAoClicarSim(AjaxRequestTarget target) {
							Retorno retorno = new Retorno();
							try{
								retorno = serviceComum.remove(serviceComum.searchFechId(abstractBean));	
							}catch(Exception e){
								retorno.setSucesso(false);
								
								if(e instanceof ConstraintViolationException || e instanceof DataIntegrityViolationException || (e.getCause()!=null && e.getCause() instanceof ConstraintViolationException)){
									retorno.addMensagem(new Mensagem(abstractBean.getNomeClass(), Mensagem.MOTIVO_UTILIZADO, Mensagem.ERRO));
								}else{
									retorno.addMensagem(new Mensagem("Erro ao tentar realizar a ação",Mensagem.ERRO));
								}
								
								e.printStackTrace();
							}
							
							if(retorno.getSucesso()){
								target.add(getAtualizarListarPage());
								getModalExcluir().close(target);
							}
							
							for(Mensagem mensagem:retorno.getListaMensagem()){
								 Util.notify(target, mensagem.toString(), mensagem.getTipo());
							 }
							
						};
					};
					
					getForm().add(excluirPanel);
					getModalExcluir().setContent(excluirPanel);
					getModalExcluir().show(target);
				}
				
			}
			
			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(serviceComum.searchFetchAlunoLogado(Util.getAlunoLogado()),abstractBean, PermissaoAcesso.OPERACAO_EXCLUIR)){
					return false;
				}
				
				return isVisibleBotaoExcluir();
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
		modalFiltros.setInitialHeight(400);
		modalFiltros.setInitialWidth(700);
		return modalFiltros;
	}


	public void setModalFiltros(ModalWindow modalFiltros) {
		this.modalFiltros = modalFiltros;
	}
	
}
