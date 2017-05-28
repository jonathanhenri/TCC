package com.mycompany.visao.comum;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mycompany.domain.AbstractBean;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IServiceComum;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.util.Util;
@SuppressWarnings("unchecked")
public abstract class EditForm<T extends AbstractBean<?>> extends Form<T>{
	private static final long serialVersionUID = 1L;
	protected AbstractBean<?> abstractBean;
	protected FeedbackPanel feedbackPanel;
	protected String nomeTitulo;
	private Panel editPanel;
	
	private WebMarkupContainer divAtualizar;
	private ModalWindow modalIncluirEditar;
	
	private ModalWindow modalExcluir;
	
	protected IServiceComum serviceComum;
	
	
	public EditForm(String id, AbstractBean<?> abstractBean,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar, ModalWindow modalIncluirEditar) {
		super(id, new CompoundPropertyModel<T>((IModel<T>) new Model<AbstractBean<?>>(abstractBean)));
		this.abstractBean = abstractBean;
		this.editPanel = editPanel;
		setFeedbackPanel(feedbackPanel);
		setDivAtualizar(divAtualizar);
		setModalIncluirEditar(modalIncluirEditar);
		setServicoComum();
		adicionarCampos();
		adicionarCamposGerais();
	}
	
	
	// caso queira desabilitar o form inteiro
	@Override
	public boolean isEnabled() {
		return isEnabledEditForm();
	}
	
	protected Boolean isEnabledEditForm(){
		return true;
	}
	protected abstract void setServicoComum();

