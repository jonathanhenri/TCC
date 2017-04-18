package com.testeUnitario;


import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.persistence.interfaces.ICursoDAO;
import com.mycompany.services.interfaces.ICursoServico;
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CursoDAOteste extends BaseServiceTestCase {
//
	private static ICursoServico cursoServico;

	@Autowired
	public void seCursoServico(ICursoServico cursoServico) {
		CursoDAOteste.cursoServico = cursoServico;
	}


	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeInserirCurso() {
		Curso curso = new Curso();
		curso.setNome("sistemas");
		curso.setDuracao(5);
		curso.setModalidade(Curso.MODALIDADE_ANUAL);
		cursoServico.persist(curso);
		assertNotNull(curso.getId());
	}
//
//	@Test(expected=Exception.class)
//	@Rollback(true)
//	@Transactional(propagation=Propagation.REQUIRED)
//	public void testeInserirUsuarioSemLogin() {
//		Aluno usuario = new Aluno();
//		usuario.setSenha("123");
//		usuario.setNome("jonathan");
//		cursoServico.salvar(usuario);
//		Assert.assertNull(usuario.getId());
//	}
//	
//
//	@Test(expected=Exception.class)
//	@Rollback(true)
//	public void testeInserirUsuarioSemSenha() {
//		Aluno usuario = new Aluno();
//		usuario.setLogin("jona");
//		usuario.setNome("jonathan");
//		cursoServico.salvar(usuario);
//		assertNotNull(usuario.getId());
//	}
//	
//	
//	@Test
//	@Transactional(propagation=Propagation.REQUIRED)
//	@Rollback(true)
//	public void usuarioUsuarioTrue() {
//		Aluno usuario = new Aluno();
//		usuario.setLogin("jona");
//		usuario.setSenha("123");
//		usuario.setNome("123");
////		usuarioDAO.usuarioTrue(usuario);
//		assertNotNull(usuario.getId());
//	}
//	
//	@Test
//	@Transactional(propagation=Propagation.REQUIRED)
//	@Rollback(true)
//	public void usuarioAlterarUsuario() {
//		Aluno usuario = new Aluno();
//		usuario.setLogin("jona");
//		usuario.setSenha("123");
//		usuario.setNome("jonathan");
//		cursoServico.salvar(usuario);
//		assertNotNull(usuario.getId());
//		
//		usuario.setLogin("jona2");
//		cursoServico.alterar(usuario);
//		Assert.assertEquals(usuario.getLogin(), "jona2");
//	}
//
//	@Test(expected=Exception.class)
//	@Rollback(true)
//	public void usuarioAlterarUsuarioSemLogin() {
//		Aluno usuario = new Aluno();
//		usuario.setSenha("1234");
//		usuario.setNome("jonathan2");
//		assertNotNull(cursoServico.alterar(usuario));
//	}
//	
//	
//	@Test(expected=Exception.class)
//	@Rollback(true)
//	public void usuarioAlterarUsuarioSemSenha() {
//		Aluno usuario = new Aluno();
//		usuario.setLogin("jona2");
//		usuario.setNome("jonathan2");
//		cursoServico.alterar(usuario);
//		assertNotNull(usuario.getId());
//	}
//	
//	@Test
//	@Rollback(true)
//	public void usuarioExcluirUsuario() {
//		Aluno usuario = new Aluno();
//		usuario.setLogin("jona");
//		usuario.setSenha("123");
//		usuario.setNome("123");
//		cursoServico.salvar(usuario);
//		assertNotNull(usuario.getId());
//		
//		cursoServico.excluir(usuario);
//		Assert.assertNull(usuario.getId());
//	}
//
//	@Test
//	public void usuarioListagemTodosUsuario() {
//		List<Aluno> listaUsuarios = new ArrayList<Aluno>();
//
////		listaUsuarios = usuarioDAO.getUsuarios();
//
//		for(Aluno usuario : listaUsuarios){
//			System.out.println("Login: "+usuario.getLogin()+"  Senha: "+usuario.getSenha()+"  Nome: "+usuario.getNome());
//		}
//		Assert.assertTrue(listaUsuarios.size()>0);
//	}
//
}
