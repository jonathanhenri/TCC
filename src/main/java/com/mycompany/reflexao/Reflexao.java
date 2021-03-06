package com.mycompany.reflexao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import com.mycompany.anotacao.ListarPageAnotacao;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.util.Util;

public class Reflexao {
	//OBS: Como a reflexão vai se comportar com relacionamento com outra entidade?
	
	public static Retorno validarTodosCamposObrigatorios(AbstractBean<?> object){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		Class<?> cls = object.getClass();	
		Field[] fld = cls.getDeclaredFields();
		for(int i = 0; i < fld.length; i++){
			String name = null;
			Column cmpColum = fld[i].getAnnotation(Column.class);
			JoinColumn cmpJoinColumn = fld[i].getAnnotation(JoinColumn.class);
			
			if(cmpColum!=null && !cmpColum.nullable()){
				name = cmpColum.name();
			}else if(cmpJoinColumn!=null && !cmpJoinColumn.nullable()){
				name = cmpJoinColumn.name();
			}
			
			if(name!=null){
				Object objectCampo = getFieldValueCamposComuns(object, fld[i].getName());
				if(objectCampo == null || objectCampo!=null && objectCampo.toString().isEmpty()){ 
					Mensagem mensagem = new Mensagem(Util.firstToUpperCase(fld[i].getName()), Mensagem.MOTIVO_NULO, Mensagem.ERRO);
					mensagem.setNomeEntidade(object.getNomeClass());
					retorno.setSucesso(false);
					retorno.addMensagem(mensagem);
				}
			}
		
		}
		
		return retorno;
	}
	
	
	public static String verificarClassEstrangeira(String nomeCampo){
		String atributoIdentificadoEstrangeiro = "";
		
		Class<?> classeConverter;
		Object classeAuxBean;
		try {
			classeConverter = Class.forName(nomeCampo);
			classeAuxBean  = classeConverter.newInstance();
			
			if(classeAuxBean instanceof AbstractBean<?>){ // VERIFICA SE O CAMPO É UMA OUTRA ENTIDADE
				atributoIdentificadoEstrangeiro = getIdentificadorEstrangeiro((AbstractBean<?>) classeAuxBean); // PEGA O IDENTIFICADOR ESTRANGEIRO: EX: "NOME"
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		}
		
		return atributoIdentificadoEstrangeiro;
	}
	
	public static List<FiltroDinamicoAtributo>  nomesAtributosFiltros(AbstractBean<?> abstractBean){
		List<FiltroDinamicoAtributo> nomesAtributos = new ArrayList<FiltroDinamicoAtributo>();
		
		Class<?> cls = abstractBean.getClass();	
		Field[] fld = cls.getDeclaredFields();
		
		for(int i = 0; i < fld.length; i++){
			ListarPageAnotacao cmp = fld[i].getAnnotation(ListarPageAnotacao.class);				
			if(cmp!=null && cmp.filtro()){
				FiltroDinamicoAtributo filtroDinamicoAtributo = new FiltroDinamicoAtributo();
				
				if(cmp.nomeColuna()!=null && !cmp.nomeColuna().isEmpty()){
					filtroDinamicoAtributo.setNomeCampoPersonalidado(cmp.nomeColuna());
				}
				
				filtroDinamicoAtributo.setNomeCampo(fld[i].getName());
				
				String atributoIdentificadoEstrangeiro = verificarClassEstrangeira(fld[i].getType().getName());
				
				 if(!atributoIdentificadoEstrangeiro.isEmpty()){
					filtroDinamicoAtributo.setNomeCampo(filtroDinamicoAtributo.getNomeCampo()+"."+atributoIdentificadoEstrangeiro);// JUNTA O NOME DO ATRIBUTO COM O IDENTIFICADOR UNICO: EX: CURSO.NOME
					filtroDinamicoAtributo.setNomeCampoPersonalidado(filtroDinamicoAtributo.getNomeCampoPersonalidado()+"."+atributoIdentificadoEstrangeiro);
				}
				
				nomesAtributos.add(filtroDinamicoAtributo);
			}
		}
		return nomesAtributos;
	}
	
	
	public static Map<String, String> colunaListarPage(AbstractBean<?> abstractBean){
		Map<String, String> hashMapColunas = new LinkedHashMap<String, String>();
		Class<?> cls = abstractBean.getClass();	
		Field[] fld = cls.getDeclaredFields();
		
		for(int i = 0; i < fld.length; i++){
			ListarPageAnotacao cmp = fld[i].getAnnotation(ListarPageAnotacao.class);				
			if(cmp!=null){
				String nomeCampo = fld[i].getName();
				String atributoIdentificadoEstrangeiro = verificarClassEstrangeira(fld[i].getType().getName());
				
				if(!atributoIdentificadoEstrangeiro.isEmpty()){
					nomeCampo+="."+atributoIdentificadoEstrangeiro; // JUNTA O NOME DO ATRIBUTO COM O IDENTIFICADOR UNICO: EX: CURSO.NOME
				}
				
				if(cmp.nomeColuna()!=null && !cmp.nomeColuna().isEmpty()){
					hashMapColunas.put(Util.firstToUpperCase(cmp.nomeColuna()), nomeCampo); // CASO SEJA IDENTIFICADO UM NOME DA COLUNA DIFERENTE DO NOME DO ATRIBUTO
				}else{
					hashMapColunas.put(Util.firstToUpperCase(nomeCampo), nomeCampo); // NOME DA COLUNA SEJA O MESMO DO ATRIBUTO
				}
			}				
		}
		return hashMapColunas;
	}
	
	/*
	 * Procurar no bean passado de paremetro quando o nome do atributo anotado com a anotação "@ListarPageAnotacao(identificadorEstrangeiro = true)"
	 */
	private static String getIdentificadorEstrangeiro(AbstractBean<?> abstractBean){
		String identificadoEstrangeiro = "";
		Class<?> cls = abstractBean.getClass();	
		Field[] fld = cls.getDeclaredFields();
		
		for(int i = 0; i < fld.length; i++){
			ListarPageAnotacao cmp = fld[i].getAnnotation(ListarPageAnotacao.class);				
			if(cmp!=null && cmp.identificadorEstrangeiro()){
				return fld[i].getName(); 
			}				
		}
		return identificadoEstrangeiro;
	}
	
	public static List<String> getListaAtributosEstrangeiros(AbstractBean<?> abstractBean){
		List<String> listaAtributosEstrangeiros = new ArrayList<String>();
		try {
			Class<?> cls = abstractBean.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				JoinColumn joinColum = fld[i].getAnnotation(JoinColumn.class);
				if( joinColum!=null){
					listaAtributosEstrangeiros.add(fld[i].getName());
				}
			}
			
		}catch (Throwable e) {
			e.printStackTrace();
		}
		
		
		return listaAtributosEstrangeiros;
	}
	
	
	/*
	 *	Usado para procurar todos os valores preenchidos dentro de um abstract bean e montar um hash map com <Nome do atributo>, <Valor do atributo>
	 */
	public static HashMap<String,Object> getFieldValuePesquisaListarPage(AbstractBean<?> abstractBean){
		HashMap<String, Object> hashMapsFieldValue = new HashMap<String, Object>();
		try {
			Class<?> cls = abstractBean.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				Column colum = fld[i].getAnnotation(Column.class);
				JoinColumn joinColum = fld[i].getAnnotation(JoinColumn.class);
				if(colum!=null){ // PARA ATRIBUTOS COMUNS
					Object valorCampo = getValueBean(abstractBean, fld[i].getName(), cls);
					if(valorCampo!=null){
						hashMapsFieldValue.put(fld[i].getName(), valorCampo);
					}
				}else if( joinColum!=null){ // PARA RELACIONAMENTOS
					Object classEstrangeira = getValueBean(abstractBean, fld[i].getName(), cls);
					if(classEstrangeira!=null){
						Map<String, Object> hashMapsValuesEstrangeiros = getFieldValuePesquisaListarPage((AbstractBean<?>) classEstrangeira);
						
						for(String nomeCampo: hashMapsValuesEstrangeiros.keySet()){
							Object valueCampoEstrangeiro = hashMapsValuesEstrangeiros.get(nomeCampo);
							hashMapsFieldValue.put(fld[i].getName()+"."+nomeCampo,valueCampoEstrangeiro);
						}
					}
					
				}
			}
		}catch (Throwable e) {
			e.printStackTrace();
		}
		
		return hashMapsFieldValue;
	}
	
	private static Object getValueBean(AbstractBean<?> abstractBean,String fieldName,Class<?> cls){
		try {
			String auxName = "get"+Util.firstToUpperCase(fieldName);
			Method metodo= cls.getDeclaredMethod(auxName, null);
			Object valorCampo = metodo.invoke(abstractBean, null);
			return valorCampo;
		}catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static Object getFieldValueCamposComuns(AbstractBean<?> object, String fieldName){
		Object pkValue = null;
		try {
			Class<?> cls = object.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				Column cmp = fld[i].getAnnotation(Column.class);
				JoinColumn cmpJoinColumn = fld[i].getAnnotation(JoinColumn.class);
				
				if((cmp!=null || cmpJoinColumn!=null) && fld[i].getName().equalsIgnoreCase(fieldName)){
					pkValue = getValueBean(object, fld[i].getName(), cls);
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
			pkValue = null;
		}
		return pkValue;
	}
}
