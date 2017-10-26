package com.testeUnitario;


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
import com.mycompany.domain.Aluno;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.services.interfaces.ICursoServico;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GeradorDeAcessoProvisorioDAOteste extends BaseServiceTestCase {
	private static IAlunoServico alunoServico;
	private static ICodigoAlunoServico codigoAlunoServico;
	private static ICursoServico cursoServico;
	
	@Autowired
	public void setCursoServico(ICursoServico cursoServico) {
		GeradorDeAcessoProvisorioDAOteste.cursoServico = cursoServico;
	}
	
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
		
		CodigoAluno codigoAluno = new CodigoAluno();
		codigoAluno.setQuantidadeAlunosAux(5);
		codigoAluno.setAdministracao(administracao);
		codigoAluno.setCursoAux(administracao.getCurso());
		
		Assert.assertTrue(codigoAlunoServico.persist(codigoAluno).getSucesso());
	}
}
