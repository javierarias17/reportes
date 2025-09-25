package co.com.pragma.model.outport;

import reactor.core.publisher.Mono;

import java.util.List;

public interface AuthenticationGateway {
    Mono<List<String>> getAdminEmails();
}
