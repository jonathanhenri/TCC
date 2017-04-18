package com.mycompany.persistence.interfaces;
import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.ISearch;

public interface IDAOComum<T, ID extends Serializable> extends GenericDAO<T, ID>{
	@Override
	@Transactional
	public boolean save(T arg0);
	
	@Transactional
	public boolean persist(T arg0);
	
	@Override
	@Transactional
	public boolean remove(T arg0);
	
	
	@Override
	@Transactional
	public <RT> List<RT> search(ISearch arg0);
	
	@Override
	@Transactional
	public <RT> RT searchUnique(ISearch arg0);
}