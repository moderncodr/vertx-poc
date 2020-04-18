package org.hliu.vertx.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;


public class HttpUtils {

	private static final String HOST_HEADER_NAME = "Host";
	private static final String X_FORWARDED_FOR = "X-Forwarded-For";

	private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String getCookie(RoutingContext context, String name) {
		
		Cookie value = context.getCookie(name);
		if(value != null) {
			return value.getValue();
		}
		
		return null;

	}

	public static void setCookie(RoutingContext context, String name,
			String value, long validTime) {

		Cookie cookie = Cookie.cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(validTime);
		
		context.addCookie(cookie);
	}

	public static String getHeader(HttpServerRequest request, String key) {
		
		return request.headers().get(key);
	}

	public static String getClientIp(HttpServerRequest request) {
		final String ips = request.headers().get(X_FORWARDED_FOR);
		if(ips != null) { 
			return getRemoteIp(ips);
		}
		else {
			return request.remoteAddress().host();
		}
	}

	public static String getRemoteIp(String ips) {
		if(ips.indexOf(",") == -1) {
			return ips;
		}
		else {
			// it is mutiple ips, use the last one
			String[] array = ips.split(",");
			return array[array.length - 1].trim();
		}
	}

	public static String getHost(HttpServerRequest request) {
		return request.headers().get(HOST_HEADER_NAME);
	}

	public static void clearCookie(RoutingContext context,
			String name) {
		context.removeCookie(name);
		
	}
	
	public static void addCookie(RoutingContext context, String name, String value) {
		Cookie cookie = Cookie.cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(YEAR_IN_SECOND);
		context.addCookie(cookie);
		
	}

	private static final long YEAR_IN_SECOND = 31536000;

	public static String getHeader(HttpServerRequest request, CharSequence key) {
		
		return request.headers().get(key);
	}

}
