package com.example.moneyapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {
	
	public AlgamoneyApiProperty() {
		// TODO Auto-generated constructor stub
	}
	
	private final Seguranca seguranca = new Seguranca();
	
	public static class Seguranca {
		
		public Seguranca() {
			// TODO Auto-generated constructor stub
		}
		
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
	}
	
	private String origemPermitida = "http://localhost:8000";
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public String getOrigemPermitida() {
		return origemPermitida;
	}


	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}


	

}
