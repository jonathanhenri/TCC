package com.mycompany.services.servico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IAgendaDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IRelacaoPeriodoServico;
import com.mycompany.util.Util;

public class AgendaServico implements IAgendaServico {
	private IAgendaDAO agendaDAO;
	private IEventoServico eventoServico;
	private IRelacaoPeriodoServico relacaoPeriodoServico;
	
	public AgendaServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Agenda agenda) {
		Retorno retorno = validaRegrasAntesIncluir(agenda);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(agendaDAO.persist(agenda)){
				mensagem  = new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			atualizarListaRelacaoPeriodos(agenda);
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(Agenda.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			search.addFetch("eventos");
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return agendaDAO.consultarPorIdFetch(abstractBean.getId());
	}
	@Override
	public void searchComum(Search search){
		Filter filterOr = Filter.or();
		if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && !Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
		
			Aluno aluno = searchFetchAlunoLogado(Util.getAlunoLogado());
			
			if(aluno.getConfiguracao()!=null && aluno.getConfiguracao().getSincronizarAgenda()){
				Filter filterAnd = Filter.and();
				
				filterAnd.add(Filter.equal("administracao.aluno.configuracao.compartilharAgenda", true));
				
				if(aluno!=null && aluno.getListaPeriodosPertecentes().size()>0){
					
					Search search2 = new Search(RelacaoPeriodo.class);
					search2.addFilterIn("periodo", Util.getPeriodosListaRelacaoPeriodos(aluno.getListaPeriodosPertecentes()));
					search2.addFilterNotNull("agenda");
					List<RelacaoPeriodo> relacaoPeriodos =  relacaoPeriodoServico.search(search2);
					List<Long> idsAgenda = new ArrayList<Long>();
					if(relacaoPeriodos!=null && relacaoPeriodos.size()>0){
						for(RelacaoPeriodo relacaoPeriodo:relacaoPeriodos){
							idsAgenda.add(relacaoPeriodo.getAgenda().getId());
						}
					}
					filterAnd.add(Filter.in("id", idsAgenda));
				}
				
				filterOr.add(filterAnd);
			}
			
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
			
		
					
			search.addFilter(filterOr);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		searchComum(search);
		return agendaDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Agenda agenda) {
		Retorno retorno = validaRegrasAntesAlterar(agenda);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			atualizarListaRelacaoPeriodos(agenda);
			if(agendaDAO.save(agenda)){
				mensagem = new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Agenda agenda) {
		Retorno retorno = validaRegrasAntesRemover(agenda);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
				
			if(agendaDAO.remove(agenda)){
				mensagem = new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(agenda.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Agenda> search(Search search) {
		searchComum(search);
		return agendaDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Agenda searchUnique(Search search) {
		searchComum(search);
		return agendaDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasComuns(Agenda agenda){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		Search searchLoginRepetido = new Search(Agenda.class);
		searchLoginRepetido.addFilterEqual("nome", agenda.getNome());
		Filter filterOr = Filter.or(Filter.equal("administracao.curso.id", agenda.getAdministracao().getCurso().getId()),Filter.equal("administracao.administradorCampus", true));
		searchLoginRepetido.addFilter(filterOr);
		
		if(agenda.getId()!=null){
			searchLoginRepetido.addFilterNotEqual("id", agenda.getId());
		}
		
		int i = count(searchLoginRepetido);
		
		if(i>0){
			retorno.addMensagem(new Mensagem("Agenda","nome", Mensagem.MOTIVO_REPETIDO, Mensagem.ERRO));
			retorno.setSucesso(false);
		}
		
		return retorno;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Agenda agenda) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(agenda);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),agenda, PermissaoAcesso.OPERACAO_INCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_INCLUIR,Mensagem.ERRO));
		}
		
		if(agenda.getAdministracao()==null || agenda.getAdministracao()!=null && agenda.getAdministracao().getCurso() == null){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem("Curso é obrigatorio", Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(agenda));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Agenda agenda) {
		Retorno retorno = Util.verificarIdNulo(agenda);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),agenda, PermissaoAcesso.OPERACAO_ALTERAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_ALTERAR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(agenda);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(agenda));				
			}
		}
		
		
		return retorno;
	}
	
	private void atualizarListaRelacaoPeriodos(Agenda agenda){
		Search search = new Search(RelacaoPeriodo.class);
		search.addFilterEqual("agenda.id", agenda.getId());
		
		List<RelacaoPeriodo> listaRelacaoPeriodoAntiga = relacaoPeriodoServico.search(search);
		
		if(agenda.getListaPeriodosPertecentes()!=null && agenda.getListaPeriodosPertecentes().size()>0){
			for(RelacaoPeriodo periodoNovo:agenda.getListaPeriodosPertecentes()){
				if(periodoNovo.getAdministracao() == null){
					periodoNovo.setAdministracao(Util.clonar(agenda.getAdministracao(),false));
				}
			}
		}
		
		if(listaRelacaoPeriodoAntiga!=null && listaRelacaoPeriodoAntiga.size()>0){
			if(agenda.getListaPeriodosPertecentes()!=null && agenda.getListaPeriodosPertecentes().size()>0){
				for(RelacaoPeriodo periodoAntigo:listaRelacaoPeriodoAntiga){
					boolean excluir = true;
					for(RelacaoPeriodo periodoNovo:agenda.getListaPeriodosPertecentes()){
						if(periodoNovo.getId() == null){
							relacaoPeriodoServico.persist(periodoNovo);
						}
						
						if(periodoAntigo.getId().equals(periodoNovo.getId())){
							excluir = false;
						}
					}
					
					if(excluir){
						relacaoPeriodoServico.remove(periodoAntigo);
					}
				}
			}else{
				relacaoPeriodoServico.remove(listaRelacaoPeriodoAntiga);
			}
		}else if(agenda.getListaPeriodosPertecentes()!=null && agenda.getListaPeriodosPertecentes().size()>0){
			relacaoPeriodoServico.persist(Util.toList(agenda.getListaPeriodosPertecentes()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Agenda agenda) {
		Retorno retorno = Util.verificarIdNulo(agenda);
			
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),agenda, PermissaoAcesso.OPERACAO_EXCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_EXCLUIR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			agenda =(Agenda)searchFechId(agenda);
			retorno.addRetorno(eventoServico.remove(Util.toList(agenda.getEventos())));
			
			Search searchPeriodo = new Search(RelacaoPeriodo.class);
			searchPeriodo.addFilterEqual("agenda.id", agenda.getId());
			relacaoPeriodoServico.remove(relacaoPeriodoServico.search(searchPeriodo));
		}
		
		return retorno;
	}
	
	public void setRelacaoPeriodoServico(
			IRelacaoPeriodoServico relacaoPeriodoServico) {
		this.relacaoPeriodoServico = relacaoPeriodoServico;
	}
	
	public void setEventoServico(IEventoServico eventoServico) {
		this.eventoServico = eventoServico;
	}
	
	public void setAgendaDAO(IAgendaDAO agendaDAO) {
		this.agendaDAO = agendaDAO;
	}

	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return agendaDAO.searchFetchAlunoLogado(alunoLogado);
	}
}
