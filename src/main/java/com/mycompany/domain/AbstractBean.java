package com.mycompany.domain;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.security.acls.objectidentity.ObjectIdentity;


public abstract class AbstractBean<T> implements ObjectIdentity{
	
	private static final long serialVersionUID = 1L;

	public abstract Long getId();
	
	public abstract void setId(Long id);
	
	
	 public T clonar(boolean clonarComId){
		try{
		
			@SuppressWarnings("all")
			Class<T> classeDestino = getJavaType();
			
			T objetoDestino = classeDestino.newInstance();
			
			PropertyDescriptor[] atributos = PropertyUtils.getPropertyDescriptors(this);
			
			for(PropertyDescriptor atributo : atributos){
				
				if("class".equals(atributo.getName()) || "identifier".equals(atributo.getName()) || ("id".equals(atributo.getName()) && !clonarComId) ){
					continue;
				}
				
				// Copia o valor dos atributos não persisitidos para o objeto de destino
				Object value = PropertyUtils.getSimpleProperty(this, atributo.getName());
				
				if(value != null){
					BeanUtils.setProperty(objetoDestino, atributo.getName(), value);
				}
			}
			return objetoDestino;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
			
	}
}
