package org.hliu.vertx.web.mvc;

import java.util.function.Function;

import org.hliu.vertx.web.utils.MessageProvider;

public class AbstractPage {

	private String uri;
	
	private String host;
	
	private String locale;
	
	private String template;
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public AbstractPage(String template) {
		this.template = template;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private String ip;
	
	private MessageProvider messageProvider = MessageProvider.getInstance();
	
	/**
	 * The function for i18n message 
	 */
	public Function<String, String> i18n = new Function<String, String>() {

		public String apply(String key) {
			return messageProvider.getMessage(locale, key);
		}
	};

}
