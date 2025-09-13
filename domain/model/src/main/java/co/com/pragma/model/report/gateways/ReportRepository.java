package co.com.pragma.model.report.gateways;

import co.com.pragma.model.report.Report;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ReportRepository {

    Mono<Report> update(BigDecimal amount);
    Mono<Report> getApprovedLoansCount();
}
