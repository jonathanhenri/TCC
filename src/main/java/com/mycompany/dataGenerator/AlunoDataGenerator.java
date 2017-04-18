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
		alunoAdministrador.setCurso((Curso) cursoDAO.search(new Search(Curso.class)).get(0));
		alunooDAO.persist(alunoAdministrador);
	}

}