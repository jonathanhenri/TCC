package com.mycompany.util;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.io.IClusterable;

/**
 * A feedback message that will be shown as JGrowl message (http://www.stanlemon.net/projects/jgrowl.html)
 * 
 * @author martin-g
 */
public class JGrowlFeedbackMessage implements IClusterable {

	private static final long serialVersionUID = 1L;

	private final String message;
	
	/**
	 * A list with all supported messages could be found at: (http://www.stanlemon.net/projects/jgrowl.html#options)
	 */
	private final Options options;
	
	public JGrowlFeedbackMessage(final FeedbackMessage feedbackMessage) {
	
		this.message = feedbackMessage.getMessage().toString();
		
		switch (feedbackMessage.getLevel()) {
			case FeedbackMessage.ERROR:
				this.options = newErrorOptions();
				break;
			case FeedbackMessage.FATAL:
				options = newFatalOptions();
				break;	
			case FeedbackMessage.DEBUG:
				options = newDebugOptions();
				break;
			case FeedbackMessage.WARNING:
				options = newWarningOptions();
				break;	
			case FeedbackMessage.INFO:
				options = newInfoOptions();
				break;
			case FeedbackMessage.SUCCESS:
				options = newSuccessOptions();
				break;
			default:
				options = new Options();
		}	
	}
	
	protected Options newSuccessOptions() {

		return new Options();
	}
	
	protected Options newFatalOptions() {

		return new Options();
	}

	protected Options newDebugOptions() {

		return new Options();
	}

	protected Options newWarningOptions() {

		return new Options();
	}

	protected Options newInfoOptions() {

		final Options infoOptions = new Options();
		
		return infoOptions;
	}

	protected Options newErrorOptions() {

		return new Options();		
	}

	/**
	 * Constructs the JGrowl invocation
	 * E.g.: '$.jGrowl("A message", {'option1' : 'value1', 'option2' : 'value2' })'
	 * 
	 * @return the jGrowl JavaScript
	 */
	public String toJavaScript() {
		
		final StringBuilder javaScript = new StringBuilder();
		javaScript.append("$('#divFeedbackJGrowl').jGrowl(\"");
		javaScript.append(message);
		javaScript.append("\"");
		if (options != null) {
			javaScript.append(", ");
			javaScript.append(options.toString());
		}
		javaScript.append(");");
		
		return  javaScript.toString();
	}
}