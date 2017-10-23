package com.testeUnitario;


import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Evento;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AgendaDAOteste extends BaseServiceTestCase {
	private static IEventoServico eventoServico;
	private static IAlunoServico alunoServico;
	private static IAgendaServico agendaServico;
	
	@Autowired
	public void setAgendaServico(IAgendaServico agendaServico) {
		AgendaDAOteste.agendaServico = agendaServico;
	}
	@Autowired
	public void setEventoServico(IEventoServico eventoServico) {
		AgendaDAOteste.eventoServico = eventoServico;
	}
	
	@Autowired
	public void setAlunoServico(IAlunoServico alunoServico) {
		AgendaDAOteste.alunoServico = alunoServico;
	}

	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeInserirAgenda() {
		Search search = new Search(Aluno.class);
		search.addFilterEqual("administracao.administradorCampus", true);
		Aluno aluno = alunoServico.searchUnique(search);
		SecurityContextHolder.getContext().setAuthentication((Authentication)aluno);
		
		Agenda agenda = new Agenda();
		agenda.setNome("Teste Junit");
		Assert.assertTrue(agendaServico.persist(agenda).getSucesso());
		
		Search searchAgenda = new Search(Agenda.class);
		searchAgenda.addFilterEqual("id", agenda.getId());
		Agenda agendaEvento = agendaServico.searchUnique(searchAgenda);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		Assert.assertTrue(eventoServico.persist(evento).getSucesso());
		evento.setAgenda(agendaEvento);
		
		Assert.assertTrue(eventoServico.save(evento).getSucesso());
		
		Search searchAgendaAposAlterar = new Search(Agenda.class);
		searchAgendaAposAlterar.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAposAlterar.addFetch("eventos");
		Agenda agendaEventoAposAlterar = agendaServico.searchUnique(searchAgendaAposAlterar);
		
		Assert.assertEquals(1, agendaEventoAposAlterar.getEventos().size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeAlterarAgenda() {
		Search search = new Search(Aluno.class);
		search.addFilterEqual("administracao.administradorCampus", true);
		Aluno aluno = alunoServico.searchUnique(search);
		SecurityContextHolder.getContext().setAuthentication((Authentication)aluno);
		
		Agenda agenda = new Agenda();
		agenda.setNome("Teste Junit");
		Assert.assertTrue(agendaServico.persist(agenda).getSucesso());
		
		Search searchAgenda = new Search(Agenda.class);
		searchAgenda.addFilterEqual("id", agenda.getId());
		Agenda agendaEvento = agendaServico.searchUnique(searchAgenda);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		Assert.assertTrue(eventoServico.persist(evento).getSucesso());
		evento.setAgenda(agendaEvento);
		
		Assert.assertTrue(eventoServico.save(evento).getSucesso());
		
		Search searchAgendaAposAlterar = new Search(Agenda.class);
		searchAgendaAposAlterar.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAposAlterar.addFetch("eventos");
		Agenda agendaEventoAposAlterar = agendaServico.searchUnique(searchAgendaAposAlterar);
		
		Assert.assertEquals(1, agendaEventoAposAlterar.getEventos().size());
		
		Evento eventoAlterar = new Evento();
		eventoAlterar.setDataInicio(new Date());
		eventoAlterar.setDataFim(new Date());
		eventoAlterar.setDescricao("Teste Junit");
		Assert.assertTrue(eventoServico.persist(eventoAlterar).getSucesso());
		eventoAlterar.setAgenda(agendaEventoAposAlterar);
		
		
		Search searchAgendaAdicionarEvento = new Search(Agenda.class);
		searchAgendaAdicionarEvento.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAdicionarEvento.addFetch("eventos");
		Agenda agendaEventoAdicionarEvento = agendaServico.searchUnique(searchAgendaAdicionarEvento);
		
		Assert.assertEquals(2, agendaEventoAdicionarEvento.getEventos().size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeExcluirAgenda() {
		Search search = new Search(Aluno.class);
		search.addFilterEqual("administracao.administradorCampus", true);
		Aluno aluno = alunoServico.searchUnique(search);
		SecurityContextHolder.getContext().setAuthentication((Authentication)aluno);
		
		Agenda agenda = new Agenda();
		agenda.setNome("Teste Junit");
		Assert.assertTrue(agendaServico.persist(agenda).getSucesso());
		
		Search searchAgenda = new Search(Agenda.class);
		searchAgenda.addFilterEqual("id", agenda.getId());
		Agenda agendaEvento = agendaServico.searchUnique(searchAgenda);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		Assert.assertTrue(eventoServico.persist(evento).getSucesso());
		evento.setAgenda(agendaEvento);
		
		Assert.assertTrue(eventoServico.save(evento).getSucesso());
		
		Search searchAgendaAposAlterar = new Search(Agenda.class);
		searchAgendaAposAlterar.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAposAlterar.addFetch("eventos");
		Agenda agendaEventoAposAlterar = agendaServico.searchUnique(searchAgendaAposAlterar);
		
		Assert.assertEquals(1, agendaEventoAposAlterar.getEventos().size());
		
		Assert.assertTrue(agendaServico.remove(agendaEventoAposAlterar).getSucesso());
	}
}
