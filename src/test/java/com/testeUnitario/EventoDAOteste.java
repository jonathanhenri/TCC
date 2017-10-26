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
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Evento;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.IEventoServico;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EventoDAOteste extends BaseServiceTestCase {
	private static IEventoServico eventoServico;
	private static IAlunoServico alunoServico;
	
	@Autowired
	public void setEventoServico(IEventoServico eventoServico) {
		EventoDAOteste.eventoServico = eventoServico;
	}
	
	@Autowired
	public void setAlunoServico(IAlunoServico alunoServico) {
		EventoDAOteste.alunoServico = alunoServico;
	}

	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeInserirEvento() {
		Aluno alunoLogado = new Aluno();
		alunoLogado.setLogin("admin");
		alunoLogado.setSenha("admin");
		alunoLogado.setId(new Long(1));
		
		alunoLogado = alunoServico.searchFetchAlunoLogado(alunoLogado);

		Authentication authRequest = new UsernamePasswordAuthenticationToken(alunoLogado, "admin", alunoLogado.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authRequest);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		evento.setRepetirEvento(false);
		
		Retorno retorno = eventoServico.persist(evento);
		Assert.assertTrue(retorno.getSucesso());
		
		Assert.assertNotNull(evento.getId());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeAlterarEvento() {
		Aluno alunoLogado = new Aluno();
		alunoLogado.setLogin("admin");
		alunoLogado.setSenha("admin");
		alunoLogado.setId(new Long(1));
		
		alunoLogado = alunoServico.searchFetchAlunoLogado(alunoLogado);

		Authentication authRequest = new UsernamePasswordAuthenticationToken(alunoLogado, "admin", alunoLogado.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authRequest);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		evento.setRepetirEvento(false);
		
		Retorno retorno = eventoServico.persist(evento);
		Assert.assertTrue(retorno.getSucesso());
		
		Assert.assertNotNull(evento.getId());
		
		
		Search searchEventoAlterar = new Search(Evento.class);
		searchEventoAlterar.addFilterEqual("id", evento.getId());
		Evento eventoAlterar = eventoServico.searchUnique(searchEventoAlterar);
		eventoAlterar.setDescricao("Teste alterar");
		eventoServico.save(eventoAlterar);
		
		searchEventoAlterar = new Search(Evento.class);
		searchEventoAlterar.addFilterEqual("id", eventoAlterar.getId());
		
		Evento eventoAlterarPesquisado = eventoServico.searchUnique(searchEventoAlterar);
		
		Assert.assertEquals("Teste alterar", eventoAlterarPesquisado.getDescricao());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeExcluirEvento() {
		Aluno alunoLogado = new Aluno();
		alunoLogado.setLogin("admin");
		alunoLogado.setSenha("admin");
		alunoLogado.setId(new Long(1));
		
		alunoLogado = alunoServico.searchFetchAlunoLogado(alunoLogado);

		Authentication authRequest = new UsernamePasswordAuthenticationToken(alunoLogado, "admin", alunoLogado.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authRequest);
		
		Evento evento = new Evento();
		evento.setDataInicio(new Date());
		evento.setDataFim(new Date());
		evento.setDescricao("Teste Junit");
		evento.setRepetirEvento(false);
		
		
		Retorno retorno = eventoServico.persist(evento);
		Assert.assertTrue(retorno.getSucesso());
		
		Assert.assertNotNull(evento.getId());
		
		
		Search searchEventoExcluir = new Search(Evento.class);
		searchEventoExcluir.addFilterEqual("id", evento.getId());
		Evento eventoExcluir = eventoServico.searchUnique(searchEventoExcluir);
		
		Assert.assertTrue(eventoServico.remove(eventoExcluir).getSucesso());
	}
}
