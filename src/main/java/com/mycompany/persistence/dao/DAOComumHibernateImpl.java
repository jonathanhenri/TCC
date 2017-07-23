package com.mycompany.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.DAOException;
import com.mycompany.domain.AbstractBean;
import com.mycompany.persistence.interfaces.IDAOComum;
import com.mycompany.util.Util;

public class DAOComumHibernateImpl<T extends AbstractBean<T>, ID extends Serializable> extends GenericDAOImpl<T, ID> implements IDAOComum<T, ID>{

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public boolean save(T entity) {
		return super._merge(entity) != null;
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
	
	@Override
	public boolean persist(T arg0) {
		try{
			if(arg0.getAdministracao()!=null ){
				if(arg0.getAdministracao().getAluno() == null){
					arg0.getAdministracao().setAluno(Util.getAlunoLogado());
				}
				
				if(arg0.getAdministracao().getId()==null){
					super._save(arg0.getAdministracao());
				}
			}
			
			super._save(arg0);
		}catch(DAOException e ){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
}