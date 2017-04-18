package com.mycompany;

/**
 * 
 * @author Elizio.Rezende
 */
public class DAOException extends RuntimeException {

	/**
	 * Numero serial para suporte a serializacao.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor default.
	 */
	public DAOException() {
		super();
	}

	/**
	 * Constr�i a excecao com uma mensagem de erro.
	 * 
	 * @param message
	 *            mensagem de erro.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constr�i a excecao com uma mensagem de erro e a causa raiz da excecao.
	 * 
	 * @param message
	 *            mensagem de erro.
	 * @param cause
	 *            causa raiz da excecao.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constr�i a excecao com a causa raiz da excecao.
	 * 
	 * @param message
	 *            mensagem de erro.
	 * @param cause
	 *            causa raiz da excecao.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}
