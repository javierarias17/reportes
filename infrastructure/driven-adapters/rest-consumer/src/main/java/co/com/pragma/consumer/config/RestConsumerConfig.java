package co.com.pragma.consumer.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
public class RestConsumerConfig {

    private final String url;
    private final int timeout;
    private static final String INTERNAL_HEADER = "X-Internal-Request";

    public RestConsumerConfig(@Value("${adapter.restconsumer.url}") String url,
                              @Value("${adapter.restconsumer.timeout}") int timeout) {
        this.url = url;
        this.timeout = timeout;
    }

    @Bean("webClient")
    @Primary
    public WebClient getWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .filter((request, next) ->
                        getCurrentToken()
                                .flatMap(token -> {
                                    ClientRequest newRequest = ClientRequest.from(request)
                                            .headers(headers -> {
                                                headers.setBearerAuth(token);
                                                headers.add(INTERNAL_HEADER, "true");
                                            })
                                            .build();
                                    return next.exchange(newRequest);
                                })
                                .switchIfEmpty(
                                        Mono.defer(() -> {
                                            ClientRequest newRequest = ClientRequest.from(request)
                                                    .headers(headers -> {
                                                        headers.add(INTERNAL_HEADER, "true");
                                                    })
                                                    .build();
                                            return next.exchange(newRequest);
                                        })
                                )
                )
                .clientConnector(getClientHttpConnector())
                .build();
    }

    private Mono<String> getCurrentToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getCredentials().toString())
                .onErrorResume(throwable -> {
                    return Mono.empty();
                });
    }

    private ClientHttpConnector getClientHttpConnector() {
        /*
        IF YO REQUIRE APPEND SSL CERTIFICATE SELF SIGNED: this should be in the default cacerts trustore
        */
        return new ReactorClientHttpConnector(HttpClient.create()
                .compress(true)
                .keepAlive(true)
                .option(CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeout, MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeout, MILLISECONDS));
                }));
    }
}
