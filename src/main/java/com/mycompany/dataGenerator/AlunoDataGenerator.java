package com.mycompany.dataGenerator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.persistence.interfaces.ICursoDAO;

public class AlunoDataGenerator {

	public static void generateData(IAlunoDAO alunooDAO,ICursoDAO cursoDAO) throws Exception{
		
		Aluno alunoAdministrador = new Aluno();
		alunoAdministrador.setNome("Administrador");
		alunoAdministrador.setSenha("admin");
		alunoAdministrador.setCpf("admin");
		alunoAdministrador.setPeriodo(5); 
		alunoAdministrador.setEmail("aluno@gmail");
		alunoAdministrador.addContadorAcesso();
		alunoAdministrador.setCurso((Curso) cursoDAO.search(new Search(Curso.class)).get(0));
		alunooDAO.persist(alunoAdministrador);
		
		
		Aluno alunoAdministrador2 = new Aluno();
		alunoAdministrador2.setNome("Jonathan");
		alunoAdministrador2.setSenha("admin");
		alunoAdministrador2.setCpf("70105574112");
		alunoAdministrador2.setPeriodo(5); 
		alunoAdministrador2.setEmail("aluno@gmail");
		alunoAdministrador2.addContadorAcesso();
		alunoAdministrador2.setCurso((Curso) cursoDAO.search(new Search(Curso.class)).get(0));
		alunooDAO.persist(alunoAdministrador2);
		
	}

}