package co.com.pragma.consumer;

import co.com.pragma.consumer.dto.GetAdminEmailsOutDTO;
import co.com.pragma.model.outport.AuthenticationGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements AuthenticationGateway {

    private final WebClient client;
    public static final String ADMIN_EMAILS = "/api/v1/usuarios/admin/emails";

    @Override
    public Mono<List<String>> getAdminEmails() {
        return client
                .get()
                .uri(ADMIN_EMAILS)
                .retrieve()
                .bodyToMono(GetAdminEmailsOutDTO.class)
                .map(response -> response.lstAdminEmails());
    }
}
