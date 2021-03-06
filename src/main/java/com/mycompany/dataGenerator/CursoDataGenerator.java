package com.mycompany.dataGenerator;

import com.mycompany.domain.Administracao;
import com.mycompany.domain.Curso;
import com.mycompany.persistence.interfaces.ICursoDAO;

public class CursoDataGenerator {

	public static void generateData(ICursoDAO cursoDAO) throws Exception{
		Curso curso = new Curso();
		curso.setNome("Sistemas de Informação");
		curso.setDuracao(4);
		
		Administracao administracao = new Administracao();
		administracao.setCurso(curso);
		curso.setAdministracao(administracao);
		cursoDAO.persist(curso);
		
		
	}

}