package co.com.pragma.usecase.generateperformancereport.inport;

import reactor.core.publisher.Mono;

public interface GeneratePerformanceReportUseCaseInPort {
    Mono<Void> execute();
}
