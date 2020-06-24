package com.cloud.netflix.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);

	
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
		System.out.println(request.toString());
		
		log.info(String.format("%s request enruta a %s", request.getMethod(), request.getRequestURL().toString()));
		
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		return null;
	}

	
	//Return always must be "pre", "post" or "router" They are the three types of available filters
	//pre = before the route or comm to microservice be established. We ca pass any param to request
	//post = It is executed inmediately after that pre has finished. We can modify for instance the header of request.
	//route = Here the route or comm is been sent.
	@Override
	public String filterType() {
		
		return "pre";
	}

	@Override
	public int filterOrder() {
		
		return 1;
	}

}
