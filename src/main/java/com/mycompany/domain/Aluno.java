	package com.mycompany.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.userdetails.UserDetails;

import com.mycompany.anotacao.ListarPageAnotacao;
import com.mycompany.feedback.Mensagem;
import com.mycompany.security.AtribuicaoAdmin;

@Entity
@Table(name = "ALUNO")
public class Aluno extends AbstractBean<Aluno> implements UserDetails, Sid{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@ListarPageAnotacao(nomeColuna = "Numero",filtro = true)
	@Id
	@Column(name = "ID_ALUNO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ListarPageAnotacao(identificadorEstrangeiro = true,filtro = true)
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao(filtro = true)
	@Column(name = "CPF", nullable = false, length = 11)
	private String cpf;
	
	@Column(name = "SENHA", nullable = false, length = 50)
	private String senha;	
	
	@ListarPageAnotacao
	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;	
	
//	@ManyToOne(optional = true,fetch=FetchType.LAZY)
//	@JoinColumn(name="ID_ARQUIVO")
//	private Arquivo imagem;
	
	@Column(name = "PERIODO", nullable = true)
	private Integer periodo;
	
	@Transient
	private List<Mensagem> listaMensagensSistema;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<Aluno> getJavaType() {
		return Aluno.class;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public void addMensagemSistema(Mensagem mensagem){
		if(listaMensagensSistema ==  null){
			listaMensagensSistema = new ArrayList<Mensagem>();
		}
		
		listaMensagensSistema.add(mensagem);
	}

	public List<Mensagem> getListaMensagensSistema() {
		return listaMensagensSistema;
	}

	public void setListaMensagensSistema(List<Mensagem> listaMensagensSistema) {
		this.listaMensagensSistema = listaMensagensSistema;
	}

	@Override
	public String getUsername() {
		return nome;
	}
	
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}
	
	
	
	@Override
	public GrantedAuthority[] getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new AtribuicaoAdmin());
		return authorities.toArray(new GrantedAuthority[authorities.size()]);
	}

}
