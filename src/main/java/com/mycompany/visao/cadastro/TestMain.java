package com.mycompany.visao.cadastro;

import com.mycompany.util.Util;

public class TestMain {

	   public static void main(String[] args) {
		   System.out.println(Util.codigoGeradorAcesso());
	      System.out.printf("CPF Valido:%s \n", Util.isValidCPF("89475244506")); 
	   }

}
