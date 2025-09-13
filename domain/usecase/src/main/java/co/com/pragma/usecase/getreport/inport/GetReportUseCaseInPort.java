package co.com.pragma.usecase.getreport.inport;

import co.com.pragma.model.report.Report;
import reactor.core.publisher.Mono;

public interface GetReportUseCaseInPort {
    Mono<Report> execute();
}
