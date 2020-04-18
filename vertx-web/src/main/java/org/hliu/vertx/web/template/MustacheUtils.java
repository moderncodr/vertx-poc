package org.hliu.vertx.web.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

public class MustacheUtils {

	
	private static MustacheFactory factory = new DefaultMustacheFactory();

	public static MustacheFactory getFactory() {
		
		return factory ;
	}

}
