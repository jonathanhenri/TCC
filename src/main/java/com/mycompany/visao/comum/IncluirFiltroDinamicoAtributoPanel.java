package com.mycompany.visao.comum;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.input.events.EventType;
import wicket.contrib.input.events.InputBehavior;
import wicket.contrib.input.events.key.KeyType;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.util.Util;
import com.mycompany.util.WicketUtil;

public abstract class IncluirFiltroDinamicoAtributoPanel extends Panel{
	private static final long serialVersionUID = 1L;
	
	private ModalWindow modalEdit;
	private AbstractBean<?> abstractBean;
	private List<FiltroDinamicoAtributo> listaFiltrosDisponiveisEntity;
	private DropDownChoice<FiltroDinamicoAtributo> dropDownFiltroDinamicoAtributoEstrangeiroNivel2;
	private DropDownChoice<FiltroDinamicoAtributo> dropDownFiltroDinamicoAtributoEstrangeiroNivel3;
	private WebMarkupContainer divCamposValorCampo;
	private WebMarkupContainer divAtributoEstrangeiro;
	private WebMarkupContainer divOperadores;
	private FiltroDinamicoAtributo filtroDinamicoSelecionado;
	private Object valorCampo;
	private Integer operador;
	
	@SuppressWarnings("all")
	private boolean focusGained;
	
	//Procurar jeito melhor de fazer as verificações de campos
	public IncluirFiltroDinamicoAtributoPanel(String id, ModalWindow modalEdit,AbstractBean<?> abstractBean,List<FiltroDinamicoAtributo> listaFiltrosDisponiveisEntity,FiltroDinamicoAtributo filtroDinamicoAtributo) {
		super(id);
		this.modalEdit = modalEdit;
		this.abstractBean = abstractBean;
		this.listaFiltrosDisponiveisEntity = listaFiltrosDisponiveisEntity;
		this.filtroDinamicoSelecionado = filtroDinamicoAtributo;
		focusGained = true;
		inicializarFiltroDinamico();
		adicionarCampos();
	}
	
	void inicializarFiltroDinamico(){
		if(filtroDinamicoSelecionado.getOperador()!=null){
			operador = filtroDinamicoSelecionado.getOperador();
		}
		
		if(filtroDinamicoSelecionado.getValorCampo()!=null){
			valorCampo = filtroDinamicoSelecionado.getValorCampo();
		}
	}
	
	void adicionarCampos(){
		add(criarNewHeader());
		
		Form<FiltroDinamicoAtributo> form = new Form<FiltroDinamicoAtributo>("formIncluirFiltroDinamico");
		form.setOutputMarkupId(true);
		form.add(criarBotaoSalvar(form));
		form.add(criarBotaoVoltar());
		form.add(criarDivCamposValorCampo());
		form.add(criarDivAtributoEstrangeiro());
		form.add(criarCampoFiltroDinamicoAtributo());
		form.add(criarDivOperadores());
		add(form);
	}
	
	private WebMarkupContainer criarDivOperadores(){
		divOperadores = new WebMarkupContainer("divOperadores");
		divOperadores.setOutputMarkupId(true);
		divOperadores.add(criarCampoOperadores());
		
		return divOperadores;
	}
	
	
	private WebMarkupContainer criarDivAtributoEstrangeiro(){
		divAtributoEstrangeiro = new WebMarkupContainer("divAtributoEstrangeiro");
		divAtributoEstrangeiro.setOutputMarkupId(true);
		divAtributoEstrangeiro.add(criarCampoFiltroDinamicoAtributoEstrangeiroNivel2());
		divAtributoEstrangeiro.add(criarCampoFiltroDinamicoAtributoEstrangeiroNivel3());
		return divAtributoEstrangeiro;
	}
	
