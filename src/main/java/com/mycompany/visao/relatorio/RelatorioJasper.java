package com.mycompany.visao.relatorio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Evento;
import com.mycompany.domain.Materia;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.util.Util;

public class RelatorioJasper {
	private IEventoServico eventoServico;
	private Agenda agenda;
	private Evento evento;
	
	private List<Evento> listaTodosEventos;
	
	public JasperPrint gerarRelatorioAgenda(Evento evento,IEventoServico eventoServico,IAgendaServico agendaServico) throws Exception{
		this.eventoServico = eventoServico;
		this.agenda = (Agenda) agendaServico.searchFechId(evento.getAgenda());
		this.evento = evento;
		Map<String, Object> params = new HashMap<String, Object>();
		
		String nomeAgenda = agenda.getNome();
		String descricao = "Não informado";
		if(evento.getDescricao()!=null){
			descricao = evento.getDescricao();
		}
		
		String dataInicio = "Não informado";
		if(evento.getDataInicio()!=null){
			dataInicio = Util.formataDataSemLocale(evento.getDataInicio());
		}
		
		String dataFim = "Não informado";
		if(evento.getDataFim()!=null){
			dataFim = Util.formataDataSemLocale(evento.getDataFim());
		}
		
		String tipoEvento = "Não informado";
		if(evento.getTipoEvento()!=null){
			tipoEvento = evento.getTipoEvento().getNome();
		}
		
		String origemEvento = "Não informado";
		if(evento.getOrigemEvento()!=null){
			origemEvento = evento.getOrigemEvento().getNome();
		}
		
		String professor = "Não informado";
		if(evento.getProfessor()!=null){
			professor = evento.getProfessor();
		}
		
		String local = "Não informado";
		if(evento.getLocal()!=null){
			local = evento.getLocal();
		}
		
		String curso = "Não informado";
		if(evento.getAdministracao()!=null && evento.getAdministracao().getCurso()!=null){
			curso = evento.getAdministracao().getCurso().getNome();
		}
		
		String periodosAgenda = "Não informado";
		if(agenda.getListaPeriodosPertecentes()!=null && agenda.getListaPeriodosPertecentes().size()>0){
			for(RelacaoPeriodo relacaoPeriodo:agenda.getListaPeriodosPertecentes()){
				periodosAgenda.concat(String.valueOf(relacaoPeriodo.getPeriodo()));
			}
		}
		
		params.put("nomeAgenda",nomeAgenda);
		params.put("descricao",descricao);
		params.put("dataInicio", dataInicio);
		params.put("dataFim",dataFim);
		params.put("tipoEvento", tipoEvento);
		params.put("origemEvento",origemEvento);
		params.put("nomeAlunoLogado",Util.getAlunoLogado().getNome());
		params.put("professor",professor);
		params.put("local",local);
		params.put("curso",curso);
		params.put("periodosAgenda",periodosAgenda);
		params.put("logo_path", getLogo());
		
		buscaEvento();
		List<Map<String, Object>> relatorioPaiFields = new ArrayList<Map<String, Object>>();
		
		SimpleDateFormat formataData = new SimpleDateFormat("EEEEEE");
//		if(listaConferenciaProduto.size() > 0){
			HashMap<Date, List<EventoBean>> hashListaProduto =  popularHashMapEventoAgrupado();
			
			for(Date key:hashListaProduto.keySet()){
				List <EventoBean> eventosLista = hashListaProduto.get(key);
				Map<String, Object> itemMap = new HashMap<>();
				
				
//				Collections.sort(conferenciaProdutoLista, new Comparator<EventoBean>() {
//					Collator collator = Collator.getInstance(Locale.US);
//					
//					public int compare(EventoBean o1, EventoBean o2) {
//					    return collator.compare(o1.getNomeProduto(), o2.getNomeProduto());
//					}
//				});
				
				String data = Util.formataDataSemLocale(key);
				data+=" - "+formataData.format(key);
				itemMap.put("diaAgrupado",data);
				itemMap.put("eventos",new JRBeanCollectionDataSource(eventosLista));
				relatorioPaiFields.add(itemMap);
			}
//		}
		params.put("SUBREPORT_DIR","jaspers2/");
		
		InputStream inputStreamAgenda = new ByteArrayInputStream(Util.lerArquivo("agenda_horizontal.jasper", "jaspers2/", false));
		
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(inputStreamAgenda, params, new JRBeanCollectionDataSource(relatorioPaiFields, false));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (ClassCastException x) {
			x.printStackTrace();
		}
		return jasperPrint;
	}
	
