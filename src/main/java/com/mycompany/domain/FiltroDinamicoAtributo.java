package com.mycompany.domain;

import java.io.Serializable;
import java.util.UUID;

public class FiltroDinamicoAtributo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nomeCampo;
	private String nomeCampoPersonalidado;
	private Object valorCampo;
	private UUID uuid = UUID.randomUUID();
	
	public FiltroDinamicoAtributo() {
	}
	
	public FiltroDinamicoAtributo(String nomeCampo, Object valorCampo) {
		setNomeCampo(nomeCampo);
		setValorCampo(valorCampo);
	}
	
	public FiltroDinamicoAtributo(String nomeCampo, String nomeCampoPersonalido,Object valorCampo) {
		setNomeCampo(nomeCampo);
		setValorCampo(valorCampo);
		setNomeCampoPersonalidado(nomeCampoPersonalido);
	}
	
	public FiltroDinamicoAtributo(String nomeCampo) {
		setNomeCampo(nomeCampo);
		setValorCampo(valorCampo);
	}
	
	public String getNomeCampo() {
		return nomeCampo;
	}
	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}
	public Object getValorCampo() {
		return valorCampo;
	}
	public void setValorCampo(Object valorCampo) {
		this.valorCampo = valorCampo;
	}

	public UUID getUuid() {
		return uuid;
	}
	
	public String getNomeCampoPersonalidado() {
		return nomeCampoPersonalidado;
	}

	public void setNomeCampoPersonalidado(String nomeCampoPersonalidado) {
		this.nomeCampoPersonalidado = nomeCampoPersonalidado;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
