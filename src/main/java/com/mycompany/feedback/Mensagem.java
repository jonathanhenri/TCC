package com.mycompany.feedback;

import com.mycompany.util.Util;

public class Mensagem {
	public static int INFORMACAO = 0;
    public static int PERGUNTA = 1;
    public static int ALERTA = 2;
    public static int ERRO = 3;
    public static int SUCESSO = 4;
    
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
	public Integer tipo;
	public String motivo;
	
	public Mensagem(){};
	
	public Mensagem(String motivo,Integer tipo){
		setTipo(tipo);
		setMotivo(motivo);
	}
	
	public Mensagem(String campo,String motivo,Integer tipo){
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
	
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
  
    
}
