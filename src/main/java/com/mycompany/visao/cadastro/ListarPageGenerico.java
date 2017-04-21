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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IServiceComum;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.MensagemExcluirPanel;
import com.mycompany.visao.comum.Menu;


public abstract class ListarPageGenerico extends Menu {
	private static final long serialVersionUID = 1L;
	IServiceComum serviceComum;
	
	private WebMarkupContainer divListaAtualizar;
	
	protected ModalWindow modalIncluirEditar;	
	protected ModalWindow modalExcluir;
	protected Form<AbstractBean<?>> form;
	private AbstractBeanDataProvider dataDataProvider;
	private AbstractBean<?> abstractBean;
	protected IColumn[] columns;
	protected int quantidadeRegistrosVisiveis = 10;
	protected String nomeTituloListarPage;
	
//	protected ListarPageGenerico(AbstractBean<?> abstractBean,Panel editPanel,int quantidadeRegistrosVisiveis,IServiceComum<?> servicoComum){
//		this.editPanel = editPanel;
//		this.serviceComum = servicoComum;
//		this.quantidadeRegistrosVisiveis = quantidadeRegistrosVisiveis;
//		this.abstractBean = abstractBean;
//		adicionaCampos();
//	}
	
	protected ListarPageGenerico(AbstractBean<?> abstractBean,int quantidadeRegistrosVisiveis,IServiceComum servicoComum){
		this.serviceComum = servicoComum;
		this.quantidadeRegistrosVisiveis = quantidadeRegistrosVisiveis;
		this.abstractBean = abstractBean;
		adicionaCampos();
	}
	
	private void criarModalIncluirEditar(){
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(400);
		modalIncluirEditar.setInitialWidth(600);
		add(modalIncluirEditar);
	
	}
	
	private void criarModalExcluir(){
		modalExcluir= new ModalWindow("modalExcluir");
		modalExcluir.setInitialHeight(350);
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
				target.add(divListaAtualizar);
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
		criarModalIncluirEditar();
		criarModalExcluir();
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
	
	protected abstract void getEditFormIncluir(AjaxRequestTarget target);
	
	protected abstract void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean);
	
	protected String getMensagemExclusao(){
		return "Esta ação não pode ser revertida, deseja excluir "+Util.firstToUpperCase(abstractBean.getClass().getSimpleName())+" realmente?";
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
		return "Listagem de "+Util.firstToUpperCase(abstractBean.getClass().getSimpleName());
	}
	
	protected AjaxLink<AbstractBean<?>> criarLinkExcluir(final AbstractBean<?> abstractBean) {
		AjaxLink<AbstractBean<?>> linkExcluir = new AjaxLink<AbstractBean<?>>("linkExcluir", new Model<AbstractBean<?>>(abstractBean)) {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				MensagemExcluirPanel excluirPanel = new MensagemExcluirPanel(getModalExcluir().getContentId(), getMensagemExclusao()){
					private static final long serialVersionUID = 1L;

					protected void executarAoClicarNao(AjaxRequestTarget target) {
						target.add(getAtualizarListarPage());
						getModalExcluir().close(target);
					};
					
					protected void executarAoClicarSim(AjaxRequestTarget target) {
						serviceComum.remove(abstractBean);
						target.add(getAtualizarListarPage());
						getModalExcluir().close(target);
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
	
}
