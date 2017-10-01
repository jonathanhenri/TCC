package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.ICodigoAlunoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.util.Util;

public class CodigoAlunoServico implements ICodigoAlunoServico {
	private ICodigoAlunoDAO codigoAlunoDAO;
	private IAlunoServico alunoServico;
	
	public CodigoAlunoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(List<CodigoAluno> listaCodigosAluno) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		for (CodigoAluno codigoAluno:listaCodigosAluno) {
			if(codigoAluno.getAdministracao().getAluno()!=null && codigoAluno.getAdministracao().getAluno().getId() ==null){
				alunoServico.persist(codigoAluno.getAdministracao().getAluno());
			}
			if(!codigoAlunoDAO.persist(codigoAluno)){
				retorno.setSucesso(false);
				break;
			}
		}
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(CodigoAluno codigoAluno) {
		Retorno retorno = validaRegrasAntesIncluir(codigoAluno);
		
		if(retorno.getSucesso()){
			List<CodigoAluno> listaNovosCodigosAluno = gerarCodigosAluno(codigoAluno);
			
			retorno = persist(listaNovosCodigosAluno);
			if(retorno.getSucesso()){
				retorno.addMensagem(new Mensagem("Alunos criados com sucesso.", Mensagem.SUCESSO));
			}else{
				retorno.addMensagem(new Mensagem("Erro ao criar alunos automaticamente.", Mensagem.ERRO));
			}
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(CodigoAluno codigoAluno) {
		Retorno retorno = validaRegrasAntesAlterar(codigoAluno);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(codigoAlunoDAO.save(codigoAluno)){
				mensagem = new Mensagem(codigoAluno.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(codigoAluno.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(codigoAluno.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(CodigoAluno codigoAluno) {
		Retorno retorno = validaRegrasAntesRemover(codigoAluno);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(codigoAlunoDAO.remove(codigoAluno)){
				mensagem = new Mensagem(codigoAluno.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(codigoAluno.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(CodigoAluno.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return codigoAlunoDAO.consultarPorIdFetch(abstractBean.getId());
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<CodigoAluno> search(Search search) {
		searchComum(search);
		return codigoAlunoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public CodigoAluno searchUnique(Search search) {
		searchComum(search);
		return codigoAlunoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(CodigoAluno codigoAluno) {
//		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(codigoALuno);
		
		//Ignora os campos obrigatorios no form
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),codigoAluno, PermissaoAcesso.OPERACAO_INCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_INCLUIR,Mensagem.ERRO));
		}
		
		if(codigoAluno.getCursoAux()==null || codigoAluno.getQuantidadeAlunosAux()<=0){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem("Quantidade de alunos deve ser maior que zero e curso é obrigatório", Mensagem.ERRO));
		}			
			
		retorno.addRetorno(validaRegrasComuns(codigoAluno));
		return retorno;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(CodigoAluno codigoAluno) {
		Retorno retorno = Util.verificarIdNulo(codigoAluno);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),codigoAluno, PermissaoAcesso.OPERACAO_ALTERAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_ALTERAR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(codigoAluno);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(codigoAluno));				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(CodigoAluno codigoAluno) {
		Retorno retorno = Util.verificarIdNulo(codigoAluno);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),codigoAluno, PermissaoAcesso.OPERACAO_EXCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_EXCLUIR,Mensagem.ERRO));
		}
		
		return retorno;
	}
	
	public void setCodigoAlunoDAO(ICodigoAlunoDAO codigoAlunoDAO) {
		this.codigoAlunoDAO = codigoAlunoDAO;
	}
	
	public void setAlunoServico(IAlunoServico alunoServico) {
		this.alunoServico = alunoServico;
	}

	@Override
	public CodigoAluno verificarCodigoAtivo(String codigo,CodigoAluno codigoAluno) {
		return codigoAlunoDAO.verificarCodigoAtivo(codigo,codigoAluno);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<CodigoAluno> gerarCodigosAluno(CodigoAluno codigoAluno) {
		List<CodigoAluno> listaCodigoAluno = codigoAlunoDAO.gerarCodigosAluno(codigoAluno);
		return listaCodigoAluno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		searchComum(search);
		return codigoAlunoDAO.count(search);
	}

	@Override
	public Retorno validaRegrasComuns(CodigoAluno codigoAluno) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		return retorno;
	}
	
	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return codigoAlunoDAO.searchFetchAlunoLogado(alunoLogado);
	}
	
	@Override
	public void searchComum(Search search){
		Filter filterOr = Filter.or();
		if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && !Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
		
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
					
			search.addFilter(filterOr);
		}
	}
	
}
