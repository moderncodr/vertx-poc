package org.hliu.vertx.web.utils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProvider {
	
	private static MessageProvider instance;
	
	private ResourceBundle defaultResource;
	
	private static Collection<String> supportedLangs = Arrays.asList("en", "de", "zh");
	
	private Map<String, Locale> localeMapping = new HashMap<String, Locale>();
	
	private final static Logger logger = LoggerFactory.getLogger(MessageProvider.class);
	
	private Map<String, ResourceBundle> resources = new HashMap<String, ResourceBundle>();
	public static MessageProvider getInstance() {
		if(instance != null) {
			return instance;
		}
		
		instance = new MessageProvider();
		return instance;
		
	}

	private ResourceBundle getBundle(String lang) {
		ResourceBundle bundle = this.resources.get(lang);
		if (bundle != null) {
			return bundle;
		}
		
		if (supportedLangs.contains(lang)) {
			if ("en".equals(lang)) {
				return defaultResource;
			}
			Locale locale = localeMapping.get(lang);
			if (locale != null) {
				logger.debug("Create the locale for lang: {}.", lang);
				bundle = ResourceBundle.getBundle("messages",
						locale, new UTF8Control());
				resources.put(lang, bundle);
			} else {
				bundle = defaultResource;
			}
			
		} else {
			logger.debug("Use the default locale for unsupported lang: {}.", lang);
			bundle = defaultResource;
		}
		
		return bundle;
	}
	
	private MessageProvider() {
		localeMapping.put("en", Locale.ENGLISH);
		localeMapping.put("zh", Locale.SIMPLIFIED_CHINESE);
		localeMapping.put("de", Locale.GERMAN);
		localeMapping.put("mn", new Locale("mn"));
		
		ResourceBundle english = ResourceBundle.getBundle("messages",
				Locale.ENGLISH, new UTF8Control());
		resources.put("en", english);
		resources.put("en_US", english);
		defaultResource = english;
	}
	
	public String getMessage(String locale, String key) {
		ResourceBundle resource = getBundle(locale);
		String value;
		try {
			value = resource.getString(key);
		}
		catch(Exception ex) {
			logger.info("Message for key({}) is not found in bundle.", key);
			value = key;
		}
		
		return value;
	}
	
	public String getMessage(String locale, String key, Object...parameters) {
		ResourceBundle resource = getBundle(locale);
		String value;
		try {
			String pattern = resource.getString(key);
			value = MessageFormat.format(pattern, parameters);
		}
		catch(MissingResourceException ex) {
			logger.info("Message for key({}) is not found in bundle.", key);
			value = key;
		}
		catch(Exception ex) {
			logger.warn("Error to find key({}).", key, ex);
			value = key;
		}
		
		return value;
	}
}
