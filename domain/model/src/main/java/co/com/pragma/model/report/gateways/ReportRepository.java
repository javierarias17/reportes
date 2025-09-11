package co.com.pragma.model.report.gateways;

import co.com.pragma.model.report.Report;
import reactor.core.publisher.Mono;

public interface ReportRepository {

    Mono<Report> incrementApprovedLoansCounter(Report report);
    Mono<Long> getApprovedLoansCount();
}
