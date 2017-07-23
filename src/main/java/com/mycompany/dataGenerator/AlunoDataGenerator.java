package com.mycompany.dataGenerator;

import com.mycompany.domain.Administracao;
import com.mycompany.domain.Aluno;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.persistence.interfaces.ICursoDAO;

public class AlunoDataGenerator {

	public static void generateData(IAlunoDAO alunooDAO,ICursoDAO cursoDAO) throws Exception{
		
		Administracao administracao = new Administracao();
		administracao.setAdministradorCampus(true);
		
		Aluno alunoAdministrador = new Aluno();
		alunoAdministrador.setAdministracao(administracao);
		alunoAdministrador.setNome("Administrador");
		alunoAdministrador.setSenha("admin");
		alunoAdministrador.setLogin("admin");
		alunoAdministrador.setPeriodo(5); 
		alunooDAO.persist(alunoAdministrador);
		
		
		
		
	}

}