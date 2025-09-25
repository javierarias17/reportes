package co.com.pragma.usecase.generateperformancereport;

import co.com.pragma.model.outport.AuthenticationGateway;
import co.com.pragma.model.outport.MessagePublisherGateway;
import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.usecase.exceptions.PerformanceReportException;
import co.com.pragma.usecase.generateperformancereport.inport.GeneratePerformanceReportUseCaseInPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class GeneratePerformanceReportUseCase implements GeneratePerformanceReportUseCaseInPort {

    private final ReportRepository reportRepository;
    private final AuthenticationGateway authenticationGateway;
    private final MessagePublisherGateway messagePublisherGateway;

    @Override
    public Mono<Void> execute() {
        return authenticationGateway.getAdminEmails()
                .flatMap(emails -> reportRepository.getApprovedLoansCount()
                        .flatMap(report -> messagePublisherGateway.sendPerformanceReport(
                                report.getTotalLoansCount(), 
                                report.getTotalLoanAmount(),
                                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                emails)))
                .onErrorMap(error -> new PerformanceReportException(error.getMessage(), error))
                .then();
    }
}
