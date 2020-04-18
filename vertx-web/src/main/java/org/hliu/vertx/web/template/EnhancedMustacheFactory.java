package org.hliu.vertx.web.template;

import java.io.Reader;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheNotFoundException;
import com.github.mustachejava.MustacheResolver;

public class EnhancedMustacheFactory extends DefaultMustacheFactory {

	private final MustacheResolver mustacheResolver;
	
	public EnhancedMustacheFactory(String baseFolder) {
		mustacheResolver = new EnhancedMustacheResolver(baseFolder);
	}
	
	@Override
	  public Reader getReader(String resourceName) {
	    Reader reader = mustacheResolver.getReader(resourceName);
	    if (reader == null) {
	      throw new MustacheNotFoundException(resourceName);
	    }
	    return reader;
	  }
}
