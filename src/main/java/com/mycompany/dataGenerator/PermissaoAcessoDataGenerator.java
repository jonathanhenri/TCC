package com.mycompany.dataGenerator;


import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Aula;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Evento;
import com.mycompany.domain.Materia;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.TipoEvento;
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
		
		//Evento
		criarPermissao("Evento Incluir", PermissaoAcesso.PERMISSAO_EVENTO_INCLUIR, 5, Evento.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Evento Pesquisar", PermissaoAcesso.PERMISSAO_EVENTO_PESQUISAR, 6, Evento.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Evento Alterar", PermissaoAcesso.PERMISSAO_EVENTO_ALTERAR, 7, Evento.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Evento Excluir", PermissaoAcesso.PERMISSAO_EVENTO_EXCLUIR, 8, Evento.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Tipo de evento
		criarPermissao("Tipo de Evento Incluir", PermissaoAcesso.PERMISSAO_TIPO_EVENTO_INCLUIR, 9, TipoEvento.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Tipo de Evento Pesquisar", PermissaoAcesso.PERMISSAO_TIPO_EVENTO_PESQUISAR, 10, TipoEvento.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Tipo de Evento Alterar", PermissaoAcesso.PERMISSAO_TIPO_EVENTO_ALTERAR, 11, TipoEvento.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Tipo de Evento Excluir", PermissaoAcesso.PERMISSAO_TIPO_EVENTO_EXCLUIR, 12, TipoEvento.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Origem de evento
		criarPermissao("Origem de Evento Incluir", PermissaoAcesso.PERMISSAO_ORIGEM_EVENTO_INCLUIR, 13, OrigemEvento.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Origem de Evento Pesquisar", PermissaoAcesso.PERMISSAO_ORIGEM_EVENTO_PESQUISAR, 14, OrigemEvento.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Origem de Evento Alterar", PermissaoAcesso.PERMISSAO_ORIGEM_EVENTO_ALTERAR, 15, OrigemEvento.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Origem de Evento Excluir", PermissaoAcesso.PERMISSAO_ORIGEM_EVENTO_EXCLUIR, 16, OrigemEvento.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Curso
		criarPermissao("Curso Incluir", PermissaoAcesso.PERMISSAO_CURSO_INCLUIR, 17, Curso.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Curso Pesquisar", PermissaoAcesso.PERMISSAO_CURSO_PESQUISAR, 18, Curso.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Curso Alterar", PermissaoAcesso.PERMISSAO_CURSO_ALTERAR, 19, Curso.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Curso Excluir", PermissaoAcesso.PERMISSAO_CURSO_EXCLUIR, 20, Curso.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Materia
		criarPermissao("Materia Incluir", PermissaoAcesso.PERMISSAO_MATERIA_INCLUIR, 21, Materia.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Materia Pesquisar", PermissaoAcesso.PERMISSAO_MATERIA_PESQUISAR, 22, Materia.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Materia Alterar", PermissaoAcesso.PERMISSAO_MATERIA_ALTERAR, 23, Materia.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Materia Excluir", PermissaoAcesso.PERMISSAO_MATERIA_EXCLUIR, 24, Materia.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Aula
		criarPermissao("Aula Incluir", PermissaoAcesso.PERMISSAO_AULA_INCLUIR, 25, Aula.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Aula Pesquisar", PermissaoAcesso.PERMISSAO_AULA_PESQUISAR, 26, Aula.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Aula Alterar", PermissaoAcesso.PERMISSAO_AULA_ALTERAR, 27, Aula.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Aula Excluir", PermissaoAcesso.PERMISSAO_AULA_EXCLUIR, 28, Aula.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Agenda
		criarPermissao("Agenda Incluir", PermissaoAcesso.PERMISSAO_AGENDA_INCLUIR, 29, Agenda.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Agenda Pesquisar", PermissaoAcesso.PERMISSAO_AGENDA_PESQUISAR, 30, Agenda.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Agenda Alterar", PermissaoAcesso.PERMISSAO_AGENDA_ALTERAR, 31, Agenda.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Agenda Excluir", PermissaoAcesso.PERMISSAO_AGENDA_EXCLUIR, 32, Agenda.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Perfil Acesso
		criarPermissao("Perfil Acesso Incluir", PermissaoAcesso.PERMISSAO_PERFIL_ACESSO_INCLUIR, 33, PerfilAcesso.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Perfil Acesso Pesquisar", PermissaoAcesso.PERMISSAO_PERFIL_ACESSO_PESQUISAR, 34, PerfilAcesso.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Perfil Acesso Alterar", PermissaoAcesso.PERMISSAO_PERFIL_ACESSO_ALTERAR, 35, PerfilAcesso.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Perfil Acesso Excluir", PermissaoAcesso.PERMISSAO_PERFIL_ACESSO_EXCLUIR, 36, PerfilAcesso.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Acesso Provisorio
		criarPermissao("Acesso Provisorio Incluir", PermissaoAcesso.PERMISSAO_ACESSO_PROVISORIO_INCLUIR, 37, CodigoAluno.class, PermissaoAcesso.OPERACAO_INCLUIR);
		criarPermissao("Acesso Provisorio Pesquisar", PermissaoAcesso.PERMISSAO_ACESSO_PROVISORIO_PESQUISAR, 38, CodigoAluno.class, PermissaoAcesso.OPERACAO_PESQUISAR);
		criarPermissao("Acesso Provisorio Alterar", PermissaoAcesso.PERMISSAO_ACESSO_PROVISORIO_ALTERAR, 39, CodigoAluno.class, PermissaoAcesso.OPERACAO_ALTERAR);
		criarPermissao("Acesso Provisorio Excluir", PermissaoAcesso.PERMISSAO_ACESSO_PROVISORIO_EXCLUIR, 30, CodigoAluno.class, PermissaoAcesso.OPERACAO_EXCLUIR);
		
		//Relatorios
		criarPermissao("Acesso a Relatórios", PermissaoAcesso.PERMISSAO_RELATORIOS, 31, AbstractBean.class, PermissaoAcesso.OPERACAO_RELATORIO);
		
		//Configuração
		criarPermissao("Compartilhar", PermissaoAcesso.PERMISSAO_CONFIGURACAO_COMPARTILHAR, 32, AbstractBean.class, PermissaoAcesso.OPERACAO_CONFIGURACAO_COMPARTILHAR);
		criarPermissao("Sincronizar", PermissaoAcesso.PERMISSAO_CONFIGURACAO_SINCRONIZAR, 33, AbstractBean.class, PermissaoAcesso.OPERACAO_CONFIGURACAO_SINCRONIZAR);
		
	}
	

	private static void criarPermissao(String nome,Integer permissao,Integer Codigo,Class<?> casoDeUso,Integer operacao) {
		Administracao administracao = new Administracao();
		PermissaoAcesso acesso = new PermissaoAcesso(nome, permissao, Codigo, casoDeUso, operacao);
		acesso.setAdministracao(administracao);
		permissaoAcessoDAO.persist(acesso);
	}
}