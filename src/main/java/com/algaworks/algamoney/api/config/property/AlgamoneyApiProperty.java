package com.algaworks.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {
	
	private  Seguranca seguranca =  new Seguranca();
	
	@Getter
	@Setter
	public static class Seguranca{
		private boolean enableHttps;
		private String origem =  "http://localhost:8000";
	}
}
