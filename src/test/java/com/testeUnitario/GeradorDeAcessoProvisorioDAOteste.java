package com.testeUnitario;


import java.util.Date;
import java.util.List;

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
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Evento;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GeradorDeAcessoProvisorioDAOteste extends BaseServiceTestCase {
	private static IAlunoServico alunoServico;
	private static ICodigoAlunoServico codigoAlunoServico;
	
	@Autowired
	public void setCodigoAlunoServico(
			ICodigoAlunoServico codigoAlunoServico) {
		GeradorDeAcessoProvisorioDAOteste.codigoAlunoServico = codigoAlunoServico;
	}
	
	@Autowired
	public void setAlunoServico(IAlunoServico alunoServico) {
		GeradorDeAcessoProvisorioDAOteste.alunoServico = alunoServico;
	}

	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeInserirCodigoAluno() {
		Search search = new Search(Aluno.class);
		search.addFilterEqual("administracao.administradorCampus", true);
		Aluno aluno = alunoServico.searchUnique(search);
		SecurityContextHolder.getContext().setAuthentication((Authentication)aluno);
		
		CodigoAluno codigoAluno = new CodigoAluno();
		codigoAluno.setQuantidadeAlunosAux(5);
		
		Assert.assertTrue(codigoAlunoServico.persist(codigoAluno).getSucesso());
		
		//???
	}
}
