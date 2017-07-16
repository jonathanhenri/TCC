package com.mycompany.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.springframework.security.context.SecurityContextHolder;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;

public class Util {
	
	public static final String caminhoImagem = "/img/seta-voltar.gif";
	
	public static final Integer SIM = 1;
	public static final Integer NAO = 0;
	
	/**
	 * Compara duas datas.<p>
	 * <blockquote><pre>
	 * Se as datas forem iguais, retorna 0;
	 * Se a data do primeiro argumento for menor que a data do segundo argumento, retorna -1;
	 * Se a data do primeiro argumento for maior que a data do segundo argumento, retorna 1.
	 * </pre></blockquote>
	 * @param  Date
	 * @param  Date
	 * @return  int
	 */
	public static int comparaDatas(Date data1, Date data2, boolean considerarHoras) {
		return comparaDatas(data1, data2, considerarHoras, true);
	}
	
	/**
	 * Compara duas datas.<p>
	 * <blockquote><pre>
	 * Se as datas forem iguais, retorna 0;
	 * Se a data do primeiro argumento for menor que a data do segundo argumento, retorna -1;
	 * Se a data do primeiro argumento for maior que a data do segundo argumento, retorna 1.
	 * </pre></blockquote>
	 * @param  Date
	 * @param  Date
	 * @return  int
	 */
	public static int comparaDatas(Date data1, Date data2, boolean considerarHoras, boolean considerarDias) {
		Calendar data1Temp = Calendar.getInstance();
		Calendar data2Temp = Calendar.getInstance();
		data1Temp.setTime(data1);
		data2Temp.setTime(data2);
		
		if(!considerarHoras){
			data1Temp.set(Calendar.HOUR_OF_DAY,0);
			data1Temp.set(Calendar.MINUTE,0);
			data1Temp.set(Calendar.SECOND,0);
			data1Temp.set(Calendar.MILLISECOND,0);
			
			data2Temp.set(Calendar.HOUR_OF_DAY,0);
			data2Temp.set(Calendar.MINUTE,0);
			data2Temp.set(Calendar.SECOND,0);
			data2Temp.set(Calendar.MILLISECOND,0);
		}
		
		if(!considerarDias){
			data1Temp.set(Calendar.DAY_OF_MONTH,1);
			data2Temp.set(Calendar.DAY_OF_MONTH,1);
		}

		return data1Temp.compareTo(data2Temp);
	}

	/**
	 * Retorna o aluno logado (SPRING)
	 */
	public static Aluno getAlunoLogado() {
		if(SecurityContextHolder.getContext()!=null && SecurityContextHolder.getContext().getAuthentication()!=null){
			return (Aluno)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		return null;
	}
	
	public static String getMensagemExclusao(AbstractBean<?> abstractBean){
		return "Esta ação não pode ser revertida, deseja excluir "+Util.firstToUpperCase(Util.separarToUpperCase(abstractBean.getClass().getSimpleName()))+" realmente?";
	}
	
	/**
	 * Codigo verificador pra cada aluno
	 * 
	 * @return
	 */
	public static String codigoGeradorAcesso(){
		return String.valueOf(UUID.randomUUID());
	}
	
   private static int calcularDigito(String str, int[] peso) {
	   
	   
      int soma = 0;
      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
         digito = Integer.parseInt(str.substring(indice,indice+1));
         soma += digito*peso[peso.length-str.length()+indice];
      }
      soma = 11 - soma % 11;
      return soma > 9 ? 0 : soma;
   }

   public static boolean isValidCPF(String cpf) {
	  int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	  
	  cpf = cpf.replace(".","").replace("-", "");
	  
      if ((cpf==null) || (cpf.length()!=11)) return false;
     
      Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
      Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
      return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
   }
	
	public static Retorno verificarIdNulo(AbstractBean<?> abstractBean){
		Retorno retorno =  new Retorno();
		retorno.setSucesso(true);
		
		if(getAlunoLogado()!=null && getAlunoLogado().getId()!=null){
			if(abstractBean==null || abstractBean!=null && abstractBean.getId()==null){
				Mensagem mensagem = new Mensagem(Mensagem.ID, Mensagem.MOTIVO_NULO, Mensagem.ERRO);
				retorno.setSucesso(false);
				retorno.addMensagem(mensagem);
			}
		}
		
		return retorno;
	}
	
