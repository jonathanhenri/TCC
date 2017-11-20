package com.mycompany.services.servico;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IRelacaoPeriodoDAO;
import com.mycompany.services.interfaces.IRelacaoPeriodoServico;

public class RelacaoPeriodoServico implements IRelacaoPeriodoServico{
	private IRelacaoPeriodoDAO relacaoPeriodoDAO;
	
	public RelacaoPeriodoServico() {
	}
	public void setRelacaoPeriodoDAO(IRelacaoPeriodoDAO relacaoPeriodoDAO) {
		this.relacaoPeriodoDAO = relacaoPeriodoDAO;
	}
	@Override
	public List<RelacaoPeriodo> search(Search search) {
		return relacaoPeriodoDAO.search(search);
	}
	@Override
	public RelacaoPeriodo searchUnique(Search search) {
		return relacaoPeriodoDAO.searchUnique(search);
	}
	@Override
	public void remove(List<RelacaoPeriodo> listaRelacaoPeriodos) {
		if(listaRelacaoPeriodos!=null && listaRelacaoPeriodos.size()>0){
			for(RelacaoPeriodo relacaoPeriodo:listaRelacaoPeriodos){
				remove(relacaoPeriodo);
			}
		}
	}
	
	@Override
	public void persist(List<RelacaoPeriodo> listaRelacaoPeriodos) {
		if(listaRelacaoPeriodos!=null && listaRelacaoPeriodos.size()>0){
			for(RelacaoPeriodo relacaoPeriodo:listaRelacaoPeriodos){
				persist(relacaoPeriodo);
			}
		}
	}
	
	@Override
	public Retorno remove(RelacaoPeriodo relacaoPeriodo) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(relacaoPeriodoDAO.remove(relacaoPeriodo));
		return retorno;
	}
	@Override
	public Retorno persist(RelacaoPeriodo relacaoPeriodo) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(relacaoPeriodoDAO.persist(relacaoPeriodo));
		return retorno;
	}
	@Override
	public Retorno save(RelacaoPeriodo relacaoPeriodo) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(relacaoPeriodoDAO.save(relacaoPeriodo));
		return retorno;
	}
	@Override
	public int count(Search search) {
		return relacaoPeriodoDAO.count(search);
	}
	
	@Override
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
		return relacaoPeriodoDAO.consultarPorIdFetch(abstractBean.getId());
	}
	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Retorno validaRegrasAntesIncluir(RelacaoPeriodo salvar) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Retorno validaRegrasAntesAlterar(RelacaoPeriodo alterar) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Retorno validaRegrasAntesRemover(RelacaoPeriodo remover) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Retorno validaRegrasComuns(RelacaoPeriodo object) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void searchComum(Search search) {
		// TODO Auto-generated method stub
		
	}

}
