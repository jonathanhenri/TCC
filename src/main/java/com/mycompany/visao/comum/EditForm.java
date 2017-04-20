package com.mycompany.visao.comum;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mycompany.domain.AbstractBean;
import com.mycompany.services.interfaces.IServiceComum;
@SuppressWarnings("unchecked")
public abstract class EditForm<T extends AbstractBean<?>> extends Form<T>{
	private static final long serialVersionUID = 1L;
	protected AbstractBean<?> abstractBean;
	protected FeedbackPanel feedbackPanel;
	
	private IServiceComum serviceComum;
	
	public EditForm(String id, AbstractBean<?> abstractBean,IServiceComum serviceComum) {
		super(id, new CompoundPropertyModel<T>((IModel<T>) new Model<AbstractBean<?>>(abstractBean)));
		this.abstractBean = abstractBean;
		this.serviceComum = serviceComum;
		adicionarCampos();
		adicionarCamposGerais();
	}
	

	private AjaxLink<String> criarBotaoVoltar(){
		 AjaxLink<String> voltar = new  AjaxLink<String>("voltar"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
			}
			 
		 };
		 return voltar;
	}
	
	
	private AjaxSubmitLink criarBotaoExcluir(){
		AjaxSubmitLink excluir = new AjaxSubmitLink("excluir", this){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> formAux) {
				if(validarRegrasAntesExcluir(target)){
					serviceComum.remove(getAbstractBean());
				}
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
				if(validarRegrasAntesSalvarEditar((target))){
					if(getAbstractBean().getId()==null){
						serviceComum.persist(getAbstractBean());
					}else{
						serviceComum.save(getAbstractBean());
					}
						
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		
		salvar.setOutputMarkupId(true);
		return salvar;
	}
	
	protected Boolean validarRegrasAntesSalvarEditar(AjaxRequestTarget target){
		return true;
	}
	
	protected Boolean validarRegrasAntesExcluir(AjaxRequestTarget target){
		return true;
	}
	
	
	protected void executarAoEditar(AjaxRequestTarget target){
		
	}
	protected void executarAoSalvar(AjaxRequestTarget target){
		
	}
	protected void executarAoExcluir(AjaxRequestTarget target){
		
	}
	protected void iniciarPagina(){
		
	}


	public AbstractBean<?> getAbstractBean() {
		return abstractBean;
	}

	private void adicionarCamposGerais(){
		add(criarBotaoExcluir());
		add(criarBotaoSalvar());
		add(criarBotaoVoltar());
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
	
	
}