	private WebMarkupContainer criarNewHeader(){
		WebMarkupContainer newHeader = new WebMarkupContainer("newHeader") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onBeforeRender() {
				super.onBeforeRender();
			}
		};
		newHeader.setOutputMarkupId(true);
		return newHeader;
	}
	
	private WebMarkupContainer criarDivCamposValorCampo(){
		divCamposValorCampo = new WebMarkupContainer("divCamposValorCampo");
		divCamposValorCampo.setOutputMarkupId(true);
		
		divCamposValorCampo.add(criarCampoTextFieldString());
		divCamposValorCampo.add(criarCampoNumberTextField());
		divCamposValorCampo.add(criarDataTextField());
		divCamposValorCampo.add(criarCampoBoolean());
		return divCamposValorCampo;
	}

	private Boolean verificarCampoData(){
		if(filtroDinamicoSelecionado!=null){
			if(filtroDinamicoSelecionado.getTypeCampo()!=null && filtroDinamicoSelecionado.getTypeCampo().isInstance(new Date()) || dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo().isInstance(new Date()))
					|| dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo().isInstance(new Date()))){
				return true;
			}
		}
		return false;
	}
	private DateTextField criarDataTextField(){
		
		DatePicker datePicker = new DatePicker(){
			private static final long serialVersionUID = 1L;
			
			
			@Override
			protected boolean alignWithIcon() {
				return true;
			}
			@Override
			protected boolean enableMonthYearSelection() {
				return false;
			}			
		};
		DateTextField datasaida = new DateTextField("valorCampoData",new PropertyModel(this, "valorCampo")){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return verificarCampoData();
			}
		};
		
		datePicker.setAutoHide(true);		
		datasaida.add(datePicker);
		datasaida.add(new AttributeModifier("onfocus", "$(this).setMask('99/99/9999');"));
		datasaida.setOutputMarkupId(true);
		return datasaida;
	}

	private Boolean verificarCampoNumber(){
		if(filtroDinamicoSelecionado!=null){
			if(   filtroDinamicoSelecionado.getTypeCampo()!=null && (filtroDinamicoSelecionado.getTypeCampo().isInstance(new Long(1)) || filtroDinamicoSelecionado.getTypeCampo().isInstance(new Integer(1)) || filtroDinamicoSelecionado.getTypeCampo().isInstance(new Double(1))) || 
				  dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo()!=null && ((dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo().isInstance(new Long(1)) || dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo().isInstance(new Integer(1)) || dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo().isInstance(new Double(1)))))
				  || dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo()!=null && ((dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo().isInstance(new Long(1)) || dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo().isInstance(new Integer(1)) || dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo().isInstance(new Double(1)))))){
				return true;
			}
		}
		return false;
	}
	
	private NumberTextField<Double> criarCampoNumberTextField(){
		NumberTextField<Double>  numberTextField = new NumberTextField<Double>("valorCampoNumber",new PropertyModel<Double>(this, "valorCampo")){
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				return verificarCampoNumber();
			};
		};
		numberTextField.setOutputMarkupId(true);
		return numberTextField;
	}
	
	private Boolean verificarCampoBoolean(){
		if(filtroDinamicoSelecionado!=null){
			if(filtroDinamicoSelecionado!=null && filtroDinamicoSelecionado.getTypeCampo()!=null && filtroDinamicoSelecionado.getTypeCampo().isInstance(new Boolean(true)) || dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo().isInstance(new Boolean(true)))
					|| dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo().isInstance(new Boolean(true)))){
				return true;
			}
		}
		return false;
	}
	private RadioGroup<Boolean> criarCampoBoolean() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("valorCampoBoolean", new PropertyModel<Boolean>(this, "valorCampo")){
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				return verificarCampoBoolean();
			};
		};
		radioGroupAtivo.add(new Radio<Boolean>("sim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sim")));
		radioGroupAtivo.add(new Radio<Boolean>("nao", new Model<Boolean>(false)).add(new AttributeModifier("id", "nao")));

		return radioGroupAtivo;
	}
	
	private Boolean verificarCampoTextFieldString(){
		if(filtroDinamicoSelecionado!=null){
			if(filtroDinamicoSelecionado.getTypeCampo()!=null && filtroDinamicoSelecionado.getTypeCampo().isInstance(new String()) || dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo().isInstance(new String()))
					|| dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject()!=null && (dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getTypeCampo().isInstance(new String()))){
				return true;
			}
		}
		return false;
	}
	
	private TextField<String> criarCampoTextFieldString(){
		TextField<String> textFieldValorCampo = new TextField<String>("valorCampoText", new PropertyModel<String>(this, "valorCampo")){
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				return verificarCampoTextFieldString();
			};
		};
		textFieldValorCampo.setOutputMarkupId(true);
		return textFieldValorCampo;
	}
	
	private DropDownChoice<FiltroDinamicoAtributo> criarCampoFiltroDinamicoAtributoEstrangeiroNivel3(){
		
		IModel<List<FiltroDinamicoAtributo>> listaModelAtributosDisponiveis = new LoadableDetachableModel<List<FiltroDinamicoAtributo>>(){
			private static final long serialVersionUID = 1L;

			@Override
			protected List<FiltroDinamicoAtributo> load(){
				List<FiltroDinamicoAtributo> lista = new ArrayList<FiltroDinamicoAtributo>();
				if(dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject()!=null){
					if(AbstractBean.class.isAssignableFrom(dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo())){
						 Constructor<?> construtor;
						try {
							
							construtor = dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo().getConstructor();
							AbstractBean<?> abstractBean = (AbstractBean<?>) construtor.newInstance();
							lista.addAll(Util.getNameFieldEntity(abstractBean,false));
							
						} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					 }
				}
				
				return lista;
			}
		};
		
		ChoiceRenderer<FiltroDinamicoAtributo> renderer = new ChoiceRenderer<FiltroDinamicoAtributo>(){

			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(FiltroDinamicoAtributo object) {
				if(object.getNomeCampoPersonalidado()!=null){
					return object.getNomeCampoPersonalidado();
				}else if(object.getNomeCampo()!=null){
					return object.getNomeCampo();
				}
				return "";
			}
		};
		
		
		dropDownFiltroDinamicoAtributoEstrangeiroNivel3 = new DropDownChoice<FiltroDinamicoAtributo>("atributoEstrangeiroNivel3",new Model<FiltroDinamicoAtributo>(),listaModelAtributosDisponiveis, renderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(filtroDinamicoSelecionado!=null && filtroDinamicoSelecionado.getTypeCampo()!=null && AbstractBean.class.isAssignableFrom(filtroDinamicoSelecionado.getTypeCampo())){
					if(dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject()!=null && AbstractBean.class.isAssignableFrom(dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getTypeCampo())){	
						return true;
					}
				}
				
				return false;
			}
		};
		
		dropDownFiltroDinamicoAtributoEstrangeiroNivel3.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {	
				target.add(divCamposValorCampo);
				target.add(divOperadores);
			}
		});
		
		dropDownFiltroDinamicoAtributoEstrangeiroNivel3.setNullValid(true);
		dropDownFiltroDinamicoAtributoEstrangeiroNivel3.setOutputMarkupId(true);
		return dropDownFiltroDinamicoAtributoEstrangeiroNivel3;
	}
	


	private DropDownChoice<FiltroDinamicoAtributo> criarCampoFiltroDinamicoAtributoEstrangeiroNivel2(){
		
		IModel<List<FiltroDinamicoAtributo>> listaModelAtributosDisponiveis = new LoadableDetachableModel<List<FiltroDinamicoAtributo>>(){
			private static final long serialVersionUID = 1L;

			@Override
			protected List<FiltroDinamicoAtributo> load(){
				List<FiltroDinamicoAtributo> lista = new ArrayList<FiltroDinamicoAtributo>();
				if(AbstractBean.class.isAssignableFrom(filtroDinamicoSelecionado.getTypeCampo())){ // Apenas verifica se o tipo e uma instancia de abstractBean
					 Constructor<?> construtor;
					try {
						
						construtor = filtroDinamicoSelecionado.getTypeCampo().getConstructor();
						AbstractBean<?> abstractBean = (AbstractBean<?>) construtor.newInstance();
						lista.addAll(Util.getNameFieldEntity(abstractBean,true));
						
					} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				 }
				
				return lista;
			}
		};
		
		ChoiceRenderer<FiltroDinamicoAtributo> renderer = new ChoiceRenderer<FiltroDinamicoAtributo>(){

			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(FiltroDinamicoAtributo object) {
				if(object.getNomeCampoPersonalidado()!=null){
					return object.getNomeCampoPersonalidado();
				}else if(object.getNomeCampo()!=null){
					return object.getNomeCampo();
				}
				return "";
			}
		};
		
		
		dropDownFiltroDinamicoAtributoEstrangeiroNivel2 = new DropDownChoice<FiltroDinamicoAtributo>("atributoEstrangeiroNivel2",new Model<FiltroDinamicoAtributo>(),listaModelAtributosDisponiveis, renderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(filtroDinamicoSelecionado!=null && filtroDinamicoSelecionado.getTypeCampo()!=null && AbstractBean.class.isAssignableFrom(filtroDinamicoSelecionado.getTypeCampo())){
					return true;
				}
				
				return false;
			}
		};
		
		dropDownFiltroDinamicoAtributoEstrangeiroNivel2.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {	
				target.add(divCamposValorCampo);
				target.add(divAtributoEstrangeiro);
				target.add(divOperadores);
			}
		});
		
		dropDownFiltroDinamicoAtributoEstrangeiroNivel2.setNullValid(true);
		dropDownFiltroDinamicoAtributoEstrangeiroNivel2.setOutputMarkupId(true);
		return dropDownFiltroDinamicoAtributoEstrangeiroNivel2;
	}
	
	private DropDownChoice<FiltroDinamicoAtributo> criarCampoFiltroDinamicoAtributo(){
		
		IModel<List<FiltroDinamicoAtributo>> listaModelAtributosDisponiveis = new LoadableDetachableModel<List<FiltroDinamicoAtributo>>(){
			private static final long serialVersionUID = 1L;

			@Override
			protected List<FiltroDinamicoAtributo> load(){
				
				Collections.sort(listaFiltrosDisponiveisEntity, new Comparator<FiltroDinamicoAtributo>() {
					public int compare(FiltroDinamicoAtributo o1, FiltroDinamicoAtributo o2) {
						return o1.getNomeCampo().compareTo(o2.getNomeCampo());
					}
				});
				
				return listaFiltrosDisponiveisEntity;
			}
		};
		
		ChoiceRenderer<FiltroDinamicoAtributo> renderer = new ChoiceRenderer<FiltroDinamicoAtributo>("nomeCampo","uuid"){

			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(FiltroDinamicoAtributo object) {
				if(object.getNomeCampoPersonalidado()!=null){
					return object.getNomeCampoPersonalidado();
				}else if(object.getNomeCampo()!=null){
					return object.getNomeCampo();
				}
				return "";
			}
		};
		
		DropDownChoice<FiltroDinamicoAtributo> dropDownFiltroDinamicoAtributo = new DropDownChoice<FiltroDinamicoAtributo>("nomeCampo",new PropertyModel<FiltroDinamicoAtributo>(this, "filtroDinamicoSelecionado"),listaModelAtributosDisponiveis, renderer);
		
		dropDownFiltroDinamicoAtributo.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {	
				target.add(divAtributoEstrangeiro);
				target.add(divCamposValorCampo);
				target.add(divOperadores);
			}
		});
		
		dropDownFiltroDinamicoAtributo.setNullValid(true);
		dropDownFiltroDinamicoAtributo.setOutputMarkupId(true);
		return dropDownFiltroDinamicoAtributo;
	}
	
	
	
	private DropDownChoice<Integer> criarCampoOperadores(){
		IModel<List<Integer>> listaModelAtributosDisponiveis = new LoadableDetachableModel<List<Integer>>(){
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Integer> load(){
				List<Integer> lista = new ArrayList<Integer>();
				
				for(Integer operador:FiltroDinamicoAtributo.getOperadores()){
					if(operador.equals(FiltroDinamicoAtributo.LIKE)){
						if(!verificarCampoTextFieldString()){
							continue;
						}
					}
					
					if(verificarCampoBoolean()){
						if(!operador.equals(FiltroDinamicoAtributo.EQUALS)){
							continue;
						}
					}
					
					lista.add(operador);
				}
				
				return lista;
			}
		};
		
		
		DropDownChoice<Integer> tipoRadioChoiceOperadores = new DropDownChoice<Integer>("operadores", new PropertyModel<Integer>(this, "operador"), listaModelAtributosDisponiveis, WicketUtil.getOperadoresFiltroDinamico(this)){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return !verificarCampoBoolean();
			}
		};
		tipoRadioChoiceOperadores.setNullValid(false);
		tipoRadioChoiceOperadores.setOutputMarkupId(true);
		tipoRadioChoiceOperadores.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				getAttributes().setChannel(new AjaxChannel("blocking", AjaxChannel.Type.ACTIVE));
				
			}
		});
		
		return tipoRadioChoiceOperadores;
	}
	
	
	private AjaxButton criarBotaoSalvar(Form<FiltroDinamicoAtributo> form){
		AjaxButton botaoVoltar= new AjaxButton("salvar",form) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(valorCampo !=null && operador!=null){
					filtroDinamicoSelecionado.setValorCampo(valorCampo);
					filtroDinamicoSelecionado.setOperador(operador);
					if(dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject().getNomeCampo()!=null){	
						filtroDinamicoSelecionado.setAtributoEstrangeiro(dropDownFiltroDinamicoAtributoEstrangeiroNivel2.getModelObject());
						
						if(dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject()!=null && dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject().getNomeCampo()!=null){
							filtroDinamicoSelecionado.getAtributoEstrangeiro().setAtributoEstrangeiro(dropDownFiltroDinamicoAtributoEstrangeiroNivel3.getModelObject());
						}
					}
					
					executarAoSalvar(target, filtroDinamicoSelecionado);
				}else{
					Util.notifyError(target, "Informar o Valor do campo e o Tipo de pesquisa");
				}
			}
		};
		
		botaoVoltar.setOutputMarkupId(true);
		return botaoVoltar;
	}
	
	private AjaxLink<String> criarBotaoVoltar(){
		AjaxLink<String> botaoVoltar= new AjaxLink<String>("voltar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(focusGained){
					executarAoVoltar(target);
					modalEdit.close(target);
				}
			}
			
		};
		botaoVoltar.add(new InputBehavior(new KeyType[]{KeyType.Escape}, EventType.click));
		botaoVoltar.setOutputMarkupId(true);
		return botaoVoltar;
	}
	
	public void setValorCampo(Object valorCampo) {
		this.valorCampo = valorCampo;
	}
	
	public Object getValorCampo() {
		return valorCampo;
	}
	
	
	public FiltroDinamicoAtributo getFiltroDinamicoSelecionado() {
		return filtroDinamicoSelecionado;
	}

	public void setFiltroDinamicoSelecionado(FiltroDinamicoAtributo filtroDinamicoSelecionado) {
		this.filtroDinamicoSelecionado = filtroDinamicoSelecionado;
	}


	public void setOperador(Integer operador) {
		this.operador = operador;
	}
	
	public Integer getOperador() {
		return operador;
	}
	
	public abstract void executarAoVoltar(AjaxRequestTarget target);
	
	public abstract void executarAoSalvar(AjaxRequestTarget target,FiltroDinamicoAtributo filtroDinamicoAtributo);
	
}