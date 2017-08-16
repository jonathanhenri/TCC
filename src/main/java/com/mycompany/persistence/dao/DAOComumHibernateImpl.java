package com.mycompany.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.DAOException;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Aluno;
import com.mycompany.persistence.interfaces.IDAOComum;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.util.Util;

public class DAOComumHibernateImpl<T extends AbstractBean<T>, ID extends Serializable> extends GenericDAOImpl<T, ID> implements IDAOComum<T, ID>{

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public boolean save(T entity) {
		inicializarAdministracao(entity);
		return super._merge(entity) != null;
	}
	
	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		if(alunoLogado!=null && alunoLogado.getId()!=null){
			Search search = new Search(Aluno.class);
			search.addFilterEqual("id", alunoLogado.getId());
			
			for(String fetch: Reflexao.getListaAtributosEstrangeiros(alunoLogado)){
				search.addFetch(fetch);
			}
			return  (Aluno)_searchUnique(search);
		}else{
			return null;
		}
	}
	
	@Override
	public int count(Search search) {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
			if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
				return super.count(search);
			}
			
			Filter filterOr = Filter.or();
			
			if( Util.getAlunoLogado().getAdministracao().getCurso()!=null){
				search.addFilterEqual("administracao.curso.id", Util.getAlunoLogado().getAdministracao().getCurso().getId());
			}
			
			Filter filterCompartilhar = Filter.equal("administracao.compartilhar", true);
			
			
			filterOr.add(filterCompartilhar);
			
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
					
			search.addFilter(filterOr);
		}
		return super.count(search);
	}
	
	
	@Override
	public List<T> search(Search search) {
		
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
			if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
				return super.search(search);
			}
			
			Filter filterOr = Filter.or();
			
			if( Util.getAlunoLogado().getAdministracao().getCurso()!=null){
				search.addFilterEqual("administracao.curso.id", Util.getAlunoLogado().getAdministracao().getCurso().getId());
			}
			
			Filter filterCompartilhar = Filter.equal("administracao.compartilhar", true);
			
			
			filterOr.add(filterCompartilhar);
			
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
					
			search.addFilter(filterOr);
		}
		
		return super.search(search);
	}
	
	@Override
	public T searchUnique(Search search) {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
			if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
				return super.searchUnique(search);
			}
			
			if( Util.getAlunoLogado().getAdministracao().getCurso()!=null){
				search.addFilterEqual("administracao.curso.id", Util.getAlunoLogado().getAdministracao().getCurso().getId());
			}
			
			Filter filterCompartilhar = Filter.equal("administracao.compartilhar", true);
			
			Filter filterOr = Filter.or();
			filterOr.add(filterCompartilhar);
			
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
			search.addFilter(filterOr);
		}
		return super.searchUnique(search);
	}

	@Override
	public boolean remove(T entity) {
		return super._deleteById(entity.getClass(), entity.getIdentifier());
	}
	
	private void inicializarAdministracao(T entity){
		if(entity instanceof AbstractBean){
			if(entity.getAdministracao()!=null ){
				if(entity.getAdministracao().getAluno() == null){
					entity.getAdministracao().setAluno(Util.getAlunoLogado());
				}
				
				if(entity.getAdministracao().getCurso() == null && Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
					entity.getAdministracao().setCurso(Util.getAlunoLogado().getAdministracao().getCurso());
				}
				
				if(entity.getAdministracao().getId()==null){
					super._save(entity.getAdministracao());
				}
			}else{
				Administracao administracao = new Administracao();
				administracao.setAluno(Util.getAlunoLogado());
				
				if(Util.getAlunoLogado().getAdministracao()!=null && Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao().getCurso()!=null){
					administracao.setCurso(Util.getAlunoLogado().getAdministracao().getCurso());
				}
				entity.setAdministracao(administracao);
				
				if(entity.getAdministracao().getId()==null){
					super._save(entity.getAdministracao());
				}
			}
		}
	}
	@Override
	public boolean persist(T entity) {
		try{
			inicializarAdministracao(entity);
			super._save(entity);
		}catch(DAOException e ){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
}