package com.mycompany.dataGenerator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.persistence.interfaces.ICursoDAO;
import com.mycompany.persistence.interfaces.IPerfilAcessoDAO;
import com.mycompany.persistence.interfaces.IPermissaoAcessoDAO;
import com.mycompany.util.Util;

public class AlunoDataGenerator {

	public static void generateData(IAlunoDAO alunooDAO,ICursoDAO cursoDAO,IPermissaoAcessoDAO permissaoAcessoDAO,IPerfilAcessoDAO perfilAcessoDAO) throws Exception{
		Administracao administracaoPerfil = new Administracao();
		PerfilAcesso perfilAcessoAdministrador = new PerfilAcesso();
		perfilAcessoAdministrador.setAdministracao(administracaoPerfil);
		perfilAcessoAdministrador.setNome("Perfil Administrador");
		perfilAcessoAdministrador.setPermissoesAcesso(Util.toSet(permissaoAcessoDAO.search(new Search(PermissaoAcesso.class))));
		perfilAcessoDAO.persist(perfilAcessoAdministrador);
		
		Administracao administracao = new Administracao();
		administracao.setAdministradorCampus(true);
		
		Aluno alunoAdministrador = new Aluno();
		alunoAdministrador.setPerfilAcesso(perfilAcessoAdministrador);
		alunoAdministrador.setAdministracao(administracao);
		alunoAdministrador.setNome("Administrador");
		alunoAdministrador.setSenha("admin");
		alunoAdministrador.setLogin("admin");
		alunoAdministrador.setPeriodo(5); 
		alunooDAO.persist(alunoAdministrador);
		
		
		
		
	}

}