package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IPermissaoAcessoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IPermissaoAcessoServico;
import com.mycompany.util.Util;

public class PermissaoAcessoServico implements IPermissaoAcessoServico {
	private IPermissaoAcessoDAO permissaoAcessoDAO;
	
	public PermissaoAcessoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(PermissaoAcesso permissaoAcesso) {
		Retorno retorno = validaRegrasAntesIncluir(permissaoAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(permissaoAcessoDAO.persist(permissaoAcesso)){
				mensagem  = new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(PermissaoAcesso.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return permissaoAcessoDAO.consultarPorIdFetch(abstractBean.getId());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return permissaoAcessoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(PermissaoAcesso permissaoAcesso) {
		Retorno retorno = validaRegrasAntesAlterar(permissaoAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(permissaoAcessoDAO.save(permissaoAcesso)){
				mensagem = new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(PermissaoAcesso permissaoAcesso) {
		Retorno retorno = validaRegrasAntesRemover(permissaoAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(permissaoAcessoDAO.remove(permissaoAcesso)){
				mensagem = new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(permissaoAcesso.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<PermissaoAcesso> search(Search search) {
		return permissaoAcessoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public PermissaoAcesso searchUnique(Search search) {
		return permissaoAcessoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasComuns(PermissaoAcesso permissaoAcesso){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		return retorno;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(PermissaoAcesso permissaoAcesso) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(permissaoAcesso);
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(permissaoAcesso));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(PermissaoAcesso permissaoAcesso) {
		Retorno retorno = Util.verificarIdNulo(permissaoAcesso);
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(permissaoAcesso);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(permissaoAcesso));				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(PermissaoAcesso permissaoAcesso) {
		Retorno retorno = Util.verificarIdNulo(permissaoAcesso);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	
	public void setPermissaoAcessoDAO(IPermissaoAcessoDAO permissaoAcessoDAO) {
		this.permissaoAcessoDAO = permissaoAcessoDAO;
	}

	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return permissaoAcessoDAO.searchFetchAlunoLogado(alunoLogado);
	}

	@Override
	public List<PermissaoAcesso> getPermissoesAcesso(){
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return permissaoAcessoDAO.getPermissoesAcesso();
		}
		
		return null;
	}
	
	@Override
	public void searchComum(Search search){
	}
}
