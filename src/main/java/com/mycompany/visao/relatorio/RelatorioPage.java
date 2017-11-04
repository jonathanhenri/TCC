package com.mycompany.visao.relatorio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Evento;
import com.mycompany.domain.Materia;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.AJAXDownload;
import com.mycompany.visao.comum.Menu;

public class RelatorioPage extends Menu implements IAjaxIndicatorAware {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="eventoServico")
	private  IEventoServico eventoServico;

	@SpringBean(name="materiaServico")
	private  IMateriaServico materiaServico;
	
	@SpringBean(name="tipoEventoServico")
	private  ITipoEventoServico tipoEventoServico;
	
	@SpringBean(name="origemEventoServico")
	private  IOrigemEventoServico origemEventoServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	@SpringBean(name="agendaServico")
	private IAgendaServico agendaServico;
	
	private Evento evento;
	private JasperPrint jasperPrint;
	private AJAXDownload ajaxDownloadPdf;
	
	private Form<Evento> form;
	public RelatorioPage() {
		inicializarPag();
		
		adicionarCampos();
	}

	
	private void inicializarPag(){
		evento = new Evento();
		evento.setAdministracao(new Administracao());
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.set(Calendar.DAY_OF_MONTH, calendarInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendarInicio.set(Calendar.HOUR_OF_DAY, calendarInicio.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendarInicio.set(Calendar.MINUTE, calendarInicio.getActualMinimum(Calendar.MINUTE));
		evento.setDataInicio(calendarInicio.getTime());
		
		Calendar calendarFim = Calendar.getInstance();
		calendarFim.set(Calendar.DAY_OF_MONTH, calendarFim.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendarFim.set(Calendar.HOUR_OF_DAY, calendarFim.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendarFim.set(Calendar.MINUTE, calendarFim.getActualMaximum(Calendar.MINUTE));
		evento.setDataFim(calendarFim.getTime());
		evento.setAgruparPorDia(false);
	}
	
	private void adicionarCampos(){
		form = new Form<Evento>("form", new CompoundPropertyModel<Evento>(evento));
		form.setOutputMarkupId(true);
		
		form.add(criarCampoAgenda());
		form.add(criarCampoCurso());
		form.add(criarCampoMateria());
		form.add(criarCampoTipoEvento());
		form.add(criarCampoOrigemEvento());
		form.add(criarCampoDataFim());
		form.add(criarCampoDataInicio());
		form.add(criarCampoAgruparPorDia());
		form.add(criarCampoDescricao());
		form.add(criarCampoProfessor());
		form.add(criarCampoLocal());
		form.add(criarImprimirPdfAjaxDownload());
		form.add(criarBaixarPdfAgenda());
		add(form);
	}
	
	private TextField<String> criarCampoLocal(){
		TextField<String> textFieldNome = new TextField<String>("local");
		textFieldNome.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(0, 600));
		return textFieldNome;
	}
	
	private TextField<String> criarCampoProfessor(){
		TextField<String> textFieldNome = new TextField<String>("professor");
		textFieldNome.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(0, 600));
		return textFieldNome;
	}
	
	private TextField<String> criarCampoDescricao(){
		TextField<String> textFieldDescricao = new TextField<String>("descricao");
		textFieldDescricao.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		textFieldDescricao.setOutputMarkupId(true);
		textFieldDescricao.add(StringValidator.lengthBetween(0, 600));
		return textFieldDescricao;
	}
	
	private DateTimeField criarCampoDataFim(){
		DateTimeField dataFim = new DateTimeField("dataFim");
		dataFim.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}
	
	private RadioGroup<Boolean> criarCampoAgruparPorDia() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("agruparPorDia");
		radioGroupAtivo.add(new Radio<Boolean>("agruparPorDiaSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "agruparPorDiaSim")));
		radioGroupAtivo.add(new Radio<Boolean>("agruparPorDiaNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "agruparPorDiaNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		radioGroupAtivo.add(new AjaxFormChoiceComponentUpdatingBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}
		});

		return radioGroupAtivo;
	}
	
	private DateTimeField criarCampoDataInicio(){
		DateTimeField dataFim = new DateTimeField("dataInicio");
		dataFim.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}
	
	
	private DropDownChoice<OrigemEvento> criarCampoOrigemEvento(){
		IChoiceRenderer<OrigemEvento> choiceRenderer = new ChoiceRenderer<OrigemEvento>("nome", "id");
		
		LoadableDetachableModel<List<OrigemEvento>> origensEvento = new LoadableDetachableModel<List<OrigemEvento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<OrigemEvento> load() {
				List<OrigemEvento> origensEvento = new ArrayList<OrigemEvento>();
				
				Search search = new Search(OrigemEvento.class);
				if(evento.getAdministracao().getCurso()!=null){
					search.addFilterEqual("administracao.curso.id",evento.getAdministracao().getCurso().getId());
				}
				origensEvento = origemEventoServico.search(search);
				return origensEvento;
			}
		};
		
		final DropDownChoice<OrigemEvento> tipoRadioChoice = new DropDownChoice<OrigemEvento>("origemEvento", origensEvento,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(agendaServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_ORIGEM_EVENTO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR)){
					return false;
				}
				return true;
			}
			
			@Override
			public boolean isEnabled() {
				return evento.getAdministracao().getCurso()!=null;
			}
		};
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(form);
			}
		});
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DropDownChoice<TipoEvento> criarCampoTipoEvento(){
		IChoiceRenderer<TipoEvento> choiceRenderer = new ChoiceRenderer<TipoEvento>("nome", "id");
		
		LoadableDetachableModel<List<TipoEvento>> tiposEvento = new LoadableDetachableModel<List<TipoEvento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<TipoEvento> load() {
				List<TipoEvento> tiposEventos = new ArrayList<TipoEvento>();
				
				Search search = new Search(TipoEvento.class);
				if(evento.getAdministracao().getCurso()!=null){
					search.addFilterEqual("administracao.curso.id",evento.getAdministracao().getCurso().getId());
				}
				tiposEventos = tipoEventoServico.search(search);
				
				return tiposEventos;
			}
		};
		
		final DropDownChoice<TipoEvento> tipoRadioChoice = new DropDownChoice<TipoEvento>("tipoEvento", tiposEvento,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(agendaServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_TIPO_EVENTO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR)){
					return false;
				}
				return true;
			}
			
			@Override
			public boolean isEnabled() {
				return evento.getAdministracao().getCurso()!=null;
			}
		};
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(form);
			}
		});
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DropDownChoice<Materia> criarCampoMateria(){
		IChoiceRenderer<Materia> choiceRenderer = new ChoiceRenderer<Materia>("nome", "id");
		
		LoadableDetachableModel<List<Materia>> materias = new LoadableDetachableModel<List<Materia>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Materia> load() {
				List<Materia> materias = new ArrayList<Materia>();
				
				Search search = new Search(Materia.class);
				if(evento.getAdministracao().getCurso()!=null){
					search.addFilterEqual("administracao.curso.id",evento.getAdministracao().getCurso().getId());
				}
				materias = materiaServico.search(search);
				
				return materias;
			}
		};
		
		final DropDownChoice<Materia> tipoRadioChoice = new DropDownChoice<Materia>("materia", materias,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(agendaServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_MATERIA_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR)){
					return false;
				}
				return true;
			}
			
			@Override
			public boolean isEnabled() {
				return evento.getAdministracao().getCurso()!=null;
			}
		};
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(form);
			}
		});
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DropDownChoice<Agenda> criarCampoAgenda(){
		IChoiceRenderer<Agenda> choiceRenderer = new ChoiceRenderer<Agenda>("nome", "id");
		
		LoadableDetachableModel<List<Agenda>> agendas = new LoadableDetachableModel<List<Agenda>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Agenda> load() {
				List<Agenda> agendas = new ArrayList<Agenda>();
				
				Search search = new Search(Agenda.class);
				if(evento.getAdministracao().getCurso()!=null){
					search.addFilterEqual("administracao.curso.id",evento.getAdministracao().getCurso().getId());
				}
				agendas = agendaServico.search(search);
				
				if(agendas!=null && agendas.size() == 1){
					evento.setAgenda(agendas.get(0));
				}
				return agendas;
			}
		};
		
		final DropDownChoice<Agenda> tipoRadioChoice = new DropDownChoice<Agenda>("agenda", agendas,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(agendaServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_AGENDA_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR)){
					return false;
				}
				return true;
			}
			
			@Override
			public boolean isEnabled() {
				return evento.getAdministracao().getCurso()!=null;
			}
		};
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(form);
			}
		});
		tipoRadioChoice.setNullValid(false);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DropDownChoice<Curso> criarCampoCurso(){
		IChoiceRenderer<Curso> choiceRenderer = new ChoiceRenderer<Curso>("nome", "id");
		
		LoadableDetachableModel<List<Curso>> cursos = new LoadableDetachableModel<List<Curso>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Curso> load() {
				List<Curso> cursos = new ArrayList<Curso>();
				
				cursos = cursoServico.search(new Search(Curso.class));
				
				return cursos;
			}
		};
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("administracao.curso", cursos,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(agendaServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_CURSO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR)){
					return false;
				}
				return true;
			}
		};
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(form);
			}
		});
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	
	private Boolean validarCamposObrigatorios(AjaxRequestTarget target){
		Boolean isOk = true;
		
		if(evento.getAdministracao().getCurso() == null){
			Util.notifyError(target, "Curso é obrigatório");
			isOk = false;
		}
		
		if(evento.getAgenda() == null){
			Util.notifyError(target, "Agenda é obrigatório");
			isOk = false;
		}else{
			Search search = new Search(Evento.class);
			search.addFilterEqual("agenda.id", evento.getAgenda().getId());
			
			if(evento.getAgruparPorDia()!=null && evento.getAgruparPorDia()){
				search.addFilterEqual("repetirEvento",true);
			}
			int i = 0;
			
			i = eventoServico.count(search);
			
			if(i<=0){
				Util.notifyError(target, "Não existem eventos cadastrados para essa agenda.");
				isOk = false;
			}
		}
		
		return isOk;
	}
	private AjaxButton criarBaixarPdfAgenda(){
		AjaxButton ajaxLink = new AjaxButton("imprimir") {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(validarCamposObrigatorios(target)){
					try {
						jasperPrint =  new  RelatorioJasper().gerarRelatorioAgenda(evento, eventoServico,agendaServico);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(jasperPrint!=null){
						ajaxDownloadPdf.initiate(target);
					}else{
						Util.notifyError(target, "Erro ao gerar o pdf de agenda.");
					}
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				Util.notifyError(target, "Erro ao gerar pdf de agenda.");
				super.onError(target, form);
			}
		};
		
		return ajaxLink;
	}
	
	private AJAXDownload criarImprimirPdfAjaxDownload(){
		
	    ajaxDownloadPdf = new AJAXDownload() {
			private static final long serialVersionUID = 1L;

			@Override
			protected IResourceStream getResourceStream() {
				AbstractResourceStreamWriter rstream = new AbstractResourceStreamWriter() {
					private static final long serialVersionUID = 1L;
					@Override
		            public void write(OutputStream output) throws IOException {
						try {
							JasperExportManager.exportReportToPdfStream(jasperPrint, output); 
							output.flush();
		            		output.close();
		            		
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};

				ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(rstream, "Agenda.pdf");        
				getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
				
				return rstream;
			}
			
			@Override
			protected String getFileName() {
				return "Agenda.pdf";
			}
		};
		
		return ajaxDownloadPdf;
	}
	
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public Evento getEvento() {
		return evento;
	}


	@Override
	public String getAjaxIndicatorMarkupId() {
		return "aguardePdf";
	}
}
