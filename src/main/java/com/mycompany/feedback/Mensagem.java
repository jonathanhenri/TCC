package com.mycompany.feedback;

import com.mycompany.util.Util;

public class Mensagem {
	public static String INFORMACAO = "info";
    public static String PERGUNTA = "info";
    public static String ALERTA = "danger";
    public static String ERRO = "error";
    public static String SUCESSO = "success";
    
    public static String MOTIVO_NULO = "não pode ser vazio.";
    public static String MOTIVO_REPETIDO = "não pode se repetir.";
    public static String MOTIVO_CADASTRADO= "cadastrado com sucesso.";
    public static String MOTIVO_ALTERADO = "alterado com sucesso.";
    public static String MOTIVO_EXCLUIDO = "excluido com sucesso.";
    
    public static String MOTIVO_CADASTRO_ERRO="Erro ao cadastrar ";
    public static String MOTIVO_ALTERADO_ERRO ="Erro ao alterar ";
    public static String MOTIVO_EXCLUIDO_ERRO = "Erro ao excluir ";
    
    public static String ID="Identificador";
	    
	public String campo;
	public String tipo;
	public String motivo;
	
	public Mensagem(){};
	
	public Mensagem(String motivo,String tipo){
		setTipo(tipo);
		setMotivo(motivo);
	}
	
	public Mensagem(String campo,String motivo,String tipo){
		setTipo(tipo);
		setCampo(campo);
		setMotivo(motivo);
	}
	
	public String toString(){
		if(getCampo()!=null)
			return getCampo()+" "+getMotivo();
		else
			return Util.firstToUpperCase(getMotivo());
	
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getMotivo() {
		return motivo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}
	
	public String getCampo() {
		return campo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
    
}
