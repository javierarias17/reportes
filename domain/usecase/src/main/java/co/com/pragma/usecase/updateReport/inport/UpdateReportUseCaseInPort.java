package co.com.pragma.usecase.updateReport.inport;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UpdateReportUseCaseInPort {
    Mono<Void> execute(BigDecimal amount);
}
