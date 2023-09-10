package com.byblosbank.ams.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    private final AMSConfigData.WebClient amsWebClientConfigData;
    
    public WebClientConfig(AMSConfigData amsWebClientConfigData) {
        this.amsWebClientConfigData = amsWebClientConfigData.getWebClient();
    }

    @Bean
    WebClient.Builder webClientBuilder()
    {

        return WebClient.builder()
                .baseUrl(amsWebClientConfigData.getCreatioBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, amsWebClientConfigData.getContentType())
                .defaultHeader(HttpHeaders.ACCEPT, amsWebClientConfigData.getAcceptType())
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .codecs(clientCodecConfigurer ->
                        clientCodecConfigurer
                                .defaultCodecs()
                                .maxInMemorySize(amsWebClientConfigData.getMaxInMemorySize()));
    }

    private HttpClient getHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, amsWebClientConfigData.getConnectTimeoutMs())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(amsWebClientConfigData.getReadTimeoutMs(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(
							new WriteTimeoutHandler(amsWebClientConfigData.getWriteTimeoutMs(), TimeUnit.MILLISECONDS));
				});
	}
 
}
