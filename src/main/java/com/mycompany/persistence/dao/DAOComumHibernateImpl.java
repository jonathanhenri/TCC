package com.mycompany.persistence.dao;

import java.io.Serializable;
import java.sql.BatchUpdateException;

import org.hibernate.SessionFactory;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.mycompany.DAOException;
import com.mycompany.domain.AbstractBean;
import com.mycompany.persistence.interfaces.IDAOComum;

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
	public boolean remove(T entity) {
		return super._deleteById(entity.getClass(), entity.getIdentifier());
	}
	
	@Override
	public boolean persist(T arg0) {
		try{
			super._save(arg0);
		}catch(DAOException e ){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}