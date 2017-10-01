package com.mycompany.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.mycompany.util.Util;

public class FiltroDinamicoAtributo extends AbstractBean<FiltroDinamicoAtributo> implements Serializable{
    private static final long serialVersionUID = 1L;
    public static Integer LIKE = 1;
    public static Integer EQUALS = 2;
    public static Integer MAIOR_IQUAL = 3;
    public static Integer MENOR_IQUAL = 4;
    
    private Integer operador;
    
    //Usado caso seja um atributo auxilar e tenha um fetch especifico
    private String fetchAuxiliar;
    private String nomeAtributoReferenciaAuxiliar;
    
    //Ex: Saida(1° Nivel), Saida Produto(2° Nivel), Produto(3° Nivel)
    //Não funciona para o 4° nivel Ex: Ncm(4° nivel)
    //Saber qual o nivel de profundidade atual
    private Integer nivel;
    
    //Nome do atributo na classe
    private String nomeCampo;
    
    //Nome dado pela anotação para o atributo
    private String nomeCampoPersonalidado;
    private Object valorCampo;
    
    // String, Double, Long, AbstractBean.. etc
    private Class<?> typeCampo;
    
    //Necessario apenas para a classe extender do abstractBean
    private Long id;
    private UUID uuid = UUID.randomUUID();
    
    // Classe relacionada: Ex: Cliente com atributo de Situação cliente
    private FiltroDinamicoAtributo atributoEstrangeiro;
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
    
    
    public void setFetchAuxiliar(String fetchAuxiliar) {
        this.fetchAuxiliar = fetchAuxiliar;
    }
    public String getFetchAuxiliar() {
        return fetchAuxiliar;
    }
    public void setAtributoEstrangeiro(FiltroDinamicoAtributo atributoEstrangeiro) {
        this.atributoEstrangeiro = atributoEstrangeiro;
    }
    public FiltroDinamicoAtributo getAtributoEstrangeiro() {
        return atributoEstrangeiro;
    }
    public static List<Integer> getOperadores(){
        return Arrays.asList(LIKE,EQUALS,MAIOR_IQUAL,MENOR_IQUAL);
    }
    public void setOperador(Integer operador) {
        this.operador = operador;
    }
    public Integer getOperador() {
        return operador;
    }
    public String getOperadorNome(){
        if(operador!=null){
            if (operador.equals(FiltroDinamicoAtributo.LIKE)) {
                return "Que Contenha";
            } else if (operador.equals(FiltroDinamicoAtributo.EQUALS)) {
                return "Seja Igual";
            }else if (operador.equals(FiltroDinamicoAtributo.MAIOR_IQUAL)) {
                return "Maior Igual";
            }else if (operador.equals(FiltroDinamicoAtributo.MENOR_IQUAL)) {
                return "Menor igual";
            }
        }
        return null;
    }
    public FiltroDinamicoAtributo(String nomeCampo) {
        setNomeCampo(nomeCampo);
        setValorCampo(valorCampo);
    }
    public void setTypeCampo(Class<?> typeCampo) {
        this.typeCampo = typeCampo;
    }
    public Class<?> getTypeCampo() {
        return typeCampo;
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
    public String getNomeCampoSearch(){
        String nomeCampo = "";
        if(getAtributoEstrangeiro() !=null ){ // 2° nivel
            nomeCampo = getNomeCampo();
            if(getAtributoEstrangeiro().getFetchAuxiliar()!=null){
                nomeCampo+="."+getAtributoEstrangeiro().getFetchAuxiliar();
            }
            nomeCampo +="."+getAtributoEstrangeiro().getNomeCampo();
            if(getAtributoEstrangeiro().getAtributoEstrangeiro()!=null){ //3° nivel
                if(getAtributoEstrangeiro().getAtributoEstrangeiro().getFetchAuxiliar()!=null){
                    nomeCampo+="."+getAtributoEstrangeiro().getAtributoEstrangeiro().getFetchAuxiliar()+"."+getAtributoEstrangeiro().getAtributoEstrangeiro().getNomeAtributoReferenciaAuxiliar(); // altera o nome do atributo que deve filtrar
                }else{
                    nomeCampo +="."+getAtributoEstrangeiro().getAtributoEstrangeiro().getNomeCampo();
                }
            }
        }else{
            nomeCampo = getNomeCampo();
        }
        return nomeCampo;
    }
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
    public Integer getNivel() {
        return nivel;
    }
    @Override
    public Serializable getIdentifier() {
        return uuid;
    }
    @Override
    public Class<FiltroDinamicoAtributo> getJavaType() {
        return FiltroDinamicoAtributo.class;
    }
    public void setNomeAtributoReferenciaAuxiliar(
            String nomeAtributoReferenciaAuxiliar) {
        this.nomeAtributoReferenciaAuxiliar = nomeAtributoReferenciaAuxiliar;
    }
    public String getNomeAtributoReferenciaAuxiliar() {
        return nomeAtributoReferenciaAuxiliar;
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
	public String getNomeClass() {
		return "Filtro Dinamico Atributo";
	}
    
	@Override
	public void setAdministracao(Administracao administracao) {
	}
	@Override
	public Administracao getAdministracao() {
		return null;
	}
}