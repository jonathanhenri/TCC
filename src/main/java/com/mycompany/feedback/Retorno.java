package com.mycompany.feedback;

import java.util.ArrayList;
import java.util.List;

public class Retorno {

	private Boolean sucesso;
	private List<Mensagem> listaMensagem;
	
	public Retorno(){}
	
	public Retorno(List<Mensagem> listaMensagem){
		setListaMensagem(listaMensagem);
	}

	public Retorno(List<Mensagem> listaMensagem,Boolean sucesso){
		setListaMensagem(listaMensagem);
		setSucesso(sucesso);
	}
	
	
	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}

	public List<Mensagem> getListaMensagem() {
		return listaMensagem;
	}

	public void setListaMensagem(List<Mensagem> listaMensagem) {
		this.listaMensagem = listaMensagem;
	}
	
	public void addMensagens(List<Mensagem> listaMensagensAux){
		if(listaMensagem == null){
			listaMensagem = new ArrayList<Mensagem>();
		}
		if(listaMensagensAux!=null && listaMensagensAux.size()>0){
			for(Mensagem mensagem:listaMensagensAux){
				addMensagem(mensagem);
			}
		}
	}
	public void addMensagem(Mensagem mensagem){
		if(listaMensagem == null){
			listaMensagem = new ArrayList<Mensagem>();
		}
		listaMensagem.add(mensagem);
	}
}

