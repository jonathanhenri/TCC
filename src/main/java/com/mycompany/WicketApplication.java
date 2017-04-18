package com.mycompany;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mycompany.visao.login.Login;


/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.mycompany.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication implements ApplicationContextAware{
	private ApplicationContext context;
	boolean isInitialized = false;

	
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return Login.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		if (!isInitialized) {
			super.init();
			setListeners();
			isInitialized = true;

			System.setProperty("jsse.enableSNIExtension", "false");

			// Remove as tags do wicket das páginas html
			// Quando muda a aplicação para deployment(web.xml), isso é feito
			// automaticamente
			getMarkupSettings().setStripWicketTags(true);
			
			
			getApplicationSettings().setPageExpiredErrorPage(Login.class);
			getApplicationSettings().setInternalErrorPage(Login.class);
			
			
			if(Application.get().usesDeploymentConfig()){
				//WicketUtil.setUrl("http://sistema.controlese.com/");
//				WicketUtil.setUrl("https://www.controlese.com/");
				System.setProperty("ambienteDesenvolvimento", "false");
			}else{
				//Indica se o ambiente é de desenvolvimento, caso seja positivo, apenas algumas categorias estão sendo incluídas (CategoriaDataGenerator)
				//Estava ficando muito lento para subir a aplicação
				System.setProperty("ambienteDesenvolvimento", "true");
				
				//Gera os wicketpath nos elementos. Utilizado para teste unitario
				getDebugSettings().setOutputComponentPath(true);
			}
		}
		
		//Corrigindo um bug de access Denied ao jquery 
		SecurePackageResourceGuard guard = new SecurePackageResourceGuard();
		guard.addPattern("+jquery*.*"); 
		guard.addPattern("+*jquery*.*");
		guard.addPattern("+*.htm"); 
		
        getResourceSettings().setPackageResourceGuard(guard);
		
		mountPage("login", Login.class);
	}
	
	@Override
    public final Session newSession(Request request, Response response) {
        return new BasicAuthenticationSession(request);
    }
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	private void setListeners() {
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, context));
	}
	
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return Login.class;
	}
	
	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return BasicAuthenticationSession.class;
	}
}
