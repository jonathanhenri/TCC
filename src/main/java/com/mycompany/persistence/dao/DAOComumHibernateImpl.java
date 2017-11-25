package com.mycompany.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.mycompany.DAOException;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.persistence.interfaces.IDAOComum;
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
		Long id = alunoLogado.getId();
		
		 if (id == null) {
	            throw new IllegalArgumentException("O id é obrigatorio.");
	        }
	        try {
	            StringBuffer hql = new StringBuffer();
	            hql.append(" SELECT u" +
	                       " FROM Aluno u" +
	                       " JOIN FETCH u.administracao a" +
	                       " LEFT JOIN FETCH u.perfilAcesso pf" +
	                       " LEFT JOIN FETCH u.configuracao cf" +
	                       " LEFT JOIN FETCH a.curso cur" +
	                       " LEFT JOIN FETCH a.aluno alua" +
	                       " WHERE u.id = :id");

	            Query query = getSession().createQuery(hql.toString());

	            query.setParameter("id", id);

	            Object resultado = null;

	            resultado = query.uniqueResult();

	            if(resultado!=null){
	                Aluno aluno = (Aluno) resultado;
	                return aluno;
	            }

	        } catch (Exception e) {
	            throw new DAOException("Erro ao buscar registro por id: " + id, e);
	        }
		}
		
	    return null;
	}
	
	
	@Override
	public int count(Search search) {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
			if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
				return super.count(search);
			}
		
			if( Util.getAlunoLogado().getAdministracao().getCurso()!=null){
				search.addFilterEqual("administracao.curso.id", Util.getAlunoLogado().getAdministracao().getCurso().getId());
			}
		}
		return super.count(search);
	}
	
	@Override
	public List<T> search(Search search) {
		//Ignora perfil
		if(search.getSearchClass()!=null && search.getSearchClass().isInstance(new PermissaoAcesso())){
			return super.search(search);
		}
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
			if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
				return super.search(search);
			}
			
			if(Util.getAlunoLogado().getAdministracao().getCurso()!=null){
				search.addFilterEqual("administracao.curso.id", Util.getAlunoLogado().getAdministracao().getCurso().getId());
			}
		}
		
		return super.search(search);
	}
	
	@SuppressWarnings("unchecked")
	private T fetchAdministracao(AbstractBean<?> abstractBean){
		if(abstractBean !=null && abstractBean.getAdministracao()!=null && abstractBean.getAdministracao().getId()!=null){
			Search search = new Search(Administracao.class);
			search.addFetch("aluno");
			search.addFetch("curso");
			search.addFilterEqual("id", abstractBean.getAdministracao().getId());
			
			Administracao administracao = (Administracao) _searchUnique(search);
			abstractBean.setAdministracao(administracao);
		}
		return (T) abstractBean;
	}
	
	@Override
	public T searchUnique(Search search) {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
			if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
				return fetchAdministracao((AbstractBean<?>) super.searchUnique(search));
			}
			
			if( Util.getAlunoLogado().getAdministracao().getCurso()!=null){
				search.addFilterEqual("administracao.curso.id", Util.getAlunoLogado().getAdministracao().getCurso().getId());
			}
		}
		return fetchAdministracao((AbstractBean<?>) super.searchUnique(search));
	}

	@Override
	public boolean remove(T entity) {
		return super._deleteById(entity.getClass(), entity.getIdentifier());
	}
	
	private void inicializarAdministracao(T entity){
		if(entity instanceof AbstractBean){
			if(entity.getAdministracao()!=null ){
				if(entity instanceof Aluno){
					entity.getAdministracao().setAluno((Aluno)entity);
				}else{
					if(entity.getAdministracao().getAluno() == null){
						entity.getAdministracao().setAluno(Util.getAlunoLogado());
					}
				}
				if(entity instanceof Curso){
					entity.getAdministracao().setCurso((Curso) entity);
				}else{
					if(entity.getAdministracao().getCurso() == null && Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null){
						entity.getAdministracao().setCurso(Util.getAlunoLogado().getAdministracao().getCurso());
					}
				}
				
				if(entity.getAdministracao().getId()==null){
					super._save(entity.getAdministracao());
				}
			}else{
				Administracao administracao = new Administracao();
				if(entity instanceof Aluno){
					administracao.setAluno((Aluno)entity);
				}else{
					administracao.setAluno(Util.getAlunoLogado());
				}
				
				if(entity instanceof Curso){
					administracao.setCurso((Curso) entity);
				}else{
					if(Util.getAlunoLogado().getAdministracao()!=null && Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao().getCurso()!=null){
						administracao.setCurso(Util.getAlunoLogado().getAdministracao().getCurso());
					}
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
			
			if(entity.getResalvarAdministracao()!=null && entity.getResalvarAdministracao()){
				super._save(entity.getAdministracao());
			}
			
		}catch(DAOException e ){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
}