package com.byblosbank.ams.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "byblosbank-ams")
public class AMSConfigData {
	private WebClient webClient;

	@Data
	public static class WebClient {
		private Integer connectTimeoutMs;
		private Integer readTimeoutMs;
		private Integer writeTimeoutMs;
		private Integer maxInMemorySize;
		private String contentType;
		private String acceptType;
		private String creatioBaseUrl;
	}
	
}
