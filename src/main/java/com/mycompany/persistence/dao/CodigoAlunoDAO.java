package com.mycompany.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;
import com.mycompany.persistence.interfaces.ICodigoAlunoDAO;
import com.mycompany.util.Util;


public class CodigoAlunoDAO extends DAOComumHibernateImpl<CodigoAluno, Long> implements ICodigoAlunoDAO{	

	public CodigoAlunoDAO() {
	}
	

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean persist(CodigoAluno codigoAluno) {
		return super.persist(codigoAluno);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean save(CodigoAluno codigoAluno) {
		return super.save(codigoAluno);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public boolean remove(CodigoAluno codigoAluno) {
		return super.remove(codigoAluno);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<CodigoAluno> getcodigoAlunos() {
		return super.findAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public CodigoAluno verificarCodigoAtivo(String codigo) {
		Search search = new Search(CodigoAluno.class);
		search.addFilterEqual("codigo", codigo);
		search.addFilterEqual("ativo", true);
		
		return super.searchUnique(search);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	private Boolean verificarCodigoExistente(String codigo) {
		Search search = new Search(CodigoAluno.class);
		search.addFilterEqual("codigo", codigo);
		
		int i = super._count(search);
		
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public CodigoAluno utilizarCodigoAluno(String codigo) {
		Search search = new Search(CodigoAluno.class);
		search.addFilterEqual("codigo", codigo);
		
		CodigoAluno codigoAluno = super.searchUnique(search);
		codigoAluno.setAtivo(false);
		
		save(codigoAluno);
		
		return codigoAluno;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	private String gerarCodigoAlunoUnico(){
		String codigo = "";
		
		while(true){
			codigo = Util.codigoGeradorAcesso();
			if(!verificarCodigoExistente(codigo)){
				break;
			}
			
		}
		return codigo;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = java.lang.Exception.class, timeout = 1200)
	public List<CodigoAluno> gerarCodigosAluno(int quantidade, Curso curso) {
		List<CodigoAluno> listaCodigoAluno = new ArrayList<CodigoAluno>();
		for(int i = 0; i<quantidade;i++){
			CodigoAluno codigoAluno = new CodigoAluno();
			codigoAluno.setAtivo(true);
			codigoAluno.setCurso(curso);
			codigoAluno.setCodigo(gerarCodigoAlunoUnico());
			
			listaCodigoAluno.add(codigoAluno);
		}
		return listaCodigoAluno;
	}
	
	
}
