package com.mycompany.visao.cadastro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import com.googlecode.genericdao.search.Search;
import com.mycompany.DetachableUsuarioModel;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IServiceComum;
import com.mycompany.util.Util;

public class AbstractBeanDataProvider extends SortableDataProvider<AbstractBean<?>, String> {
	private static final long serialVersionUID = 1L;

	IServiceComum servicoComum;
	
	private AbstractBean<?> abstractBean;
	protected Search search;
	private Integer size;
	private List<AbstractBean<?>> listaBean;
	private String[] colPropertyExpression;
	protected Boolean pesquisaPadrao = true;
	
	public AbstractBeanDataProvider(IServiceComum servicoComum, String[] colPropertyExpression) {
		this.servicoComum = servicoComum;
		this.colPropertyExpression=colPropertyExpression;
		
		if(search == null){
			search = new Search();
			search.addSort("id", true); // ordena quando inicializar
		}
	}
	
	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<AbstractBean<?>> getListaBean() {
		return listaBean;
	}

	public void setListaBean(List<AbstractBean<?>> listaBean) {
		this.listaBean = listaBean;
	}
	
	@Override
	public Iterator<AbstractBean<?>> iterator(long first, long count) {
		try{
			if(search == null){
				search = new Search();
				search.addSort("id", true);
			}
			search.setFirstResult((int)first);
			search.setMaxResults((int)count);
			
			if(Util.possuiPermissao(servicoComum.searchFetchAlunoLogado(Util.getAlunoLogado()),abstractBean, PermissaoAcesso.OPERACAO_PESQUISAR)){
				listaBean =  (List<AbstractBean<?>>) servicoComum.search(search);
			}else{
				search = new Search();
				listaBean = new ArrayList<AbstractBean<?>>();
			}
			
			
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		return listaBean.iterator();
	}
	@Override
	public long size() {
		try{
			if(size == null){
				if (search == null)
				{
					search = new Search();
					search.addSort("id", true);
				}
				
				SortParam<String> sp = getSort();
				if(sp!=null){
					search.addSort(sp.getProperty(), !sp.isAscending(), false);
				}
				
				addFilters();
				if(Util.possuiPermissao(servicoComum.searchFetchAlunoLogado(Util.getAlunoLogado()),abstractBean, PermissaoAcesso.OPERACAO_PESQUISAR)){
					size = servicoComum.count(search);
				}else{
					size = 0;
				}
			}
			return size;
		}catch(Exception e ){
			if(e instanceof ClassCastException){
				System.err.println();
				Util.getAlunoLogado().addMensagemSistema(new Mensagem("Erro ao realizar a pesquisa, coloque dados coerentes com o campo pesquisado.", Mensagem.ERRO));
			}
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public IModel<AbstractBean<?>> model(AbstractBean<?> object) {
		return new DetachableUsuarioModel(object, servicoComum);
	}
	
	public AbstractBean<?> getFilterState() {
		return abstractBean;
	}
	
	public void setFilterState(AbstractBean<?> abstractBean) {
		this.abstractBean = abstractBean;
	}
	@Override
	public void detach() {
		super.detach();
		size = null;
		
		if(search!=null){
			search.clearSorts();
			search.clearFields();
			search.clearFilters();
		}
	}
	
	public void addFilters(){
		if(pesquisaPadrao){	
			HashMap<String, Object> hashMapPesquisa = Reflexao.getFieldValuePesquisaListarPage(abstractBean);
			
			for(String key:hashMapPesquisa.keySet()){
				Object valorCampo = hashMapPesquisa.get(key);
				if(valorCampo instanceof String){
					search.addFilterLike(key, "%" + valorCampo + "%");	
				}else{
					search.addFilterEqual(key, valorCampo);
				}
			}
		}
	}

	public void setColPropertyExpression(String[] colPropertyExpression){
		this.colPropertyExpression = colPropertyExpression;
	}
}
