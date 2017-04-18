package com.mycompany.visao.comum;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.persistence.interfaces.IAlunoDAO;

public class BasePage extends WebPage{
	private static final long serialVersionUID = 1L;
	@SpringBean(name="usuarioDAO")
	IAlunoDAO usuarioDAO;
	private List<String> names = Arrays.asList(new String[] { "Kumarsun", "Ramkishore", 
			"Kenneth", "Kingston", "Raju", "Rakesh", "Vijay", "Venkat", "Sachin" });

//	List<Aluno> usuarios = usuarioDAO.getUsuarios();

	public BasePage() {

//		AutoCompleteTextField<String> txtName = new AutoCompleteTextField<String>("name", new Model<String>()){
//			private static final long serialVersionUID = 1L;
//			protected Iterator<String> getChoices(String input) {
//				List<String> probables = new ArrayList<String>();
//
//				Iterator<Aluno> iter = usuarios.iterator();
//				while (iter.hasNext()) {
//					Aluno name =  iter.next();
//					String p=name.getLogin();
//					if (p.startsWith(input)) {
//						probables.add(p);
//					}
//				}
//				return probables.iterator();
//			}
//		};
//		add(txtName);

	}
}