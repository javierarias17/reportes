package co.com.pragma.api;

import co.com.pragma.usecase.gettotalapprovedloans.inport.GetTotalApprovedLoansUseCaseInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final GetTotalApprovedLoansUseCaseInPort getTotalApprovedLoansUseCaseInPort;

    @PreAuthorize("hasAuthority(T(co.com.pragma.api.security.Role).ADMINISTRATOR.code)")
    public Mono<ServerResponse> listenGetTotalApprovedLoans(ServerRequest serverRequest) {
            return getTotalApprovedLoansUseCaseInPort.execute()
                    .flatMap(value->ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.TEXT_PLAIN)
                    .bodyValue(value.toString()));
    }
}
