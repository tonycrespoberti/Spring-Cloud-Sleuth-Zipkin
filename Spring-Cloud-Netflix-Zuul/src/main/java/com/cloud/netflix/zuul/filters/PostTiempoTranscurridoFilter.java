package com.cloud.netflix.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	
	//If we want that filter be executed during each request.
	//if there is a request param
	@Override
	public boolean shouldFilter() {
		
		return true;
	}

	//Here, we code the business logic when execute the filter
	@Override
	public Object run() throws ZuulException {
		
		//We capture the request object of client petition
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("Entrando a post filter");
		
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		log.info(String.format("Tiempo transcurrido en seg %s", tiempoTranscurrido.doubleValue()/1000.00));
		log.info(String.format("Tiempo transcurrido en miliseg %s", tiempoTranscurrido.doubleValue()));
		
		
		
		return null;
	}

	
	//Return always must be "pre", "post" or "router" They are the three types (key words) of available filters
	//pre = before the route or comm to microservice be established. We ca pass any param to request
	//post = It is executed inmediately after that pre has finished. We can modify for instance the header of request.
	//route = Here the route or comm is been sent.
	@Override
	public String filterType() {
		
		return "post";
	}

	@Override
	public int filterOrder() {
		
		return 1;
	}

}
