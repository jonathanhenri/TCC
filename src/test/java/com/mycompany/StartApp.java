package com.mycompany;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Seperate startup class for people that want to run the examples directly.
 */
public class StartApp {
	
	/**
	 * Main function, starts the jetty server.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		Server server = new Server();
		SocketConnector connector = new SocketConnector();
		connector.setPort(81);
		server.setConnectors(new Connector[] { connector });

		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath("/");
		context.setWar("src/main/webapp");
		context.setMaxFormContentSize(-1);
		
		server.addHandler(context);
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}