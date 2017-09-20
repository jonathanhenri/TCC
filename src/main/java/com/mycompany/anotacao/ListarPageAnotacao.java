package com.mycompany.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Anotação usada para mostrar quais atributos irão aparecer no listar page
 *  A ordem dos atributos irar ser a ordem das colunas
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListarPageAnotacao {
	String nomeColuna() default "";
	boolean identificadorEstrangeiro() default false;
	boolean filtro() default false;
	
    //Logica criada para passar do 3° nivel em alguns casos, ai ele coloca qual o fetch que deve ser feito e o atributo referenciado
    String fetch() default "";
    String nomeMetodoReferencia() default "";
}
