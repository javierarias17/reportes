package co.com.pragma.usecase.incrementapprovedloanscounter.inport;

import co.com.pragma.model.report.Report;
import reactor.core.publisher.Mono;

public interface IncrementApprovedLoansCounterUseCaseInPort {
    Mono<Void> execute(Report report);
}
