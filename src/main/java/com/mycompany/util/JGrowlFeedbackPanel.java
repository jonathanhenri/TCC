package com.mycompany.util;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * A feedback panel that shows feedback messages with JGrowl (http://www.stanlemon.net/projects/jgrowl.html)
 * 
 * @author martin-g
 */
@SuppressWarnings("serial")
public class JGrowlFeedbackPanel extends FeedbackPanel implements IHeaderContributor {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The settings for the different feedback levels
	 * 
	 * Full list of supported settings at: http://www.stanlemon.net/projects/jgrowl.html#options
	 */
	
	private Options errorOptions;
	private Options warningOptions;
	private Options fatalOptions;
	private Options infoOptions;
	private Options debugOptions;
	private Options successOptions;

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public JGrowlFeedbackPanel(final String id)
	{
		this(id, null);
	}

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 * 
	 * @param id
	 * @param filter
	 */
	public JGrowlFeedbackPanel(final String id, IFeedbackMessageFilter filter)
	{
		super(id, filter);
		
		// this feedback panel is intended to be used in JavaScript environment (AJAX)
		setOutputMarkupId(true);
		
		// needed to not escape apostrophes
		setEscapeModelStrings(false);
	}

	/**
	 * Generates a component that is used to display the message inside the feedback panel. This
	 * component must handle being attached to <code>span</code> tags.
	 * 
	 * By default a {@link Label} is used.
	 * 
	 * Note that the created component is expected to respect feedback panel's
	 * {@link #getEscapeModelStrings()} value
	 * 
	 * @param id
	 *            parent id
	 * @param message
	 *            feedback message
	 * @return component used to display the message
	 */
	@Override
	protected Component newMessageDisplayComponent(final String id, final FeedbackMessage message)
	{
		final JGrowlFeedbackMessage jgrowlFeedbackMessage = new JGrowlFeedbackMessage(message) {
			
			@Override
			protected Options newFatalOptions() {
				return fatalOptions;
			}
			
			@Override
			protected Options newErrorOptions() {
				return errorOptions;
			}
			
			@Override
			protected Options newWarningOptions() {
				return warningOptions;
			}
			
			@Override
			protected Options newInfoOptions() {
				return infoOptions;
			}
			
			@Override
			protected Options newDebugOptions() {
				return debugOptions;
			}
			
			@Override
			protected Options newSuccessOptions() {
				return successOptions;
			}
		};
		
		final String jgrowlJavaScript = jgrowlFeedbackMessage.toJavaScript();
		
		final Label label = new Label(id, jgrowlJavaScript);
		label.setEscapeModelStrings(JGrowlFeedbackPanel.this.getEscapeModelStrings());
		return label;
	}

	public void renderHead(final IHeaderResponse response) {
//		response.renderCSSReference(new ResourceReference(JGrowlFeedbackPanel.class, "res/jquery.jgrowl.css"));
//		response.renderJavascriptReference(JQueryBehavior.JQUERY_JS);
//		response.renderJavascriptReference(new ResourceReference(JGrowlFeedbackPanel.class, "res/jquery.jgrowl.js"));	
	}

	public JGrowlFeedbackPanel setErrorMessageOptions(final Options errorOptions) {
		this.errorOptions = errorOptions;
		
		return this;
	}
	
	public JGrowlFeedbackPanel setFatalMessageOptions(final Options fatalOptions) {
		this.fatalOptions = fatalOptions;
		
		return this;
	}
	
	public JGrowlFeedbackPanel setWarningMessageOptions(final Options warningOptions) {
		this.warningOptions = warningOptions;
		
		return this;
	}
	
	public JGrowlFeedbackPanel setInfoMessageOptions(final Options infoOptions) {
		this.infoOptions = infoOptions;
		
		return this;
	}
	
	public JGrowlFeedbackPanel setDebugMessageOptions(final Options debugOptions) {
		this.debugOptions = debugOptions;
		
		return this;
	}
	
	public JGrowlFeedbackPanel setSuccessMessageOptions(final Options successOptions) {
		this.successOptions = successOptions;
		
		return this;
	}
}