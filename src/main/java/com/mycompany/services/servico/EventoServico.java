package com.mycompany.services.servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Evento;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IEventoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IRelacaoPeriodoServico;
import com.mycompany.util.Util;

public class EventoServico implements IEventoServico {
	private IEventoDAO eventoDAO;
	private IRelacaoPeriodoServico relacaoPeriodoServico;
	
	public EventoServico() {
	}

	
	private void atualizarListaRelacaoPeriodos(Evento evento){
		Search search = new Search(RelacaoPeriodo.class);
		search.addFilterEqual("evento.id", evento.getId());
		
		List<RelacaoPeriodo> listaRelacaoPeriodoAntiga = relacaoPeriodoServico.search(search);
		
		if(evento.getListaPeriodosPertecentes()!=null && evento.getListaPeriodosPertecentes().size()>0){
			for(RelacaoPeriodo periodoNovo:evento.getListaPeriodosPertecentes()){
				if(periodoNovo.getAdministracao() == null){
					periodoNovo.setAdministracao(Util.clonar(evento.getAdministracao(),false));
				}
			}
		}
		
		if(listaRelacaoPeriodoAntiga!=null && listaRelacaoPeriodoAntiga.size()>0){
			if(evento.getListaPeriodosPertecentes()!=null && evento.getListaPeriodosPertecentes().size()>0){
				for(RelacaoPeriodo periodoAntigo:listaRelacaoPeriodoAntiga){
					boolean excluir = true;
					for(RelacaoPeriodo periodoNovo:evento.getListaPeriodosPertecentes()){
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
		}else if(evento.getListaPeriodosPertecentes()!=null && evento.getListaPeriodosPertecentes().size()>0){
			relacaoPeriodoServico.persist(Util.toList(evento.getListaPeriodosPertecentes()));
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Evento evento) {
		if(evento.getDataInicio() == null){
			evento.setDataInicio(new Date());
		}
		
		Retorno retorno = validaRegrasAntesIncluir(evento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(eventoDAO.persist(evento)){
				mensagem  = new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			atualizarListaRelacaoPeriodos(evento);
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(Evento.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return eventoDAO.consultarPorIdFetch(abstractBean.getId());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasComuns(Evento evento){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);

		Search search = new Search(Evento.class);
		search.addFilterEqual("descricao", evento.getDescricao());
		
		if(evento.getId()!=null){
			search.addFilterNotEqual("id",evento.getId());
		}
		
		int i = count(search);
		
		if(i>0){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem("Evento","descrição", Mensagem.MOTIVO_REPETIDO, Mensagem.ERRO));
		}
		
		if(evento.getRepetirEvento() == null){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem("Evento","Repetir Evento", Mensagem.MOTIVO_NULO, Mensagem.ERRO));
		}else{
			if(evento.getRepetirEvento()){
				if(evento.getRepetirTodaSegunda() == null && evento.getRepetirTodaTerca() == null && evento.getRepetirTodaQuarta() == null && evento.getRepetirTodaQuinta() == null && evento.getRepetirTodaSexta() == null &&
				   evento.getRepetirTodoSabado() == null && evento.getRepetirTodoDomingo() == null){
					retorno.setSucesso(false);
					retorno.addMensagem(new Mensagem("Evento","Dia da repetição", Mensagem.MOTIVO_NULO, Mensagem.ERRO));
				}
			}else{
				if(evento.getDataInicio() == null || evento.getDataFim() == null){
					retorno.setSucesso(false);
					retorno.addMensagem(new Mensagem("Evento","Data de inicio e fim", Mensagem.MOTIVO_NULO, Mensagem.ERRO));
				}
			}
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Evento evento) {
		Retorno retorno = validaRegrasAntesAlterar(evento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			atualizarListaRelacaoPeriodos(evento);
			if(eventoDAO.save(evento)){
				mensagem = new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		searchComum(search);
		return eventoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(List<Evento> listaEvento) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		if(listaEvento!=null && listaEvento.size()>0){
			for(Evento evento:listaEvento){
				retorno = validaRegrasAntesRemover(evento);
				if(retorno.getSucesso()){
					retorno.setSucesso(remove(evento).getSucesso());
				}
			}
		}
		
		return retorno;
	}
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Evento evento) {
		Retorno retorno = validaRegrasAntesRemover(evento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(eventoDAO.remove(evento)){
				mensagem = new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(evento.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Evento> search(Search search) {
		searchComum(search);
		return eventoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Evento searchUnique(Search search) {
		searchComum(search);
		return eventoDAO.searchUnique(search);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Evento evento) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(evento);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),evento, PermissaoAcesso.OPERACAO_INCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_INCLUIR,Mensagem.ERRO));
		}
		
		if(evento.getAdministracao()==null || evento.getAdministracao()!=null && evento.getAdministracao().getCurso() == null){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem("Curso é obrigatorio", Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(evento));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Evento evento) {
		Retorno retorno = Util.verificarIdNulo(evento);

		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),evento, PermissaoAcesso.OPERACAO_ALTERAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_ALTERAR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(evento);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(evento));
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Evento evento) {
		Retorno retorno = Util.verificarIdNulo(evento);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),evento, PermissaoAcesso.OPERACAO_EXCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_EXCLUIR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			Search searchPeriodo = new Search(RelacaoPeriodo.class);
			searchPeriodo.addFilterEqual("evento.id", evento.getId());
			relacaoPeriodoServico.remove(relacaoPeriodoServico.search(searchPeriodo));
		}
		
		return retorno;
	}
	

	public void setRelacaoPeriodoServico(
			IRelacaoPeriodoServico relacaoPeriodoServico) {
		this.relacaoPeriodoServico = relacaoPeriodoServico;
	}
	public void setEventoDAO(IEventoDAO eventoDAO) {
		this.eventoDAO = eventoDAO;
	}

	@Override
	public void searchComum(Search search){
		Filter filterOr = Filter.or();
		if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && !Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
			Aluno aluno = searchFetchAlunoLogado(Util.getAlunoLogado());
			
			if(aluno.getConfiguracao()!=null && aluno.getConfiguracao().getSincronizarEvento()){
				Filter filterAnd = Filter.and();
				
				filterAnd.add(Filter.equal("administracao.aluno.configuracao.compartilharEvento", true));
				
				if(aluno!=null && aluno.getListaPeriodosPertecentes().size()>0){
					
					Search search2 = new Search(RelacaoPeriodo.class);
					search2.addFilterIn("periodo", Util.getPeriodosListaRelacaoPeriodos(aluno.getListaPeriodosPertecentes()));
					search2.addFilterNotNull("evento");
					List<RelacaoPeriodo> relacaoPeriodos =  relacaoPeriodoServico.search(search2);
					List<Long> idsEvento = new ArrayList<Long>();
					if(relacaoPeriodos!=null && relacaoPeriodos.size()>0){
						for(RelacaoPeriodo relacaoPeriodo:relacaoPeriodos){
							idsEvento.add(relacaoPeriodo.getEvento().getId());
						}
					}
					filterAnd.add(Filter.in("id", idsEvento));
				}
				filterOr.add(filterAnd);
			}
			
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
			
					
			search.addFilter(filterOr);
		}
	}

	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return eventoDAO.searchFetchAlunoLogado(alunoLogado);
	}

	@Override
	public List<Evento> getEventos() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return eventoDAO.getEventos();
		}
		
		return null;
	}
}
