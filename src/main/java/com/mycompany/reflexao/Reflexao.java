package com.mycompany.reflexao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.mycompany.anotacao.ListarPageAnotacao;
import com.mycompany.domain.AbstractBean;
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
			Column cmp = fld[i].getAnnotation(Column.class);
			if(cmp!=null && !cmp.nullable()){
				Object objectCampo = getFieldValueCamposComuns(object, cmp.name());
				if(objectCampo == null){ 
					Mensagem mensagem = new Mensagem(cmp.name(), Mensagem.MOTIVO_NULO, Mensagem.ERRO);
					retorno.setSucesso(false);
					retorno.addMensagem(mensagem);
				}
			}
		}
		
		return retorno;
	}
	
//	public static Retorno naoDeixarAlterarExcluirIdNulo(AbstractBean<?> object){
//		Retorno retorno = new Retorno();
//		retorno.setSucesso(true);
//		
//		Class<?> cls = object.getClass();	
//		Field[] fld = cls.getDeclaredFields();
//		for(int i = 0; i < fld.length; i++){
//			Id cmp = fld[i].getAnnotation(Id.class);
//			if(cmp!=null){
//				Object objectCampo = getFieldValueId(object);
//				if(objectCampo==null){
//					Mensagem mensagem = new Mensagem(Mensagem.ID, Mensagem.MOTIVO_NULO, Mensagem.ERRO);
//					retorno.setSucesso(false);
//					retorno.addMensagem(mensagem);
//				}
//			}
//		}
//		
//		return retorno;
//	}
	
	private static Object getFieldValueId(AbstractBean<?> object){
		Object pkValue = null;
		try {
			Class<?> cls = object.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				Id cmp = fld[i].getAnnotation(Id.class);
				
				if(cmp!=null){
					String auxName = "get"+Util.firstToUpperCase(fld[i].getName());
					
					Method metodo= cls.getDeclaredMethod(auxName, null);
					pkValue = metodo.invoke(object, null);
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
			pkValue = null;
		}
		return pkValue;
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
			e.printStackTrace();
		}
		
		return atributoIdentificadoEstrangeiro;
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
	
//	/*
//	 * Este metodo tem o objetivo de passar um bean de paremetro é montar um map com <Nome atributo>, <Valor do Atributo>
//	 * Ele irar pesquisar no bean todos os valores do atributo que não estiver vazio e ir preenchendo o map com seu respectivo nome de atributo
//	 */
//	public static Map<String, Object> getTodosFieldValues(AbstractBean<?> abstractBean){
//		Map<String, Object> hashMapColunas = new LinkedHashMap<String, Object>();
//		Class<?> cls = abstractBean.getClass();	
//		Field[] fld = cls.getDeclaredFields();
//		
//		for(int i = 0; i < fld.length; i++){
//			ListarPageAnotacao cmp = fld[i].getAnnotation(ListarPageAnotacao.class);				
//			if(cmp!=null){
//				String nomeCampo = fld[i].getName();
//				String atributoIdentificadoEstrangeiro = verificarClassEstrangeira(fld[i].getType().getName());
//				
//				if(!atributoIdentificadoEstrangeiro.isEmpty()){
//					nomeCampo+="."+atributoIdentificadoEstrangeiro; // JUNTA O NOME DO ATRIBUTO COM O IDENTIFICADOR UNICO: EX: CURSO.NOME
//				}
//				Object valueCampo = getValueBean(abstractBean, nomeCampo, cls);
//				if(valueCampo!=null){
//					if(cmp.nomeColuna()!=null && !cmp.nomeColuna().isEmpty()){
//						hashMapColunas.put(cmp.nomeColuna(),valueCampo ); // CASO SEJA IDENTIFICADO UM NOME DA COLUNA DIFERENTE DO NOME DO ATRIBUTO
//					}else{
//						hashMapColunas.put(nomeCampo, getValueBean(abstractBean, nomeCampo, cls)); // NOME DA COLUNA SEJA O MESMO DO ATRIBUTO
//					}
//				}
//			}				
//		}
//		return hashMapColunas;
//	}
	
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
				
				if(cmp!=null && fld[i].getName().equalsIgnoreCase(fieldName)){
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
