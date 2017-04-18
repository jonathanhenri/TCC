package com.mycompany;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.services.interfaces.IServiceComum;

public class DetachableUsuarioModel extends LoadableDetachableModel<AbstractBean<?>> {
	private static final long serialVersionUID = 1L;
	@SpringBean(name="servicoComum")
	IServiceComum<?> servicoComum;
	private long id;
	
	public DetachableUsuarioModel(AbstractBean<?> entity, IServiceComum<?> servicoComum){
		super(entity);
		this.id = entity.getId();
		this.servicoComum = servicoComum;
		
	}
	
	
	@Override
	protected AbstractBean<?> load() {
		Search search = new Search(AbstractBean.class);
		search.addFilterEqual("id", id);
		return (AbstractBean<?>) servicoComum.searchUnique(search);
	}
}
