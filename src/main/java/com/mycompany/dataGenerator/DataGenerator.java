package com.mycompany.dataGenerator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.persistence.interfaces.ICursoDAO;

public class DataGenerator implements InitializingBean {
	
	private IAlunoDAO alunoDAO;
	private ICursoDAO cursoDAO;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void afterPropertiesSet() throws Exception {

		if(cursoDAO.search(new Search(Curso.class)).size() == 0){
			System.setProperty("ambienteDesenvolvimento", "true");
			CursoDataGenerator.generateData(cursoDAO);
		}
		
		if(alunoDAO.search(new Search(Aluno.class)).size()==0){
			System.setProperty("ambienteDesenvolvimento", "true");
			AlunoDataGenerator.generateData(alunoDAO,cursoDAO);
		}
		
	}
	
	public void setCursoDAO(ICursoDAO cursoDAO) {
		this.cursoDAO = cursoDAO;
	}
	
	public void setAlunoDAO(IAlunoDAO alunoDAO) {
		this.alunoDAO = alunoDAO;
	}
}