package com.mycompany.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.userdetails.UserDetails;

import com.mycompany.anotacao.ListarPageAnotacao;
import com.mycompany.security.AtribuicaoAdmin;

@Entity
@Table(name = "ALUNO")
public class Aluno extends AbstractBean<Aluno> implements UserDetails, Sid{
	private static final long serialVersionUID = 1L;
	
	@ListarPageAnotacao(nomeColuna = "Numero")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ListarPageAnotacao(identificadorEstrangeiro = true)
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao
	@Column(name = "CPF", nullable = false, length = 20)
	private String cpf;
	
	@Column(name = "SENHA", nullable = false, length = 50)
	private String senha;	
	
	@ListarPageAnotacao
	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;	
	
	@Column(name = "CONTADOR_ACESSO", nullable = false)
	private Double contadorAcesso;	
	
	@ListarPageAnotacao(nomeColuna = "Curso")
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ARQUIVO")
	private Arquivo imagem;
	
	@Column(name = "PERIODO", nullable = true)
	private Integer periodo;
	
	public void setContadorAcesso(Double contadorAcesso) {
		this.contadorAcesso = contadorAcesso;
	}
	
	public Double getContadorAcesso() {
		return contadorAcesso;
	}
	
	public void addContadorAcesso(){
		if(getContadorAcesso() == null){
			setContadorAcesso(1.0);
		}else{
			setContadorAcesso(getContadorAcesso() + 1);
		}
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setImagem(Arquivo imagem) {
		this.imagem = imagem;
	}
	
	public Arquivo getImagem() {
		return imagem;
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
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public Curso getCurso() {
		return curso;
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

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return nome;
	}

	@Override
	public GrantedAuthority[] getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new AtribuicaoAdmin());
		return authorities.toArray(new GrantedAuthority[authorities.size()]);
	}

}
