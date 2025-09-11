package co.com.pragma.usecase.gettotalapprovedloans.inport;

import reactor.core.publisher.Mono;

public interface GetTotalApprovedLoansUseCaseInPort {
    Mono<Long> execute();
}
