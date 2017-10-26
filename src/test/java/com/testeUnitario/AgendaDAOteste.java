package com.testeUnitario;


import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Evento;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AgendaDAOteste extends BaseServiceTestCase {
	private static IEventoServico eventoServico;
	private static IAlunoServico alunoServico;
	private static IAgendaServico agendaServico;
	private static ICursoServico cursoServico;
	
	@Autowired
	public void setCursoServico(ICursoServico cursoServico) {
		AgendaDAOteste.cursoServico = cursoServico;
	}
	
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
		Aluno alunoLogado = new Aluno();
		alunoLogado.setLogin("admin");
		alunoLogado.setSenha("admin");
		alunoLogado.setId(new Long(1));
		alunoLogado = alunoServico.searchFetchAlunoLogado(alunoLogado);

		Authentication authRequest = new UsernamePasswordAuthenticationToken(alunoLogado, "admin", alunoLogado.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authRequest);

		Administracao administracao = new Administracao();
		administracao.setAluno(alunoLogado);
		administracao.setCurso(cursoServico.search(new Search()).get(0));
		
		Agenda agenda = new Agenda();
		agenda.setNome("Teste Junit");
		agenda.setAdministracao(administracao);
		Assert.assertTrue(agendaServico.persist(agenda).getSucesso());
		
		Search searchAgenda = new Search(Agenda.class);
		searchAgenda.addFilterEqual("id", agenda.getId());
		searchAgenda.addFetch("administracao");
		Agenda agendaEvento = agendaServico.searchUnique(searchAgenda);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		evento.setRepetirEvento(false);
		Assert.assertTrue(eventoServico.persist(evento).getSucesso());
		evento.setAgenda(agendaEvento);
		
		Assert.assertTrue(eventoServico.save(evento).getSucesso());
		
		Search searchAgendaAposAlterar = new Search(Agenda.class);
		searchAgendaAposAlterar.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAposAlterar.addFetch("eventos");
		Agenda agendaEventoAposAlterar = agendaServico.searchUnique(searchAgendaAposAlterar);
		
		Search searchteste = new Search();
		searchteste.addFilterEqual("agenda.id",agendaEventoAposAlterar.getId());
		
		Assert.assertEquals(1, eventoServico.search(searchteste).size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeAlterarAgenda() {
		Aluno alunoLogado = new Aluno();
		alunoLogado.setLogin("admin");
		alunoLogado.setSenha("admin");
		alunoLogado.setId(new Long(1));
		
		alunoLogado = alunoServico.searchFetchAlunoLogado(alunoLogado);

		Authentication authRequest = new UsernamePasswordAuthenticationToken(alunoLogado, "admin", alunoLogado.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authRequest);
		
		Administracao administracao = new Administracao();
		administracao.setAluno(alunoLogado);
		administracao.setCurso(cursoServico.search(new Search()).get(0));
		
		Agenda agenda = new Agenda();
		agenda.setNome("Teste Junit");
		agenda.setAdministracao(administracao);
		Assert.assertTrue(agendaServico.persist(agenda).getSucesso());
		
		Search searchAgenda = new Search(Agenda.class);
		searchAgenda.addFilterEqual("id", agenda.getId());
		Agenda agendaEvento = agendaServico.searchUnique(searchAgenda);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setRepetirEvento(false);
		evento.setDescricao("Teste Junit");
		evento.setAgenda(agendaEvento);
		Assert.assertTrue(eventoServico.persist(evento).getSucesso());
		
		
		Assert.assertTrue(eventoServico.save(evento).getSucesso());
		
		Search searchAgendaAposAlterar = new Search(Agenda.class);
		searchAgendaAposAlterar.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAposAlterar.addFetch("eventos");
		Agenda agendaEventoAposAlterar = agendaServico.searchUnique(searchAgendaAposAlterar);
		
		Search searchteste = new Search();
		searchteste.addFilterEqual("agenda.id",agendaEventoAposAlterar.getId());
		
		Assert.assertEquals(1, eventoServico.search(searchteste).size());
		
		Evento eventoAlterar = new Evento();
		eventoAlterar.setDataInicio(new Date());
		eventoAlterar.setDataFim(new Date());
		eventoAlterar.setRepetirEvento(false);
		eventoAlterar.setDescricao("Teste Junit 2");
		eventoAlterar.setAgenda(agendaEventoAposAlterar);
		Assert.assertTrue(eventoServico.persist(eventoAlterar).getSucesso());
	
		
		
		Search searchAgendaAdicionarEvento = new Search(Agenda.class);
		searchAgendaAdicionarEvento.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAdicionarEvento.addFetch("eventos");
		Agenda agendaEventoAdicionarEvento = agendaServico.searchUnique(searchAgendaAdicionarEvento);
		
		searchteste = new Search();
		searchteste.addFilterEqual("agenda.id",agendaEventoAdicionarEvento.getId());
		
		Assert.assertEquals(2, eventoServico.search(searchteste).size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeExcluirAgenda() {
		Aluno alunoLogado = new Aluno();
		alunoLogado.setLogin("admin");
		alunoLogado.setSenha("admin");
		alunoLogado.setId(new Long(1));
		
		alunoLogado = alunoServico.searchFetchAlunoLogado(alunoLogado);

		Authentication authRequest = new UsernamePasswordAuthenticationToken(alunoLogado, "admin", alunoLogado.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authRequest);
		
		Administracao administracao = new Administracao();
		administracao.setAluno(alunoLogado);
		administracao.setCurso(cursoServico.search(new Search()).get(0));
		
		Agenda agenda = new Agenda();
		agenda.setNome("Teste Junit");
		agenda.setAdministracao(administracao);
		Assert.assertTrue(agendaServico.persist(agenda).getSucesso());
		
		Search searchAgenda = new Search(Agenda.class);
		searchAgenda.addFilterEqual("id", agenda.getId());
		Agenda agendaEvento = agendaServico.searchUnique(searchAgenda);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setRepetirEvento(false);
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		Assert.assertTrue(eventoServico.persist(evento).getSucesso());
		evento.setAgenda(agendaEvento);
		
		Assert.assertTrue(eventoServico.save(evento).getSucesso());
		
		Search searchAgendaAposAlterar = new Search(Agenda.class);
		searchAgendaAposAlterar.addFilterEqual("id", agendaEvento.getId());
		searchAgendaAposAlterar.addFetch("eventos");
		Agenda agendaEventoAposAlterar = agendaServico.searchUnique(searchAgendaAposAlterar);
		
		Search searchteste = new Search();
		searchteste.addFilterEqual("agenda.id",agendaEventoAposAlterar.getId());
		
		Assert.assertEquals(1, eventoServico.search(searchteste).size());
		
		Assert.assertTrue(agendaServico.remove(agendaEventoAposAlterar).getSucesso());
	}
}
