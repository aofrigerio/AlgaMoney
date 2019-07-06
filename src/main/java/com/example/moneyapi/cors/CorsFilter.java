package com.example.moneyapi.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.moneyapi.config.AlgamoneyApiProperty;

@Order
@Component
public class CorsFilter implements Filter {
	
	@Autowired
	AlgamoneyApiProperty algamoneyProperty;
	
	public CorsFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Origin", algamoneyProperty.getOrigemPermitida());
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		//if("OPTIONS".equals(request.getMethod()) && this.originPermitido.equals(request.getHeader("Origin")) ) {
		if("OPTIONS".equals(request.getMethod()) && algamoneyProperty.getOrigemPermitida().equals(request.getHeader("Origin")) ) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Context-Type, Accept");
			response.setHeader("Access-Control-Max-Age", "3600");
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
	}

}
