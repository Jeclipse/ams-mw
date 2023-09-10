package com.byblosbank.ams.service;

public interface QueryWebClient {

	String performGetQuery(String relativeURI);
	
	String performPostQuery(String relativeURI, String requestBody, String method);
	

}