	private AjaxLink<String> criarBotaoVoltar(){
		 AjaxLink<String> voltar = new  AjaxLink<String>("voltar"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				executarAoVoltar(target);
			}
			 
		 };
		 return voltar;
	}
	
	
	private ModalWindow criarModalExcluir(){
		modalExcluir= new ModalWindow("modalExcluir");
		modalExcluir.setInitialHeight(300);
		modalExcluir.setInitialWidth(600);
		modalExcluir.setOutputMarkupId(true);
		return modalExcluir;
	}
	
	private AjaxSubmitLink criarBotaoExcluir(){
		AjaxSubmitLink excluir = new AjaxSubmitLink("excluir", this){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> formAux) {
				if(validarRegrasAntesExcluir(target)){
					
					MensagemExcluirPanel excluirPanel = new MensagemExcluirPanel(modalExcluir.getContentId(), Util.getMensagemExclusao(abstractBean)){
						private static final long serialVersionUID = 1L;

						protected void executarAoClicarNao(AjaxRequestTarget target) {
							modalExcluir.close(target);
						};
						
						protected void executarAoClicarSim(AjaxRequestTarget target) {
							Retorno retorno = null;
							try{
								retorno = serviceComum.remove(abstractBean);
								
								 for(Mensagem mensagem:retorno.getListaMensagem()){
									 Util.notify(target, mensagem.toString(), mensagem.getTipo());
								 }
								 
								modalExcluir.close(target);
								executarAoExcluir(target);
							}catch(Exception e){
								System.err.println(retorno);
								e.printStackTrace();
							}
						};
					};
					
					getForm().add(excluirPanel);
					modalExcluir.setContent(excluirPanel);
					modalExcluir.show(target);
				}
			}
			
			@Override
			public boolean isVisible() {
				if(getAbstractBean()!=null && getAbstractBean().getId()!=null){
					return true;
				}
				return false;
			}
		};
		excluir.setOutputMarkupId(true);
		return excluir;
	}
	
	
	private AjaxSubmitLink criarBotaoSalvar(){
		AjaxSubmitLink salvar = new AjaxSubmitLink("salvar",this){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> formAux) {
				Retorno retorno = Reflexao.validarTodosCamposObrigatorios(getAbstractBean());
				if(retorno.getSucesso()){
					if(validarRegrasAntesSalvarEditar((target))){
						if(getAbstractBean().getId()==null){
							persistAbstract(abstractBean,target);
							executarAoSalvar(target);
						}else{
							saveAbstract(abstractBean,target);
							executarAoEditar(target);
						}
						
						executarAoSalvarEditar(target);
					}
				}else{
					 for(Mensagem mensagem:retorno.getListaMensagem()){
						 Util.notify(target, mensagem.toString(), mensagem.getTipo());
			        }
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
//				target.appendJavaScript("$.jGrowl('teste');");
				
				target.add(feedbackPanel);
			}
		};
		
		salvar.setOutputMarkupId(true);
		return salvar;
	}
	
	protected void saveAbstract(AbstractBean<?> abstractBean,AjaxRequestTarget target) {
		try{
			Retorno retorno = serviceComum.save(getAbstractBean());
			 for(Mensagem mensagem:retorno.getListaMensagem()){
				 Util.notify(target, mensagem.toString(), mensagem.getTipo());
			 }
		}catch(Exception e){
			Util.notifyError(target, "Erro ao tentar realizar a ação");
			e.printStackTrace();
		}
	}
	protected void persistAbstract(AbstractBean<?> abstractBean,AjaxRequestTarget target) {
		try{
			Retorno retorno = serviceComum.persist(getAbstractBean());
			 for(Mensagem mensagem:retorno.getListaMensagem()){
				 Util.notify(target, mensagem.toString(), mensagem.getTipo());
	        }
		}catch(Exception e){
			Util.notifyError(target, "Erro ao tentar realizar a ação");
			e.printStackTrace();
		}
	}
	
	protected Boolean validarRegrasAntesSalvarEditar(AjaxRequestTarget target){
		return true;
	}
	
	protected Boolean validarRegrasAntesExcluir(AjaxRequestTarget target){
		return true;
	}
	
	protected void executarAoVoltar(AjaxRequestTarget target){
		getModalIncluirEditar().close(target);
	}
	
	
	protected void executarAoSalvarEditar(AjaxRequestTarget target){
	}
	
	protected void executarAoEditar(AjaxRequestTarget target){
		target.add(getDivAtualizar());
		getModalIncluirEditar().close(target);
	}
	
	protected void executarAoSalvar(AjaxRequestTarget target){
		target.add(getDivAtualizar());
		getModalIncluirEditar().close(target);
	}
	
	protected void executarAoExcluir(AjaxRequestTarget target){
		
	}
	protected void iniciarPagina(){
		
	}
	
	protected Label criarCampoTituloPage(){
		Label titulo = new Label("nomeTitulo",getNomeTituloListarPage());
		titulo.setOutputMarkupId(true);
		return titulo;
	}
	
	protected String getNomeTituloListarPage(){
		String titulo = "";
		if(abstractBean.getId()!=null){
			titulo = "Editando "+ Util.firstToUpperCase(Util.separarToUpperCase(abstractBean.getClass().getSimpleName()));
		}else{
			titulo = "Incluindo " +Util.firstToUpperCase(Util.separarToUpperCase(abstractBean.getClass().getSimpleName()));
		}
		return titulo;
	}


	public AbstractBean<?> getAbstractBean() {
		return abstractBean;
	}
	
//	protected abstract void inicializarEditForm(String id,AbstractBean<?> abstractBean,Panel editPanel);
			
	private void adicionarCamposGerais(){
		add(criarBotaoExcluir());
		add(criarBotaoSalvar());
		add(criarBotaoVoltar());
		add(criarModalExcluir());
		add(criarCampoTituloPage());
	}
	protected void adicionarCampos(){
	}

	public void setAbstractBean(AbstractBean<?> abstractBean) {
		this.abstractBean = abstractBean;
	}


	public FeedbackPanel getFeedbackPanel() {
		return feedbackPanel;
	}


	public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
		this.feedbackPanel = feedbackPanel;
	}

	public IServiceComum getServiceComum() {
		return serviceComum;
	}

	public void setServiceComum(IServiceComum serviceComum) {
		this.serviceComum = serviceComum;
	}


	public WebMarkupContainer getDivAtualizar() {
		return divAtualizar;
	}


	public void setDivAtualizar(WebMarkupContainer divAtualizar) {
		this.divAtualizar = divAtualizar;
	}


	public ModalWindow getModalIncluirEditar() {
		return modalIncluirEditar;
	}


	public void setModalIncluirEditar(ModalWindow modalIncluirEditar) {
		this.modalIncluirEditar = modalIncluirEditar;
	}
	
	
}