	public static String separarToUpperCase(String str){
		String novaString = "";
		for(int i = 0;i<str.length();i++){
			if(Character.isUpperCase(str.charAt(i))){
				novaString = novaString +" "+str.charAt(i);
			}else{
				novaString+=str.charAt(i);
			}
		}
		
		return novaString;
	}
	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase().concat(str.substring(1));
	}
	
	public static ChoiceRenderer<Boolean> getSimNao(final Component component){
		ChoiceRenderer<Boolean> rendererSimNao = new ChoiceRenderer<Boolean>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getIdValue(Boolean object, int index) {
				if(object==null)
					return null;
				
				return object.toString();
			}

			@Override
			public Object getDisplayValue(Boolean object) {
				if (object==null) {
					return component.getString("todos");
				}else if (object.equals(Boolean.TRUE) || object.equals(SIM)) {
					return component.getString("sim");
				} else if (object.equals(Boolean.FALSE) || object.equals(NAO)) {
					return component.getString("nao");
				}
				return null;
			}
		};
		return rendererSimNao;
	}
	
	/**
	 * Ler o arquivo do disco para efetuar sua gravaï¿½ï¿½o no banco e deleta.
	 * @param	String	nomeArquivo
	 * @param	String	diretorio
	 * @throws	Exception
	 */
	public static byte[] lerArquivo(String nomeArquivo, String diretorio, boolean deletar) throws FileNotFoundException, IOException{
		int tamanhoFile = 0;
		File file = new File(diretorio + nomeArquivo);
		FileInputStream fis = new FileInputStream(file);
		Long tamanhoAux = new Long(file.length());
		tamanhoFile = tamanhoAux.intValue();
		byte[] ret = new byte[tamanhoFile];
		fis.read(ret, 0, tamanhoFile);
		fis.close();
		if(deletar){
			file.delete();
		}
		return ret;
	}
	
	/**
	 * Salva um arquivo em um determinado diretorio.
	 * @param 	String	nomeArquivo
	 * @param 	byte[]	arquivo
	 */
	public static void salvarArquivo(String nomeArquivo, byte[] arquivo){
		FileOutputStream file  = null;
		try{
			file = new FileOutputStream(nomeArquivo);
			file.write(arquivo);
			file.flush();
			file.close();
		}catch(Exception exception) {
			exception.printStackTrace(System.out);
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Ler o arquivo do disco para efetuar sua gravaï¿½ï¿½o no banco e deleta.
	 * @param	String	nomeArquivo
	 * @param	String	diretorio
	 * @throws	Exception
	 */
	public static void apagarArquivo(String nomeArquivo, String diretorio) throws FileNotFoundException, IOException{
		File file = new File(diretorio + nomeArquivo);
		file.delete();
	}
	
	public static void notify(AjaxRequestTarget target,String mensagem,String tipo){
//		target.appendJavaScript("$.notify('"+mensagem+"',\""+tipo+"\");");
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: '"+tipo+"',"
				+ "clickToHide: true,"
				+ "showDuration: 500,"
				+ "autoHide: true});");
	}
	
	public static void notifyError(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'error',"
				+ "clickToHide: true,"
				+ "showDuration: 500,"
				+ "autoHide: true});");
	}
	
	public static void notifyInfo(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'info',"
				+ "clickToHide: true,"
				+ "showDuration: 500,"
				+ "autoHide: true});");
	}
	
	public static void notifySuccess(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'success',"
				+ "clickToHide: true,"
				+ "showDuration: 500,"
				+ "autoHide: true});");
	}
	
	public static void notifyWarn(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'warn',"
				+ "clickToHide: true,"
				+ "showDuration: 500,"
				+ "autoHide: true});");
	}
	
	
	/**
	 * Retorna um número randômico
	 * 
	 * @return int número randômico
	 */
	public static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	/**
	 * Retorna uma string randomica
	 * 
	 * @param  int Menor numero
	 * @param  int Maior  numero
	 * @return String randomica
	 */
	public static String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}
	
	
	public static void main(String[] args) {
	}
	
	public static Date zeraHoraData(Date data){
		if(data!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data);
			zeraHoraData(calendar);
			return calendar.getTime();
		}
		return null;
	}
	
	public static void zeraHoraData(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
}
