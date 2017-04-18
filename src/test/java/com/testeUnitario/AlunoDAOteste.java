package com.testeUnitario;


import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.domain.Aluno;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAlunoServico;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AlunoDAOteste extends BaseServiceTestCase {
	private static IAlunoServico alunoServico;


	public static void setAlunoServico(IAlunoServico alunoServico) {
		AlunoDAOteste.alunoServico = alunoServico;
	}

	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public void testeInserirAuno() {
		Aluno aluno = new Aluno();
		aluno.setCpf("teste");
		aluno.setNome("teste");
		aluno.setSenha("teste");
		Retorno retorno = alunoServico.persist(aluno);
		
		Assert.assertTrue(retorno.getSucesso());
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
