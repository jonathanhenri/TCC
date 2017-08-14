package com.mycompany.dataGenerator;


import com.mycompany.domain.Administracao;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.persistence.interfaces.IPermissaoAcessoDAO;

public class PermissaoAcessoDataGenerator {

	private static IPermissaoAcessoDAO permissaoAcessoDAO;
	
	public static void generateData(IPermissaoAcessoDAO permissaoAcessoDAO2) throws Exception{
		permissaoAcessoDAO = permissaoAcessoDAO2;
		
		//Aluno
		criarPermissao("Aluno Incluir", PermissaoAcesso.PERMISSAO_ALUNO_INCLUIR, 1, Aluno.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Aluno Pesquisar", PermissaoAcesso.PERMISSAO_ALUNO_PESQUISAR, 2, Aluno.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Aluno Alterar", PermissaoAcesso.PERMISSAO_ALUNO_ALTERAR, 3, Aluno.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Aluno Excluir", PermissaoAcesso.PERMISSAO_ALUNO_EXCLUIR, 4, Aluno.class, PermissaoAcesso.OPERACAO_EXCLUIR);
	}

	private static void criarPermissao(String nome,Integer permissao,Integer Codigo,Class<?> casoDeUso,Integer operacao) {
		Administracao administracao = new Administracao();
		PermissaoAcesso acesso = new PermissaoAcesso(nome, permissao, Codigo, casoDeUso, operacao);
		acesso.setAdministracao(administracao);
		permissaoAcessoDAO.persist(acesso);
	}
}