	private void buscaEvento(){
		Search search = new Search(Evento.class);
		search.addFilterEqual("agenda.id", agenda.getId());
		
		if(evento.getTipoEvento()!=null){
			search.addFilterEqual("tipoEvento.id", evento.getTipoEvento().getId());
		}
		
		if(evento.getDataInicio()!=null && evento.getDataFim()!=null){
			search.addFilterGreaterOrEqual("dataInicio", Util.zeraHoraData(evento.getDataInicio()));
			search.addFilterLessOrEqual("dataFim", Util.ultimaHoraData(evento.getDataFim()));
		}
		
		if(evento.getDescricao()!=null){
			search.addFilterEqual("descricao", evento.getDescricao());
		}
		
		if(evento.getOrigemEvento()!=null){
			search.addFilterEqual("origemEvento.id", evento.getOrigemEvento().getId());
		}
		
		if(evento.getProfessor()!=null){
			search.addFilterEqual("professor", evento.getProfessor());
		}
		
		if(evento.getLocal()!=null){
			search.addFilterEqual("local", evento.getLocal());
		}
		
		listaTodosEventos = new ArrayList<Evento>();
		listaTodosEventos.addAll(eventoServico.search(search));
	}
	
	private void replicarEventoPorDiaEspecifico(Evento eventoParametro,Integer diaSemana){
		boolean sair = true;
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(evento.getDataInicio());
		
		Calendar calendarFim = Calendar.getInstance();
		calendarFim.setTime(evento.getDataFim());
		
		while(sair){
			if(Util.comparaDatas(calendarInicio.getTime(),calendarFim.getTime(), false) == 0){
				break;
			}
			calendarInicio.add(Calendar.DAY_OF_MONTH, 1);
			
			if(calendarInicio.get(Calendar.DAY_OF_WEEK) == diaSemana){
				Evento eventoNovo = eventoParametro.clonar(true);
				eventoNovo.setDataAuxiliar(calendarInicio.getTime());
				listaTodosEventos.add(eventoNovo);
			}
		}
	}
	
	private void replicarPopularListaEventos(){
		List<Evento> listaAux = new ArrayList<Evento>();
		listaAux.addAll(listaTodosEventos);
		
		listaTodosEventos = new ArrayList<Evento>();
		
		if(listaAux!=null && listaAux.size()>0){
			for(Evento evento:listaAux){
				if(evento.getRepetirEvento()!=null && evento.getRepetirEvento()){
					// Seg
					if(evento.getRepetirTodaSegunda()!=null && evento.getRepetirTodaSegunda()){
						replicarEventoPorDiaEspecifico(evento, Calendar.MONDAY);
					}
					
					// Ter
					if(evento.getRepetirTodaTerca()!=null && evento.getRepetirTodaTerca()){
						replicarEventoPorDiaEspecifico(evento, Calendar.TUESDAY);
					}

					// Quarta
					if(evento.getRepetirTodaQuarta()!=null && evento.getRepetirTodaQuarta()){
						replicarEventoPorDiaEspecifico(evento, Calendar.WEDNESDAY);
					}
					// Quinta
					if(evento.getRepetirTodaQuinta()!=null && evento.getRepetirTodaQuinta()){
						replicarEventoPorDiaEspecifico(evento, Calendar.THURSDAY);
					}
					
					// Sexta
					if(evento.getRepetirTodaSexta()!=null && evento.getRepetirTodaSexta()){
						replicarEventoPorDiaEspecifico(evento, Calendar.FRIDAY);
					}
					
					// Sab
					if(evento.getRepetirTodoSabado()!=null && evento.getRepetirTodoSabado()){
						replicarEventoPorDiaEspecifico(evento, Calendar.SATURDAY);
					}
					
					// Domingo
					if(evento.getRepetirTodoDomingo()!=null && evento.getRepetirTodoDomingo()){
						replicarEventoPorDiaEspecifico(evento, Calendar.SUNDAY);
					}
				}else{
					if(Util.comparaDatas(evento.getDataInicio(), evento.getDataFim(), false) == -1){
						replicarEventoPorPeriodoTempo(evento, evento.getDataInicio(), evento.getDataFim());
					}else{
						listaTodosEventos.add(evento);
					}
				}
			}
		}
	}
	
	private void replicarEventoPorPeriodoTempo(Evento evento,Date dataInicio, Date dataFim){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataInicio);
		
		boolean sair = true;
		
