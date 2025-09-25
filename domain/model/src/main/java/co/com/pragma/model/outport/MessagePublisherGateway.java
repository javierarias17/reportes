package co.com.pragma.model.outport;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface MessagePublisherGateway {
    Mono<String> sendPerformanceReport(Long totalLoansCount, BigDecimal totalLoanAmount, String date, List<String> lstAmdinEmails);
}
