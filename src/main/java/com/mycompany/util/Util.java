package com.mycompany.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.context.SecurityContextHolder;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.WicketApplication;
import com.mycompany.anotacao.ListarPageAnotacao;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAlunoServico;

public class Util {
	
	@SpringBean(name="alunoServico")
	private static IAlunoServico alunoServico;
	
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

    public static <T> Set<T> toSet(List<T> beanList) {
		Set<T> result = new LinkedHashSet<T>();
		if( beanList!=null){
		result.addAll(beanList);
		}
		return result;
	}
    
    public static <T> List<T> toList(Collection<T> beanList) {
		List<T> result = new ArrayList<T>();
		if( beanList!=null){
			result.addAll(beanList);
		}
		return result;
	}
    
    
    public static Boolean possuiPermissao(Aluno alunoLogado,AbstractBean<?> abstractBean,Integer operacao){
    	
    	if(alunoLogado!=null){
//    		if(aluno.getAdministracao()!=null && aluno.getAdministracao().getAdministradorCampus()){
//    			return true;
//    		}
    		if(alunoLogado.getPerfilAcesso()!=null && alunoLogado.getPerfilAcesso().getPermissoesAcesso()!=null && alunoLogado.getPerfilAcesso().getPermissoesAcesso().size()>0){
    			
    			for(PermissaoAcesso permissaoAcesso:alunoLogado.getPerfilAcesso().getPermissoesAcesso()){
    				if(permissaoAcesso.getCasoDeUso().isInstance(abstractBean)){
    					if(permissaoAcesso.getOperacao().equals(operacao)){
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    public static Boolean possuiPermissao(Aluno alunoLogado,Integer permissao,Integer operacao){
    	if(alunoLogado!=null){
//    		if(aluno.getAdministracao()!=null && aluno.getAdministracao().getAdministradorCampus()){
//    			return true;
//    		}
    		if(alunoLogado.getPerfilAcesso()!=null && alunoLogado.getPerfilAcesso().getPermissoesAcesso()!=null && alunoLogado.getPerfilAcesso().getPermissoesAcesso().size()>0){
    			
    			for(PermissaoAcesso permissaoAcesso:alunoLogado.getPerfilAcesso().getPermissoesAcesso()){
    				if(permissaoAcesso.getPermissao().equals(permissao)){
    					if(permissaoAcesso.getOperacao().equals(operacao)){
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
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
	
	public static String getDateFormat(Date date){
		DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
		return formata.format(date);
	}
	
	public static String getMesAbreviadoDate(Date date){
		DateFormat formata = new SimpleDateFormat("MMM");
		return formata.format(date);
	}
	
	public static String getDiaSemanaDate(Date date){
		DateFormat formata = new SimpleDateFormat("dd");
		return formata.format(date);
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
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: '"+tipo+"',"
				+ "clickToHide: true,"
				+ "showDuration: 800,"
				+ "autoHide: true});");
	}
	
	public static void notifyError(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'error',"
				+ "clickToHide: true,"
				+ "showDuration: 800,"
				+ "autoHide: true});");
	}
	
	public static void notifyInfo(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'info',"
				+ "clickToHide: true,"
				+ "showDuration: 800,"
				+ "autoHide: true});");
	}
	
	public static void notifySuccess(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'success',"
				+ "clickToHide: true,"
				+ "showDuration: 800,"
				+ "autoHide: true});");
	}
	
	public static void notifyWarn(AjaxRequestTarget target,String mensagem){
		target.appendJavaScript("$.notify('"+mensagem+"',{"
				+ "className: 'warn',"
				+ "clickToHide: true,"
				+ "showDuration: 800,"
				+ "autoHide: true});");
	}
	
	
	public static Properties properties = null;
	
	
	/**
	 * Converte, de forma simples, um valor de qualquer tipo para um primitivo
	 * int.
	 * 
	 * @param Object
	 *            value valor a ser convertido.
	 * @return int o valor convertido.
	 */
	public static int toInt(Object value) {
		return ((Integer) new IntegerConverter().convert(Integer.class, value)).intValue();
	}

	/**
	 * Converte, de forma simples, um valor de qualquer tipo para Integer.
	 * 
	 * @param Object
	 *            valor a ser convertido.
	 * @return Integer o valor convertido ou null para valor igual a null.
	 * @see org.apache.commons.beanutils.converters.IntegerConverter
	 */
	public static Integer toInteger(Object value) {
		return (value == null) ? null : (Integer) new IntegerConverter().convert(Integer.class, value);
	}

	/**
	 * Converte um iterator em list
	 * 
	 * @param Collection
	 *            collection a ser convertida
	 * @return List a lista obtida
	 */
	public static List<?> iteratorToList(Iterator<?> iterator) {
		return IteratorUtils.toList(iterator);
	}

	/**
	 * Verifica se o numero de telefone est� no formato v�lido XX-XXXX-XXXX
	 * 
	 * @return
	 */
	public static boolean isTelefoneValido(String phoneNumber) {
		Pattern pattern = Pattern.compile("\\(\\d{2}\\) \\d{4}-\\d{4}");

		if (!pattern.matcher((String) phoneNumber).matches()) {
			return false;
		}
		return true;
	}

	/**
	 * Retorna um numero rand�mico
	 * 
	 * @return int Numero rand�mico
	 */
	public static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	/**
	 * Retorna uma string rand�mica
	 * 
	 * @param  int Menor numero
	 * @param  int Maior numero
	 * @return String rand�mica
	 */
	public static String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}

	/**
	 * Retorna o pattern de acordo com o Locale
	 * Essa fun��o s� est� preparada para funcionar com BR e US
	 * 
	 * @return int Numero rand�mico
	 */
	public static String getDatePattern(Locale locale) {
		if (locale.getCountry().equals("US")) {
			return "MM/dd/yyyy";
		} else {
			return "dd/MM/yyyy";
		}
	}
	
	public static String repete(String string, int quantidade) {
        StringBuffer retorno = new StringBuffer();
        for (int j = 0; j < quantidade; j++) {
            retorno.append(string);
        }
        return retorno.toString();
    }

	public static boolean isDate(String dateStr, Locale locale) {
		DateFormat df = new SimpleDateFormat(getDatePattern(locale));
		Calendar cal = new GregorianCalendar();

		// gerando o calendar
		try {
			cal.setTime(df.parse(dateStr));
		} catch (ParseException e) {
			return false;
		}

		// separando os dados da string para comparacao e validacao
		String[] data = dateStr.split("/");
		
		String dia = "";
		String mes = "";
		String ano = data[2];
		
		if(locale.getCountry().equals("US")){
			dia = data[1];
			mes = data[0];
		}else{
			dia = data[0];
			mes = data[1];
		}

		// testando se h� discrepancia entre a data que foi
		// inserida no calendar e a data que foi passada como
		// string. se houver diferenca, a data passada era
		// invalida
		if ((new Integer(dia)).intValue() != (new Integer(cal.get(Calendar.DAY_OF_MONTH))).intValue()) {
			// dia nao casou
			return (false);
		} else if ((new Integer(mes)).intValue() != (new Integer(cal.get(Calendar.MONTH) + 1)).intValue()) {
			// mes nao casou
			return (false);
		} else if ((new Integer(ano)).intValue() != (new Integer(cal.get(Calendar.YEAR))).intValue()) {
			// ano nao casou
			return (false);
		}

		return (true);
	}
	
	/**
	 * Formata um numero de acordo com o locale do usuario
	 * 
	 * @param locale locale do usuario logado
	 * @param valor Numero a ser formatado 
	 * @return  String
	 */
	public static String formataMoeda(Locale locale, Double valor) {
		
		NumberFormat numberFormat = DecimalFormat.getNumberInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		if(valor!=null){
			return numberFormat.format(valor);
		}else{
			return numberFormat.format(new Double(0));
		}
	}
	
	public static String desconsiderarZerosComecoString(String string){
        int y = 1;
        for(int i = 0; i < string.length(); i++){
            String x = string.substring(i, y);
            
            try {
                Integer numero = Integer.parseInt(x);
                if(numero.equals(0)){
                    y++;
                }else{
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
        String nova = string.substring(y-1, string.length());
        return nova;
    }
	
	public static String formataMoedaQuatroCasasDecimais(Locale locale, Double valor) {
		if(valor!=null){
			
			String valorString = valor.toString();
			int virgula = valorString.indexOf(".");
			int casasDecimais = 0 ;
			
			if(virgula!= -1){
				valorString = valorString.substring(virgula +1 , valorString.length());
				casasDecimais = valorString.length();
				
			}
			
			if(casasDecimais>4){
				casasDecimais = 4;
			}
			
			NumberFormat numberFormat = DecimalFormat.getNumberInstance(locale); 
			numberFormat.setMinimumFractionDigits(casasDecimais);
			numberFormat.setMaximumFractionDigits(casasDecimais);
		
		
			return numberFormat.format(valor);
		}else{
			return "0,00";
		}
	}
	public static String formataMoedaNaoNula(Double valor) {
		if(valor==null)
			return null;
		
		return formataMoeda(new Locale("pt", "BR"), valor);
	}
	
	public static String formataMoedaSemLocale(Double valor) {
		return formataMoeda(new Locale("pt", "BR"), valor);
	}
	
	public static String formataMoedaSemLocaleQuatroCasasDecimais(Double valor) {
		return formataMoedaQuatroCasasDecimais(new Locale("pt", "BR"), valor);
	}
	
	public static <T> Set<T> toSet(Collection<T> beanList) {
		Set<T> result = new LinkedHashSet<T>();
		if( beanList!=null){
			result.addAll(beanList);
		}
		return result;
	}
	
	/**
	 * Retorna uma lista com as constantes que representam Sim=1 e Não=0
	 * @return
	 */
	public static List<Integer> getConstantesSimNao(){
		return Arrays.asList(SIM, NAO);
	}
	
	
	public static Integer getMes(Date data){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		Integer mes = calendar.get(Calendar.MONTH);
		
		return mes;
	}
	
	public static Integer getAno(Date data){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		Integer ano = calendar.get(Calendar.YEAR);
		
		return ano;
	}
	
	public static Integer getDia(Date data){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		Integer dia = calendar.get(Calendar.DAY_OF_MONTH);
		
		return dia;
	}
	
	public static int getDiaDaSemanaHoje(){
		Calendar xmas = new GregorianCalendar(Util.getAno(new Date()).intValue(),Util.getMes(new Date()).intValue(), Util.getDia(new Date()).intValue());
		int diaDaSemana = xmas.get(Calendar.DAY_OF_WEEK);
		
		return diaDaSemana;
	}
	
	public static int getDiaDaSemana(Date dataInformada){
		Calendar xmas = new GregorianCalendar(Util.getAno(dataInformada).intValue(),Util.getMes(dataInformada).intValue(), Util.getDia(dataInformada).intValue());
		int diaDaSemana = xmas.get(Calendar.DAY_OF_WEEK);
		
		return diaDaSemana;
	}
	
	/**
	 * Define a primeira hora, minuto, segundo e milisegundo para a data informada.
	 * @param data
	 * @return
	 */
	public static Date zeraHoraData(Date data){
		if(data!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data);
			zeraHoraData(calendar);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * Define a ultima hora, minuto, segundo e milisegundo para a data informada.
	 * @param calendar
	 */
	public static void ultimaHoraData(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
	}
	
	/**
	 * Define a ultima hora, minuto, segundo e milisegundo para a data informada.
	 * @param data
	 * @return
	 */
	public static Date ultimaHoraData(Date data){
		if(data!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data);
			ultimaHoraData(calendar);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * Define a primeira hora, minuto, segundo e milisegundo para a data informada.
	 * @param calendar
	 */
	public static void zeraHoraData(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	public static String formataDataComHora(Date date, Locale locale) {
		if(date!=null){
			SimpleDateFormat simpleDate = new SimpleDateFormat(getDatePatternComHora(locale));
			return simpleDate.format(date);
		}
		
		return "";
	}
	
	public static String getDatePatternComHora(Locale locale) {
		if (locale.getCountry().equals("US")) {
			return "MM/dd/yyyy HH:mm:ss";
		} else {
			return "dd/MM/yyyy HH:mm:ss";
		}
	}
	
	public static String formataData(Date date, Locale locale) {
		if(date!=null){
			SimpleDateFormat simpleDate = new SimpleDateFormat(getDatePattern(locale));
			return simpleDate.format(date);
		}
		
		return "";
	}
	
	public static String formataDataSemLocale(Date data) {
		return formataData(data, new Locale("pt", "BR"));
	}
	
	public static String formataDataComHoraSemLocale(Date data) {
		if(data!=null){
			return formataDataComHora(data, new Locale("pt", "BR"));
		}
		
		return null;
	}
	
	/**
	 * Retorna a diferenca entre duas datas.
	 * @param 	Date 	(Data "DD/MM/AAAA HH:MM")
	 * @param 	Date 	(Data "DD/MM/AAAA HH:MM")
	 * @param 	int 	<br> Calendar.DAY_OF_MONTH - retorna a diferença em dias;
	 * 					<br> Calendar.WEEK_OF_MONTH - retorna a diferença em semanas; 
	 * 					<br> Calendar.MONTH - retorna a diferença em meses;
	 * 					<br> Calendar.DAY_OF_YEAR - retorna a diferença em anos.
	 * @param 	TimeZone 	("GMT-03:00")
	 * @return 	int
	 */
	public static int diferencaDatas(Date sdate1, Date sdate2, int tipoRetorno, TimeZone tz){
		Date date1 = sdate1;
		Date date2 = sdate2;

		Calendar cal1 = null; 
		Calendar cal2 = null;

		if (tz == null)
		{
			cal1=Calendar.getInstance(); 
			cal2=Calendar.getInstance(); 
		}
		else
		{
			cal1=Calendar.getInstance(tz); 
			cal2=Calendar.getInstance(tz); 
		}

		// data diferente pode ter offset diferente
		cal1.setTime(date1);          
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);

		cal2.setTime(date2);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);

		// Usa calculos com inteiros, trunca os decimais
		int seconds1   = (int)(ldate1/1000); //60*60*1000
		int seconds2   = (int)(ldate2/1000);
		
		int minute1   = (int)(ldate1/60000); //60*60*1000
		int minute2   = (int)(ldate2/60000);
		
		int hr1   = (int)(ldate1/3600000); //60*60*1000
		int hr2   = (int)(ldate2/3600000);

		int days1 = hr1/24;
		int days2 = hr2/24;

		int secondDiff  = seconds2 - seconds1;
		int minuteDiff  = minute2 - minute1;
		int dateDiff  = days2 - days1;
		int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK))<0 ? 1 : 0;
		int weekDiff  = dateDiff/7 + weekOffset; 
		int yearDiff  = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR); 
		int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

		if(tipoRetorno == Calendar.DAY_OF_MONTH || tipoRetorno == Calendar.DAY_OF_WEEK || tipoRetorno == Calendar.DAY_OF_WEEK_IN_MONTH || tipoRetorno == Calendar.DAY_OF_YEAR){
			return dateDiff;
		}else if(tipoRetorno == Calendar.WEEK_OF_MONTH || tipoRetorno == Calendar.WEEK_OF_YEAR){
			return weekDiff;
		}else if(tipoRetorno == Calendar.MONTH){
			return monthDiff;
		}else if(tipoRetorno == Calendar.YEAR){
			return yearDiff;
		}else if(tipoRetorno == Calendar.MINUTE){
			return minuteDiff;
		}else if(tipoRetorno == Calendar.SECOND){
			return secondDiff;
		}else{
			return dateDiff;
		}
	}
	
	public static int diferencaDatas(Calendar cal1, Calendar cal2, int tipoRetorno){
		Date sdate1 = cal1.getTime();
		Date sdate2 = cal2.getTime();
		
		return diferencaDatas(sdate1, sdate2, tipoRetorno, null);
	}
	
	public static List<Integer> getPeriodosListaRelacaoPeriodos(Set<RelacaoPeriodo> relacaoPeriodos){
		List<Integer> listaPeriodos = new ArrayList<Integer>();
		
		if(relacaoPeriodos!=null && relacaoPeriodos.size()>0){
			for(RelacaoPeriodo relacaoPeriodo:relacaoPeriodos){
				listaPeriodos.add(relacaoPeriodo.getPeriodo());
			}
		}
		
		return listaPeriodos;
	}
	
	/**
	 * Compara os valores das duas strings para verificar se antiga foi modificada.
	 * @param	String	valorAntigo
	 * @param	String	valorNovo
	 * @return	String	<br>
	 * 					<p>null - Se nao houve modificacao <br>
	 * 					"null" - Se houve modificacao de remocao do valor antigo; <br>
	 * 					String diferente de "null" e nao nula se houve modificacao para um novo valor nao null ou vazio. 
	 */
	public static String comparaObjetoAntigoNovo(String valorAntigo, String valorNovo){
		String textoCampo = null;
		if(valorAntigo==null){
			if(valorNovo!=null){
				if(!(valorNovo.trim().length()==0)){
					textoCampo = valorNovo;
				}
			}
		}else{
			if(valorNovo==null){
				textoCampo = "";
			}else{
				if(!valorAntigo.equals(valorNovo)){
					if(valorNovo.trim().length()==0){
						valorNovo = "";
					}
					textoCampo = valorNovo;
				}
			}
		}
		return textoCampo;
	}
	
	public static String getProperty(String key) {
		if(key==null){
			return null;
		}
		
		if(properties == null){
			 properties = new Properties();
			 try {
				properties.load(WicketApplication.class.getResourceAsStream("WicketApplication.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return properties.getProperty(key);
	}
	
	public static <T> Collection<T> comparaObjetosColecaoAntigaNova(Collection<T> colecaoAntiga, Collection<T> colecaoNova){
		Collection<T> colecao = new HashSet<T>();

		if(colecaoAntiga.isEmpty()){
			if(!colecaoNova.isEmpty()){
				return colecaoNova;
			}

			return null;
		}

		if(!colecaoNova.isEmpty()){
			if(colecaoAntiga.size() != colecaoNova.size()){
				return colecaoNova;
			}

			Iterator<T> colecaoAntigaIter = colecaoAntiga.iterator();
			while(colecaoAntigaIter.hasNext()){
				boolean diferente = true;

				String colecaoAntigaWhile = colecaoAntigaIter.next().toString();

				Iterator<T> colecaoNovaIter = colecaoNova.iterator();
				while(colecaoNovaIter.hasNext()){
					String colecaoNovaWhile = colecaoNovaIter.next().toString();

					if(colecaoAntigaWhile.equals(colecaoNovaWhile)){
						diferente = false;
						break;
					}
				}
				if(diferente){
					return colecaoNova;
				}
			}
			return null;
		}
		return colecao;
	}
	
	
	

	public static String replaceAll(String string, String velha, String nova){

		if(string == null) return ""; 

		if(nova == null){
			nova = "";
		}
		
		if(velha == null) return string;

		StringBuffer aux = new StringBuffer("");
		StringBuffer novaString = new StringBuffer(string);

		while(novaString.indexOf(velha) != -1){
			aux.append(novaString.substring(0, novaString.indexOf(velha)));
			aux.append(nova);
			aux.append(novaString.substring(novaString.indexOf(velha) + velha.length()));
			novaString.delete(0, novaString.length());
			novaString.append(aux.toString());
			aux.delete(0, aux.length());
		}
		return novaString.toString();
	}
	
	public static String replaceMascaraLetras(String numero){
		String numeroTemporario = new String(numero);
		String novoNumero = "";
		
		while(!numeroTemporario.equals("")){
			Integer numeroInteiro = null;
			
			if(numeroTemporario.indexOf(".")>0){
				numeroInteiro = Integer.valueOf(numeroTemporario.substring(0, numeroTemporario.indexOf(".")));
				numeroTemporario = numeroTemporario.substring(numeroTemporario.indexOf(".")+1);	
			}else{
				numeroInteiro = Integer.valueOf(numeroTemporario);
				numeroTemporario = "";
			}
			
				novoNumero = novoNumero + processaOrdenacao(numeroInteiro) + ".";
		}
		
		if(novoNumero.indexOf(".")>0){
			novoNumero = novoNumero.substring(0, novoNumero.length()-1);
		}
		
		return novoNumero;
	}
	
	public static String removerPontosEBarras(String string){
		if(string!=null)return string.replace(".", "").replace("/", "").replace("-", "").trim();
		return "";
	}
	
	private static String processaOrdenacao(Integer numeroInteiro){
		String numero = "";
		
		for(int i=0;i<numeroInteiro;i++){
			numero = numero + "a";
		}
		
		return numero;
	}
	
	
	public static void salvarArquivo(String nomeArquivo, byte[] arquivo, boolean throwsException) throws IOException {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(nomeArquivo);
            file.write(arquivo);
            file.flush();
            file.close();
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
            if(throwsException){
                throw exception;
            }
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	
	/**
	 * Copia os dados de um objeto para outro de acordo com o prefixo do atributo.
	 * 
	 * @param Object
	 * to
	 * @param Object
	 * from
	 * @param String
	 * prefix
	 */
	public static void populate(Object to,Object from,String prefix,Locale locale){
		try{
			PropertyDescriptor[] fromDescriptors = PropertyUtils.getPropertyDescriptors(from);
			String fromPropertyName;
			String toPropertyName;
			
			for(int i = 0; i < fromDescriptors.length; i++){
				fromPropertyName = fromDescriptors[i].getName();
				
				if("class".equals(fromPropertyName)){
					continue;
				}
				
				toPropertyName = fromPropertyName;
				
				if(prefix != null){
					if(fromPropertyName.startsWith(prefix)){
						toPropertyName = toPropertyName.substring(prefix.length());
						
						if(Character.isUpperCase(toPropertyName.charAt(0))){
							toPropertyName = toPropertyName.substring(0, 1).toLowerCase() + toPropertyName.substring(1);
						}
					}else{
						if(!Character.isUpperCase(toPropertyName.charAt(0))){
							toPropertyName = toPropertyName.substring(0, 1).toUpperCase() + toPropertyName.substring(1);
						}
						
						toPropertyName = prefix + toPropertyName;
					}
				}
				
				if(PropertyUtils.isReadable(from, fromPropertyName) && PropertyUtils.isWriteable(to, toPropertyName)){
					try{
						Object value = PropertyUtils.getSimpleProperty(from, fromPropertyName);
						// Se o campo do objeto de destino for uma data, entï¿½o transforma a string em data
						if(PropertyUtils.getPropertyType(to, toPropertyName).equals(Date.class)){
							DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
							if(value != null){
								if(value instanceof Date){
									BeanUtils.copyProperty(to, toPropertyName, value);
								}else{
									if(!((String) value).trim().equals("")){
										BeanUtils.copyProperty(to, toPropertyName, dateFormat.parse((String) value));
									}
								}
							}
							// Se o campo do objeto de origem for uma data, entï¿½o formata a data para string
						}else if(PropertyUtils.getPropertyType(from, fromPropertyName).equals(Date.class)){
							DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
							if(value != null){
								BeanUtils.copyProperty(to, toPropertyName, dateFormat.format((Date) value));
							}
						}else{
							if(PropertyUtils.getProperty(to, toPropertyName) instanceof byte[]){
								BeanUtils.copyProperty(to, toPropertyName, value != null ? value.toString().getBytes() : new Byte[1]);
								
								// Impede que popule campos do tipo string com valor vazio.
							}else if(value instanceof String && ((String) value).trim().equals("")){
								BeanUtils.copyProperty(to, toPropertyName, null);
								
							}else if(value instanceof String && !((String) value).trim().equals("")){
								BeanUtils.copyProperty(to, toPropertyName, value);
							}else{
								BeanUtils.copyProperty(to, toPropertyName, value);
							}
						}
					}catch(NoSuchMethodException e){
						throw new RuntimeException("geral.metodoNaoEncontrado");
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	//Mes começando do 0 até o 11, como padrão da classe Calendar.
	public static String getStringMes(int mes){
		
		switch (mes) {
		case 0:
			return "Janeiro";
		case 1:
			return "Fevereiro";
		case 2:
			return "Março";
		case 3:
			return "Abril";
		case 4:
			return "Maio";
		case 5:
			return "Junho";
		case 6:
			return "Julho";
		case 7:
			return "Agosto";
		case 8:
			return "Setembro";
		case 9:
			return "Outubro";
		case 10:
			return "Novembro";
		case 11:
			return "Dezembro";
		default:
			break;
		}
		
		return null;
	}
	
	public static String getStringDiaSemana(int dia){
		String diaSemana = "";
		
		switch (dia) {
		case 1:
			diaSemana =  "Domingo";
			break;
		case 2:
			diaSemana = "Segunda-feira";
			break;
		case 3:
			diaSemana = "Terça-feira";
			break;
		case 4:
			diaSemana = "Quarta-feira";
			break;
		case 5:
			diaSemana = "Quinta-feira";
			break;
		case 6:
			diaSemana = "Sexta-feira";
			break;
		case 7:
			diaSemana = "Sábado";
			break;
		default:
			break;
		}
		
		return diaSemana;
	}
	
	
	
	public static void deleteFile(File file){
		deleteFile(file, 10, 0);
	}
	
	public static void deleteFile(File file,int tries,int tried) {
		if(file.exists()){
			if(!file.delete()){
				System.gc(); // No caso da JVM por sí mesma achar que possui um manipulador para o arquivo
				if(!file.delete()){
					try{ // Agora espera e verifica se o arquivo já foi apagado
						Thread.sleep(200);
					}catch(InterruptedException e){}
					// Potencial loop permanente evitado com um contador. Se o arquivo ainda existe após todas as tentativas, desiste. Você agora tem outro problema.
					if(tried < tries){
						tried++;
						deleteFile(file, tries, tried++);
					}
				}
			}
		}
	}
	
	/**
	*	Retorna true se a string tiver somente números
	*	Retorna false se a string tiver letras no meio da string
	**/
	public static boolean soContemNumeros(String texto) {  
        if(texto == null)  
            return false;  
        for (char letra : texto.toCharArray())  
            if(letra < '0' || letra > '9')  
                return false;  
        return true;  
          
    }
	
	/**
	 *** CEP - resultado: 81580-200
		 format("#####-###", "81580200");
	 *** CPF - resultado 012.345.699-01
		 format("###.###.###-##", "01234569905");
	 *** CNPJ - resultado: 01.234.569/9052-34
		 format("##.###.###/####-##", "01234569905234");
	 * 
	 * */
	public static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static String replaceEspaco(String texto, int tamanho) {
		if(texto==null){
			texto = "";
		}else{
			if(texto.length()>tamanho){
				texto = texto.substring(0, tamanho);
			}
		}
		
		
        return StringUtils.rightPad(texto, tamanho, " ");
    }
	
	public static String formataCnpj(String cnpj){
		if(cnpj==null){
			cnpj = "";
		}else{
			cnpj = cnpj.replaceAll("\\.", "").replace("-", "").replace("/", "");
		}
		return cnpj;
	}
	
	public static String formataCpf(String cpf){
		if(cpf==null){
			cpf = "";
		}else{
			cpf = cpf.replaceAll("\\.", "").replace("-", "").replace("/", "");
		}
		return cpf;
	}
	
	public static String replaceZeros(String texto, int tamanho) {
		if(texto==null){
			texto = "";
		}else{
			texto = texto.replace(".", "").replace(",", "");
			
			if(texto.length()>tamanho){
				texto = texto.substring(0, tamanho);
			}
		}
		
        return StringUtils.leftPad(texto, tamanho, "0");
    }
	
	
	 // Metodo de reflexão para os filtros dinamicos
    public static List<FiltroDinamicoAtributo>  getNameFieldEntity(AbstractBean<?> abstractBean, boolean buscarAbstractBean){
        List<FiltroDinamicoAtributo> listaDeAtributos = new ArrayList<FiltroDinamicoAtributo>();
        Class<?> cls = abstractBean.getClass();
        Field[] fld = cls.getDeclaredFields();
        for(int i = 0; i < fld.length; i++){
        	ListarPageAnotacao cmp = fld[i].getAnnotation(ListarPageAnotacao.class); // Procura somente atributos com a anotação
            if(cmp!=null){
                FiltroDinamicoAtributo filtroDinamicoAtributo = new FiltroDinamicoAtributo();
                if(!buscarAbstractBean && AbstractBean.class.isAssignableFrom(fld[i].getType())){ // Usado para não buscar atributo do 3°nivel
                    continue;
                }
                if(Collection.class.isAssignableFrom(fld[i].getType())){
                    //Pega o tipo da lista ex: Set<EnderecoCliente> = EnderecoCliente
                    Class<?> listClass = getFirstParameterType(fld[i].getGenericType());
                    filtroDinamicoAtributo.setTypeCampo(listClass);
                }else{
                    filtroDinamicoAtributo.setTypeCampo(fld[i].getType());
                }
                if(cmp.nomeColuna()!=null && !cmp.nomeColuna().isEmpty()){ // Nome personalidado pela anotação
                    filtroDinamicoAtributo.setNomeCampoPersonalidado(cmp.nomeColuna());
                }
                if(cmp.fetch()!=null && !cmp.fetch().isEmpty()){
                    filtroDinamicoAtributo.setFetchAuxiliar(cmp.fetch());
                    if(cmp.nomeMetodoReferencia()!=null && !cmp.nomeMetodoReferencia().isEmpty()){
                        filtroDinamicoAtributo.setNomeAtributoReferenciaAuxiliar(cmp.nomeMetodoReferencia());
                    }
                }
                filtroDinamicoAtributo.setNomeCampo(fld[i].getName());
                listaDeAtributos.add(filtroDinamicoAtributo);
            }
        }
        return listaDeAtributos;
    }
    
	
	public static String replaceZeros(Integer texto, int tamanho) {
        return replaceZeros(texto.toString(), tamanho);
    }
	
	public static String replaceZeros(Double texto, int tamanho) {
        return replaceZeros(texto.toString().replace(".", "").replace(",", ""), tamanho);
    }
	
	public static String replaceEspaco(Integer texto, int tamanho) {
        return replaceEspaco(texto.toString(), tamanho);
    }
	
	public static String replaceEspaco(Double texto, int tamanho) {
        return replaceEspaco(texto.toString().replace(".", "").replace(",", ""), tamanho);
    }
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static String fileSeparator(String str) {
		String separator = File.separator;
		if(str != null && str != "");
 	    return str.replace("/",separator);
	}
	
	public static String concatenarStringComSeparador(String ...itens){
		String result = "";
		if(itens != null && itens.length > 0){
			for (int i = 0; i < itens.length; i++) {
				if(itens[i] != null && !itens[i].isEmpty()){
					if(i == 0 ){
						result += itens[i];
					}else{
						if(i == 1 && (itens[0] == null || itens[0] == "")){
							result += itens[i];
						}else{
							result += " - " + itens[i];
						}
					}
				}
			}
		}
		return result;
	}
	
	
	/**
	 * Serialize any object
	 * @param obj
	 * @return
	 */
	public static String serialize(Object obj) {
	    try {
	        ByteArrayOutputStream bo = new ByteArrayOutputStream();
	        ObjectOutputStream so = new ObjectOutputStream(bo);
	        so.writeObject(obj);
	        so.flush();
	        // This encoding induces a bijection between byte[] and String (unlike UTF-8)
	        return bo.toString("UTF-8");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
	
	/**
	 * Deserialize any object
	 * @param str
	 * @param cls
	 * @return
	 */
	public static <T> T deserialize(String str, Class<T> cls) {
	    // deserialize the object
	    try {
	        // This encoding induces a bijection between byte[] and String (unlike UTF-8)
	        byte b[] = str.getBytes("UTF-8"); 
	        ByteArrayInputStream bi = new ByteArrayInputStream(b);
	        ObjectInputStream si = new ObjectInputStream(bi);
	        return cls.cast(si.readObject());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	 /**
     * Pega o primeiro parâmetro do tipo
     * @param java.lang.reflect.Type type
     * @return Class<?>
     */
    public static Class<?> getFirstParameterType(Type type){
        ParameterizedType listType = (ParameterizedType) type;
        return (Class<?>) listType.getActualTypeArguments()[0];
    }
	
	public static boolean isEan13(String eanCode) {
		
		if(eanCode==null || eanCode.trim().equals(""))
			return false;
		
		eanCode = eanCode.trim();
		
		String caracteresValidos = "0123456789";
		
		for (int i = 0; i < eanCode.length(); i++) { 
			char digit = eanCode.charAt(i); 
			if (caracteresValidos.indexOf(digit) == -1) {
				return false;
			}
		}
		
		// Add five 0 if the code has only 8 digits
		if (eanCode.length() == 8 ) {
			eanCode = "00000" + eanCode;
		}
		
		// Check for 13 digits otherwise
		else if (eanCode.length() != 13) {
			return false;
		}
		
		// Get the check number
		int originalCheck = Integer.valueOf(eanCode.substring(eanCode.length() - 1));
		eanCode = eanCode.substring(0, eanCode.length() - 1);
		
		// Add even numbers together
		int even = Integer.valueOf(eanCode.charAt(1) + "").intValue() + 
				Integer.valueOf(eanCode.charAt(3) + "").intValue() + 
				Integer.valueOf(eanCode.charAt(5) + "").intValue() + 
				Integer.valueOf(eanCode.charAt(7) + "").intValue() + 
				Integer.valueOf(eanCode.charAt(9) + "").intValue() + 
				Integer.valueOf(eanCode.charAt(11) + "").intValue();
		
		// Multiply this result by 3
		even *= 3;
		
		// Add odd numbers together
		int odd = Integer.valueOf(eanCode.charAt(0) + "") + 
				Integer.valueOf(eanCode.charAt(2) + "") + 
				Integer.valueOf(eanCode.charAt(4) + "") + 
				Integer.valueOf(eanCode.charAt(6) + "") + 
				Integer.valueOf(eanCode.charAt(8) + "") + 
				Integer.valueOf(eanCode.charAt(10) + "");
		
		// Add two totals together
		int total = even + odd;
		
		// Calculate the checksum
	    // Divide total by 10 and store the remainder
	    int checksum = total % 10;
	    // If result is not 0 then take away 10
	    if (checksum != 0) {
	        checksum = 10 - checksum;
	    }

		// Return the result
		if (checksum != originalCheck) {
			return false;
		}
		
	    return true;
	}
	
	 public static String getStringValueField(FiltroDinamicoAtributo filtroDinamicoAtributo){
        String value = "";
        Class<?> typeCampo;
        if(filtroDinamicoAtributo.getAtributoEstrangeiro()==null){
            typeCampo = filtroDinamicoAtributo.getTypeCampo();
        }else{
            if(filtroDinamicoAtributo.getAtributoEstrangeiro().getAtributoEstrangeiro() == null){
                typeCampo = filtroDinamicoAtributo.getAtributoEstrangeiro().getTypeCampo();
            }else{
                typeCampo = filtroDinamicoAtributo.getAtributoEstrangeiro().getAtributoEstrangeiro().getTypeCampo();
            }
        }
        if(filtroDinamicoAtributo.getValorCampo()!=null){
            if(String.class.isAssignableFrom(typeCampo)){
                value = String.valueOf(filtroDinamicoAtributo.getValorCampo());
            }else if(Date.class.isAssignableFrom(typeCampo)){
                value = formataDataSemLocale((Date) filtroDinamicoAtributo.getValorCampo());
            }else if(Integer.class.isAssignableFrom(typeCampo) || Double.class.isAssignableFrom(typeCampo) || Long.class.isAssignableFrom(typeCampo)){
                value = String.valueOf(filtroDinamicoAtributo.getValorCampo());
            }else if(Boolean.class.isAssignableFrom(typeCampo)){
                Boolean valor = (Boolean) filtroDinamicoAtributo.getValorCampo();
                if(valor){
                    value = "Sim";
                }else{
                    value = "Não";
                }
            }
        }
        return value;
    }
	 
	 public static Search montarSearchFiltroDinamico(List<FiltroDinamicoAtributo> listaFiltroDinamicoAtributo){
	        Search search = new Search();
	        if(listaFiltroDinamicoAtributo!=null && listaFiltroDinamicoAtributo.size() > 0){
	            Filter filterAnd = Filter.and();
	            for(FiltroDinamicoAtributo atributo: listaFiltroDinamicoAtributo){
	                if(atributo.getAtributoEstrangeiro()!=null){ // Atributo de 2° Nivel.
	                    String nomeCampoSearch = atributo.getNomeCampo();
	                    if(atributo.getAtributoEstrangeiro().getAtributoEstrangeiro()!=null){ //Atributo do 3°Nivel
	                        nomeCampoSearch +="."+atributo.getAtributoEstrangeiro().getNomeCampo();
	                    }
	                    search.addFetch(nomeCampoSearch);
	                }
	                if(atributo.getOperador().equals(FiltroDinamicoAtributo.EQUALS)){
	                    if(Date.class.isAssignableFrom(atributo.getValorCampo().getClass())){
	                        Date date = (Date)atributo.getValorCampo();
	                        filterAnd.add(Filter.and(Filter.greaterOrEqual(atributo.getNomeCampoSearch(), Util.zeraHoraData(date)),Filter.lessOrEqual(atributo.getNomeCampoSearch(), Util.ultimaHoraData(date))));
	                    }else{
	                        filterAnd.add(Filter.equal(atributo.getNomeCampoSearch(), atributo.getValorCampo()));
	                    }
	                }else if(atributo.getOperador().equals(FiltroDinamicoAtributo.LIKE)){
	                    filterAnd.add(Filter.like(atributo.getNomeCampoSearch(), "%" + String.valueOf(atributo.getValorCampo())+"%"));
	                }else if(atributo.getOperador().equals(FiltroDinamicoAtributo.MAIOR_IQUAL)){
	                    if(Date.class.isAssignableFrom(atributo.getValorCampo().getClass())){
	                        Date date = (Date)atributo.getValorCampo();
	                        filterAnd.add(Filter.greaterOrEqual(atributo.getNomeCampoSearch(), Util.zeraHoraData(date)));
	                    }else{
	                        filterAnd.add(Filter.greaterOrEqual(atributo.getNomeCampoSearch(), atributo.getValorCampo()));
	                    }
	                }else if(atributo.getOperador().equals(FiltroDinamicoAtributo.MENOR_IQUAL)){
	                    if(Date.class.isAssignableFrom(atributo.getValorCampo().getClass())){
	                        Date date = (Date)atributo.getValorCampo();
	                        filterAnd.add(Filter.lessOrEqual(atributo.getNomeCampoSearch(), Util.ultimaHoraData(date)));
	                    }else{
	                        filterAnd.add(Filter.lessOrEqual(atributo.getNomeCampoSearch(), atributo.getValorCampo()));
	                    }
	                }
	            }
	            search.addFilter(filterAnd);
	        }
	        return search;
	    }
	 
	 
	 public static <T> T clonar(T entity,boolean clonarComId){
			try{
			
				@SuppressWarnings("all")
				Class<T> classeDestino = (Class<T>) entity.getClass();
				
				T objetoDestino = classeDestino.newInstance();
				
				PropertyDescriptor[] atributos = PropertyUtils.getPropertyDescriptors(entity);
				
				for(PropertyDescriptor atributo : atributos){
					
					if("class".equals(atributo.getName()) || "identifier".equals(atributo.getName()) || ("id".equals(atributo.getName()) && !clonarComId) ){
						continue;
					}
					
					// Copia o valor dos atributos não persisitidos para o objeto de destino
					Object value = PropertyUtils.getSimpleProperty(entity, atributo.getName());
					
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
