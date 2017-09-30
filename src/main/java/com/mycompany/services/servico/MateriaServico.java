package com.mycompany.services.servico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Materia;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IMateriaDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IRelacaoPeriodoServico;
import com.mycompany.util.Util;

public class MateriaServico implements IMateriaServico {
	private IMateriaDAO materiaDAO;
	private IRelacaoPeriodoServico relacaoPeriodoServico;
	
	public MateriaServico() {
	}

	private void atualizarListaRelacaoPeriodos(Materia materia){
		Search search = new Search(RelacaoPeriodo.class);
		search.addFilterEqual("materia.id", materia.getId());
		
		List<RelacaoPeriodo> listaRelacaoPeriodoAntiga = relacaoPeriodoServico.search(search);
		
		if(materia.getListaPeriodosPertecentes()!=null && materia.getListaPeriodosPertecentes().size()>0){
			for(RelacaoPeriodo periodoNovo:materia.getListaPeriodosPertecentes()){
				if(periodoNovo.getAdministracao() == null){
					periodoNovo.setAdministracao(Util.clonar(materia.getAdministracao(),false));
				}
			}
		}
		if(listaRelacaoPeriodoAntiga!=null && listaRelacaoPeriodoAntiga.size()>0){
			if(materia.getListaPeriodosPertecentes()!=null && materia.getListaPeriodosPertecentes().size()>0){
				for(RelacaoPeriodo periodoAntigo:listaRelacaoPeriodoAntiga){
					boolean excluir = true;
					for(RelacaoPeriodo periodoNovo:materia.getListaPeriodosPertecentes()){
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
		}else if(materia.getListaPeriodosPertecentes()!=null && materia.getListaPeriodosPertecentes().size()>0){
			relacaoPeriodoServico.persist(Util.toList(materia.getListaPeriodosPertecentes()));
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Materia materia) {
		Retorno retorno = validaRegrasAntesIncluir(materia);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(materiaDAO.persist(materia)){
				mensagem  = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			atualizarListaRelacaoPeriodos(materia);
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
		if(abstractBean!=null && abstractBean.getId()!=null){
			Search search = new Search(Materia.class);
			search.addFilterEqual("id", abstractBean.getId());
			
			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
				search.addFetch(fetch);
			}
			
			return  (AbstractBean<?>) searchUnique(search);
		}
		return null;
	}
	
	@Override
	public void searchComum(Search search){
		Filter filterOr = Filter.or();
		if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && !Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
		
			Aluno aluno = searchFetchAlunoLogado(Util.getAlunoLogado());
			
			if(aluno.getConfiguracao()!=null && aluno.getConfiguracao().getSincronizarMateria()){
				Filter filterCompartilhar = Filter.or(Filter.equal("administracao.aluno.configuracao.compartilharMateria", true));
				filterOr.add(filterCompartilhar);
			}
			
			if(aluno!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", aluno.getId()));
			}
			
			if(aluno!=null && aluno.getListaPeriodosPertecentes().size()>0){
				
				Search search2 = new Search(RelacaoPeriodo.class);
				search2.addFilterIn("periodo", Util.getPeriodosListaRelacaoPeriodos(aluno.getListaPeriodosPertecentes()));
				search2.addFilterNotNull("materia");
				List<RelacaoPeriodo> relacaoPeriodos =  relacaoPeriodoServico.search(search2);
				List<Long> idsMaterias = new ArrayList<Long>();
				if(relacaoPeriodos!=null && relacaoPeriodos.size()>0){
					for(RelacaoPeriodo relacaoPeriodo:relacaoPeriodos){
						idsMaterias.add(relacaoPeriodo.getMateria().getId());
					}
				}
				filterOr.add(Filter.in("id", idsMaterias));
			}
			search.addFilter(filterOr);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		searchComum(search);
		return materiaDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Materia materia) {
		Retorno retorno = validaRegrasAntesAlterar(materia);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			atualizarListaRelacaoPeriodos(materia);
			if(materiaDAO.save(materia)){
				mensagem = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Materia materia) {
		Retorno retorno = validaRegrasAntesRemover(materia);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(materiaDAO.remove(materia)){
				mensagem = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Materia> search(Search search) {
		searchComum(search);
		return materiaDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Materia searchUnique(Search search) {
		searchComum(search);
		return materiaDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasComuns(Materia materia){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		Search searchLoginRepetido = new Search(Materia.class);
		searchLoginRepetido.addFilterEqual("nome", materia.getNome());
		Filter filterOr = Filter.or(Filter.equal("administracao.curso.id", materia.getAdministracao().getCurso().getId()),Filter.equal("administracao.administradorCampus", true));
		searchLoginRepetido.addFilter(filterOr);
		
		if(materia.getId()!=null){
			searchLoginRepetido.addFilterNotEqual("id", materia.getId());
		}
		
		int i = count(searchLoginRepetido);
		
		if(i>0){
			retorno.addMensagem(new Mensagem("Materia","nome", Mensagem.MOTIVO_REPETIDO, Mensagem.ERRO));
			retorno.setSucesso(false);
		}
		
		return retorno;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Materia materia) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(materia);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),materia, PermissaoAcesso.OPERACAO_INCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_INCLUIR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(materia));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Materia materia) {
		Retorno retorno = Util.verificarIdNulo(materia);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),materia, PermissaoAcesso.OPERACAO_ALTERAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_ALTERAR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(materia);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(materia));				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Materia materia) {
		Retorno retorno = Util.verificarIdNulo(materia);
			
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),materia, PermissaoAcesso.OPERACAO_EXCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_EXCLUIR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			Search searchPeriodo = new Search(RelacaoPeriodo.class);
			searchPeriodo.addFilterEqual("materia.id", materia.getId());
			relacaoPeriodoServico.remove(relacaoPeriodoServico.search(searchPeriodo));
		}
		
		return retorno;
	}
	

	public void setRelacaoPeriodoServico(
			IRelacaoPeriodoServico relacaoPeriodoServico) {
		this.relacaoPeriodoServico = relacaoPeriodoServico;
	}
	
	public void setMateriaDAO(IMateriaDAO materiaDAO) {
		this.materiaDAO = materiaDAO;
	}

	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return materiaDAO.searchFetchAlunoLogado(alunoLogado);
	}


	@Override
	public List<Materia> getMaterias() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return materiaDAO.getMaterias();
		}
		
		return null;
	}
}
