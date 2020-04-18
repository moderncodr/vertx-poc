package org.hliu.vertx.web.mvc;

import java.io.StringWriter;
import java.util.List;

import org.hliu.vertx.web.template.MustacheUtils;
import org.hliu.vertx.web.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import io.vertx.core.Handler;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Locale;
import io.vertx.ext.web.RoutingContext;

public abstract class PageHandler<T extends AbstractPage> implements Handler<RoutingContext> {

	private static final String TEMPLATE_ROOT = "templates/";

	protected RoutingContext context;

	private static final Logger logger = LoggerFactory.getLogger(PageHandler.class);

	private static final String DEFAULT_LOCALE = "en";

	private String defaultLocale = DEFAULT_LOCALE;

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	protected String getLocale(RoutingContext context, T page) {

		// First get from the request
		Cookie lang = context.getCookie("lang");
		// Initialize the page attribute
		logger.debug("Get user lang setting from cookie: {}.", lang);
		if (lang != null) {
			return lang.getValue();
		} else {

			// get locale from header
			List<Locale> locales = context.acceptableLocales();
			if (locales != null && !locales.isEmpty()) {
				logger.debug("Get user lang setting from header: {}.", locales);
				String language = locales.get(0).toString();

				if (language.contains("-")) {
					return language.replace("-", "_");
				}
				return language;
			}
			logger.debug("Use default lang for the user: {}.", defaultLocale);
			return defaultLocale;
		}

	}

	@Override
	public void handle(RoutingContext ctx) {
		logger.debug("Start to handle the request.");
		this.context = ctx;

		T page = this.getPage(context);

		initPage(context, page);

		this.preProcess(context, page);

		ProcessResult result = this.process(context, page);

		if (result.getStatus() == ProcessResult.OK_STATUS) {

			MustacheFactory mf = MustacheUtils.getFactory();

			long start = System.nanoTime();

			try {
				// Compile the template
				Mustache m = mf.compile(TEMPLATE_ROOT + page.getTemplate());
				StringWriter outputWriter = new StringWriter();
				m.execute(outputWriter, page).flush();
				context.response().end(outputWriter.toString());

				// Set the cookie
			} catch (Exception e) {
				logger.error("Failed to process the request! page: {}, template: {}.", page.getClass(),
						page.getTemplate(), e);
				// Render the error template TODO
				context.response().end("Internal error.");
			}

			long end = System.nanoTime();
			logger.debug("Process time: {}. ", (end - start));
		} else if (result.getStatus() == ProcessResult.NOT_FOUND_STATUS) {

			context.response().setStatusCode(404);
			context.response().end("Not found.");
		} else if (result.getStatus() == ProcessResult.NOT_ALLOWED_STATUS) {

			context.response().setStatusCode(404);
			context.response().end("Not allowed.");
		} else if (result.getStatus() == ProcessResult.REDIRECT_STATUS) {
			context.request().response().setStatusCode(302).headers().set("Location", result.getTarget());
			context.request().response().end();
		}

		logger.debug("Finish handling the request.");
	}

	protected abstract T getPage(RoutingContext context);

	protected abstract ProcessResult process(RoutingContext context, T page);

	protected void preProcess(RoutingContext context, T page) {

	}

	private void initPage(RoutingContext ctx, T page) {
		page.setUri(context.request().uri());
		final String host = HttpUtils.getHost(context.request());
		page.setHost(host);

		final String ip = HttpUtils.getClientIp(context.request());
		page.setIp(ip);

	}
}