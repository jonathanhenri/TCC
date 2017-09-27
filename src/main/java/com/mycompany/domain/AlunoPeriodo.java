package com.mycompany.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ALUNO_PERIODO")
public class AlunoPeriodo {
	@Column(name = "PERIODO",nullable=false)
	private Integer periodo;
	
	@ManyToOne(fetch=FetchType.LAZY,optional = true)
	@JoinColumn(name="ID_ALUNO",nullable = true)
	private Aluno aluno;
	
	@Id
	@Column(name = "ID_ALUNO_PERIODO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public AlunoPeriodo(){
		
	}
	
	public AlunoPeriodo(Integer periodo,Aluno aluno){
		setPeriodo(periodo);
		setAluno(aluno);
	}
	
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	
	
}
