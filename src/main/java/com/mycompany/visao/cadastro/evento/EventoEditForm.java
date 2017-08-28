package com.mycompany.visao.cadastro.evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Evento;
import com.mycompany.domain.Materia;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.ColorTextField;
import com.mycompany.visao.comum.EditForm;

public class EventoEditForm extends EditForm<Evento> {
	
	@SpringBean(name="eventoServico")
	private  IEventoServico eventoServico;

	@SpringBean(name="materiaServico")
	private  IMateriaServico materiaServico;
	
	@SpringBean(name="tipoEventoServico")
	private  ITipoEventoServico tipoEventoServico;
	
	@SpringBean(name="origemEventoServico")
	private  IOrigemEventoServico origemEventoServico;
	
	private Evento evento;
	
	public EventoEditForm(Evento evento,Agenda agenda,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.evento = evento;
		this.evento.setAgenda(agenda);
	}
	
	public EventoEditForm(Evento evento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.evento = evento;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = eventoServico;
	}
	
	private ColorTextField criarCampoCodigoCor(){
		ColorTextField textFieldCodigoCor = new ColorTextField("codigoCor");
		textFieldCodigoCor.setOutputMarkupId(true);
		textFieldCodigoCor.add(StringValidator.lengthBetween(1, 40));
		return textFieldCodigoCor;
	}

	private DropDownChoice<Materia> criarCampoMateria(){
		IChoiceRenderer<Materia> choiceRenderer = new ChoiceRenderer<Materia>("nome", "id");
		LoadableDetachableModel<List<Materia>> materias = new LoadableDetachableModel<List<Materia>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Materia> load() {
				List<Materia> tiposEvento = new ArrayList<Materia>();
				
				tiposEvento = materiaServico.search(new Search(Materia.class));
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<Materia> tipoRadioChoice = new DropDownChoice<Materia>("materia", materias,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DropDownChoice<OrigemEvento> criarCampoOrigemEvento(){
		IChoiceRenderer<OrigemEvento> choiceRenderer = new ChoiceRenderer<OrigemEvento>("nome", "id");
		LoadableDetachableModel<List<OrigemEvento>> origensEventos = new LoadableDetachableModel<List<OrigemEvento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<OrigemEvento> load() {
				List<OrigemEvento> tiposEvento = new ArrayList<OrigemEvento>();
				
				tiposEvento = origemEventoServico.search(new Search(OrigemEvento.class));
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<OrigemEvento> tipoRadioChoice = new DropDownChoice<OrigemEvento>("origemEvento", origensEventos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DateTextField criarCampoDataFim(){
		
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
		DateTextField dataFim = new DateTextField("dataFim", new PropertyModel<Date>(getAbstractBean(), "dataFim"),new PatternDateConverter("dd/MM/yyyy", true));
		datePicker.setAutoHide(true);		
		dataFim.add(datePicker);
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}

	private DateTextField criarCampoDataInicio(){
		
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
		DateTextField dataInicio = new DateTextField("dataInicio", new PropertyModel<Date>(getAbstractBean(), "dataInicio"),new PatternDateConverter("dd/MM/yyyy", true));
		datePicker.setAutoHide(true);		
		dataInicio.add(datePicker);
		dataInicio.setOutputMarkupId(true);
		return dataInicio;
	}


	
	private DropDownChoice<TipoEvento> criarCampoTipoEvento(){
		IChoiceRenderer<TipoEvento> choiceRenderer = new ChoiceRenderer<TipoEvento>("nome", "id");
		LoadableDetachableModel<List<TipoEvento>> tiposEventos = new LoadableDetachableModel<List<TipoEvento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<TipoEvento> load() {
				List<TipoEvento> tiposEvento = new ArrayList<TipoEvento>();
				
				tiposEvento = tipoEventoServico.search(new Search(TipoEvento.class));
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<TipoEvento> tipoRadioChoice = new DropDownChoice<TipoEvento>("tipoEvento", tiposEventos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private TextField<String> criarCampoDescricao(){
		TextField<String> textFieldNome = new TextField<String>("descricao");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(1, 600));
		return textFieldNome;
	}
	
	private NumberTextField<Integer> criarCampoPeriodo(){
		NumberTextField<Integer> periodo = new NumberTextField<Integer>("periodo");
		periodo.setOutputMarkupId(true);
		return periodo;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoDescricao());
		add(criarCampoOrigemEvento());
		add(criarCampoTipoEvento());
		add(criarCampoDataFim());
		add(criarCampoDataInicio());
		add(criarCampoMateria());
		add(criarCampoCodigoCor());
		add(criarCampoPeriodo());
	}
	
	@Override
	protected Boolean validarRegrasAntesSalvarEditar(AjaxRequestTarget target) {
		return super.validarRegrasAntesSalvarEditar(target);
	}
	private static final long serialVersionUID = 1L;

	
}
