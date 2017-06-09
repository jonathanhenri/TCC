package com.mycompany.visao.comum;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.util.Util;

public abstract class IncluirFiltroDinamicoAtributoPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private List<FiltroDinamicoAtributo> listaAtributos;
	private FiltroDinamicoAtributo filtroDinamicoAtributo;
	private ModalWindow modalIncluirFiltro;
	private IndicatorDropDownChoice<FiltroDinamicoAtributo> nomeCampoDropDown;
	
	public IncluirFiltroDinamicoAtributoPanel(String id,ModalWindow  modalIncluirFiltro,List<FiltroDinamicoAtributo> listaAtributos) {
		super(id);
		this.listaAtributos = listaAtributos;
		this.modalIncluirFiltro = modalIncluirFiltro;
		filtroDinamicoAtributo = new FiltroDinamicoAtributo();
		add(criarForm());
	}
	
	
	private IndicatorDropDownChoice<FiltroDinamicoAtributo> criarCampoNomeCampo(){
		
		LoadableDetachableModel<List<FiltroDinamicoAtributo>> nomesAtributo = new LoadableDetachableModel<List<FiltroDinamicoAtributo>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<FiltroDinamicoAtributo> load() {
				return listaAtributos;
			}
			
		};
	
		
		ChoiceRenderer<FiltroDinamicoAtributo> renderer = new ChoiceRenderer<FiltroDinamicoAtributo>("nomeCampo","uuid"){
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(FiltroDinamicoAtributo object) {
				if(object.getNomeCampoPersonalidado()!=null && !object.getNomeCampoPersonalidado().isEmpty()){
					return object.getNomeCampoPersonalidado();
				}else{
					return object.getNomeCampo();
				}
			};
		};
		
		nomeCampoDropDown = new IndicatorDropDownChoice<FiltroDinamicoAtributo>("nomeCampo",new Model<FiltroDinamicoAtributo>(), nomesAtributo,renderer);
		
		nomeCampoDropDown.setOutputMarkupId(true);
		
		return nomeCampoDropDown;
		
	}

	
	private Form<FiltroDinamicoAtributo> criarForm(){
		Form<FiltroDinamicoAtributo> form  = new Form<FiltroDinamicoAtributo>("formIncluirFiltro");
		form.setOutputMarkupId(true);
		form.add(criarCampoNomeCampo());
		form.add(criarButtonSalvar(form));
		form.add(criarBotaoVoltar());
		form.addOrReplace(criarCampoValorCampo());
		return form;
	}
	
	private AjaxButton criarButtonSalvar(Form<FiltroDinamicoAtributo> form){
		AjaxButton ajaxButton =  new AjaxButton("salvar",form) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				// ARRUMAR ISSO, PQ O DROPDOWN NÃO CONSEGUE PEGAR O OBJETO?
				if(nomeCampoDropDown.getModelObject()!=null &&
					(nomeCampoDropDown.getModelObject().getNomeCampo()!=null && !nomeCampoDropDown.getModelObject().getNomeCampo().isEmpty() ||
					nomeCampoDropDown.getModelObject().getNomeCampoPersonalidado()!=null && !nomeCampoDropDown.getModelObject().getNomeCampoPersonalidado().isEmpty())
					&& filtroDinamicoAtributo.getValorCampo()!=null){
					
					if(nomeCampoDropDown.getModelObject().getNomeCampo()!=null)
						filtroDinamicoAtributo.setNomeCampo(nomeCampoDropDown.getModelObject().getNomeCampo());
					
					if(nomeCampoDropDown.getModelObject().getNomeCampoPersonalidado()!=null)
						filtroDinamicoAtributo.setNomeCampoPersonalidado(nomeCampoDropDown.getModelObject().getNomeCampoPersonalidado());
					
					executarAoSalvar(target, filtroDinamicoAtributo);
				}else{
					Util.notifyError(target, "Todos os campos são obrigatórios");
				}
			}
		};
		
		return ajaxButton;
	}
	
	private AjaxLink<String> criarBotaoVoltar(){
		 AjaxLink<String> voltar = new  AjaxLink<String>("voltar"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalIncluirFiltro.close(target);
				executarAoVoltar(target);
			}
			 
		 };
		 return voltar;
	}
	
	private TextField<Object> criarCampoValorCampo(){
		TextField<Object> valorCampo = new TextField<Object>("valorCampo", new PropertyModel<Object>(filtroDinamicoAtributo, "valorCampo"));
		valorCampo.setOutputMarkupId(true);
		return valorCampo;
	}
	
	protected abstract void executarAoSalvar(AjaxRequestTarget target,FiltroDinamicoAtributo filtroDinamicoAtributo);
	
	protected abstract void executarAoVoltar(AjaxRequestTarget target);


}