		if(Util.comparaDatas(calendar.getTime(), dataFim, false) == 0){
			Evento eventoNovo = evento.clonar(true);
			eventoNovo.setDataAuxiliar(calendar.getTime());
			listaTodosEventos.add(eventoNovo);
		}else{
			Evento eventoNovo2 = evento.clonar(true);
			eventoNovo2.setDataAuxiliar(calendar.getTime());
			listaTodosEventos.add(eventoNovo2);
			
			while(sair){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				
				Evento eventoNovo = evento.clonar(true);
				eventoNovo.setDataAuxiliar(calendar.getTime());
				listaTodosEventos.add(eventoNovo);
				
				if(Util.comparaDatas(calendar.getTime(), dataFim, false) == 0){
					break;
				}
				
			}
		}
	}
	
	private HashMap<Date, List<EventoBean>> popularHashMapEventoAgrupado(){
		replicarPopularListaEventos();
		HashMap<Date, List<EventoBean>> hashMapAgrupado = new HashMap<Date, List<EventoBean>>();
		
		if(listaTodosEventos!=null && listaTodosEventos.size()>0){
			for(Evento evento:listaTodosEventos){
				if(evento.getDataAuxiliar() == null){
					evento.setDataAuxiliar(evento.getDataInicio());
				}
				evento.setDataAuxiliar(Util.zeraHoraData(evento.getDataAuxiliar()));
				
				List<EventoBean> listaAux;
				if(hashMapAgrupado.containsKey(evento.getDataAuxiliar())){
					listaAux = hashMapAgrupado.get(evento.getDataAuxiliar());
				}else{
					listaAux = new ArrayList<EventoBean>();
				}
				EventoBean eventoBean = new EventoBean(evento);
				listaAux.add(eventoBean);
				hashMapAgrupado.put(evento.getDataAuxiliar(),listaAux);
			}
		}
		 return hashMapAgrupado;
	}
	
	
	
	public InputStream getLogo(){
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(Util.lerArquivo(getClass().getResource("logofundo.png").getFile(), "", false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return inputStream;
	}
	private String concatenarDiasRepeticao(Evento evento){
		String string = new String();
		if(evento.getRepetirEvento()){
			if(evento.getRepetirTodaSegunda()!=null && evento.getRepetirTodaSegunda()){
				string +="|Seg";
			}
			
			if(evento.getRepetirTodaTerca()!=null && evento.getRepetirTodaTerca()){
				string +="|Ter";
			}

			if(evento.getRepetirTodaQuarta()!=null && evento.getRepetirTodaQuarta()){
				string +="|Qua";
			}
			
			if(evento.getRepetirTodaQuinta()!=null && evento.getRepetirTodaQuinta()){
				string +="|Qui";
			}
			

			if(evento.getRepetirTodaSexta()!=null && evento.getRepetirTodaSexta()){
				string +="|Sex";
			}
			

			if(evento.getRepetirTodoSabado()!=null && evento.getRepetirTodoSabado()){
				string +="|Sab";
			}
			

			if(evento.getRepetirTodoDomingo()!=null && evento.getRepetirTodoDomingo()){
				string +="|Dom";
			}
			
			string +="|";
		}else{
			String dataInicio = "";
			String dataFim = "";
			
			dataFim = Util.formataDataSemLocale(evento.getDataFim());
			dataInicio = Util.formataDataSemLocale(evento.getDataInicio());
			
			string =  dataInicio + " a "+dataFim;
		}
		
		
		return string;
	}
	
	public class EventoBean{
		private String data;
		private String descricao;
		private String materia;
		private String local;
		private String professor;
		private String tipoEvento;
		private String origemEvento;
		
		EventoBean(Evento evento){
			setData(concatenarDiasRepeticao(evento));
			setDescricao(evento.getDescricao());
			setMateria(evento.getMateria());
			setLocal(evento.getLocal());
			setProfessor(evento.getProfessor());
			setTipoEvento(evento.getTipoEvento());
			setOrigemEvento(evento.getOrigemEvento());
		}
		
		public void setData(String data) {
			this.data = data;
		}
		
		public String getData() {
			return data;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao!=null?descricao:"";
		}
		
		public String getDescricao() {
			return descricao;
		}
		public void setMateria(Materia materia) {
			if(materia!=null){
				this.materia = materia.getNome();
			}else{
				this.materia = "";
			}
		}
		public String getMateria() {
			return materia;
		}
		public void setLocal(String local) {
			this.local = local!=null?local:"";
		}
		public String getLocal() {
			return local;
		}
		public void setProfessor(String professor) {
			this.professor = professor!=null?professor:"";
		}
		public String getProfessor() {
			return professor;
		}
		public void setTipoEvento(TipoEvento tipoEvento) {
			if(tipoEvento!=null){
				this.tipoEvento = tipoEvento.getNome();
			}else{
				this.tipoEvento = "";
			}
		}
		
		public String getTipoEvento() {
			return tipoEvento;
		}
		public void setOrigemEvento(OrigemEvento origemEvento) {
			if(origemEvento!=null){
				this.origemEvento = origemEvento.getNome();
			}else{
				this.origemEvento = "";
			}
		}
		
		public String getOrigemEvento() {
			return origemEvento;
		}
	}
	
}