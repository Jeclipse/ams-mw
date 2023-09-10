package com.byblosbank.ams.service.impl;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.byblosbank.ams.config.AMSConfigData;
import com.byblosbank.ams.service.QueryWebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AMSQueryWebClient implements QueryWebClient{
	

    private final WebClient.Builder webClientBuilder;

    private final AMSConfigData amsConfigData;

    @Override
    public String performGetQuery(String relativeURI) {
        log.info("Performing GET query with URI {}", relativeURI);
        return getWebClient(relativeURI)
                .bodyToMono(String.class)
                .log()//Logger
                .block();
    }
    
    @Override
    public String performPostQuery(String relativeURI, String requestBody, String method) {
        log.info("Performing {} query with URI {}", method, relativeURI);
        return postWebClient(relativeURI, requestBody, method)
                .bodyToMono(String.class)
                .log()//Logger
                .block();
    }
    
    private WebClient.ResponseSpec getWebClient(String relativeURI) {
        return webClientBuilder
                .build()
                .get()
                .uri(amsConfigData.getWebClient().getCreatioBaseUrl() + relativeURI)
                .accept(MediaType.valueOf(amsConfigData.getWebClient().getAcceptType()))
                .retrieve();
        
                /*.onStatus( For potential manual OnStatus handling
                        HttpStatus.UNAUTHORIZED::equals,
                        clientResponse -> Mono.just(new BadCredentialsException("Not authenticated!")))
                .onStatus(
                		status -> HttpStatus.Series.CLIENT_ERROR.equals(status.series()),
                        clientResponse -> Mono.error(
                                new AMSWebClientException("Fill")));*/
    }
    
    private WebClient.ResponseSpec postWebClient(String relativeURI, String requestBody, String method) {
        return webClientBuilder
                .build()
                .method(HttpMethod.valueOf(method))
                .uri(amsConfigData.getWebClient().getCreatioBaseUrl() + relativeURI)
                .accept(MediaType.valueOf(amsConfigData.getWebClient().getAcceptType()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve();
    }


}
