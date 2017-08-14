package com.mycompany.dataGenerator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.persistence.interfaces.ICursoDAO;
import com.mycompany.persistence.interfaces.IPerfilAcessoDAO;
import com.mycompany.persistence.interfaces.IPermissaoAcessoDAO;

public class DataGenerator implements InitializingBean {
	
	private IAlunoDAO alunoDAO;
	private ICursoDAO cursoDAO;
	private IPermissaoAcessoDAO permissaoAcessoDAO;
	private IPerfilAcessoDAO perfilAcessoDAO;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void afterPropertiesSet() throws Exception {
		
		if(permissaoAcessoDAO.search(new Search(PermissaoAcesso.class)).size()==0){
			System.setProperty("ambienteDesenvolvimento", "true");
			PermissaoAcessoDataGenerator.generateData(permissaoAcessoDAO);
		}
		
		if(cursoDAO.search(new Search(Curso.class)).size() == 0){
			System.setProperty("ambienteDesenvolvimento", "true");
			CursoDataGenerator.generateData(cursoDAO);
		}
		
		if(alunoDAO.search(new Search(Aluno.class)).size()==0){
			System.setProperty("ambienteDesenvolvimento", "true");
			AlunoDataGenerator.generateData(alunoDAO,cursoDAO,permissaoAcessoDAO,perfilAcessoDAO);
		}
	}
	
	public void setPerfilAcessoDAO(IPerfilAcessoDAO perfilAcessoDAO) {
		this.perfilAcessoDAO = perfilAcessoDAO;
	}
	
	public void setPermissaoAcessoDAO(IPermissaoAcessoDAO permissaoAcessoDAO) {
		this.permissaoAcessoDAO = permissaoAcessoDAO;
	}
	
	public void setCursoDAO(ICursoDAO cursoDAO) {
		this.cursoDAO = cursoDAO;
	}
	
	public void setAlunoDAO(IAlunoDAO alunoDAO) {
		this.alunoDAO = alunoDAO;
	}
}