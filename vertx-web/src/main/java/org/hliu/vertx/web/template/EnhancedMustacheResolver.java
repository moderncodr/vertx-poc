package org.hliu.vertx.web.template;

import java.io.Reader;

import com.github.mustachejava.MustacheResolver;
import com.github.mustachejava.resolver.ClasspathResolver;

/**
 * This resolver supports specifying the base folder. 
 */
public class EnhancedMustacheResolver implements MustacheResolver {

	private final ClasspathResolver classpathResolver;
	
	private final ClasspathResolver baseClasspathResolver;
	
	public EnhancedMustacheResolver(String base) {
		classpathResolver  = new ClasspathResolver();
		baseClasspathResolver  = new ClasspathResolver(base);
	}
	@Override
	public Reader getReader(String resourceName) {
		Reader reader = classpathResolver.getReader(resourceName);
	      if(reader == null) {
	        reader = baseClasspathResolver.getReader(resourceName);
	      }
	      return reader;
	}

}
