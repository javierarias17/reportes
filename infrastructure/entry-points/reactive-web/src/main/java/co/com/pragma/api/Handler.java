package co.com.pragma.api;

import co.com.pragma.api.mapper.ReportDTOMapper;
import co.com.pragma.usecase.getreport.inport.GetReportUseCaseInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final GetReportUseCaseInPort getReportUseCaseInPort;
    private final ReportDTOMapper reportDTOMapper;

    @PreAuthorize("hasAuthority(T(co.com.pragma.api.security.Role).ADMINISTRATOR.code)")
    public Mono<ServerResponse> listenGetReport(ServerRequest serverRequest) {
            return getReportUseCaseInPort.execute()
                    .map(reportDTOMapper::toResponse).flatMap(dto->ServerResponse.status(HttpStatus.OK).bodyValue(dto));
    }
}