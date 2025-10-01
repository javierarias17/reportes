package co.com.pragma.usecase.generateperformancereport;

import co.com.pragma.model.outport.AuthenticationGateway;
import co.com.pragma.model.outport.MessagePublisherGateway;
import co.com.pragma.model.report.Report;
import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.usecase.exceptions.PerformanceReportException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneratePerformanceReportUseCaseTest {

    private static final Long TOTAL_LOANS_COUNT = 25L;
    private static final BigDecimal TOTAL_LOAN_AMOUNT = new BigDecimal("50000000");
    private static final String ADMIN_EMAIL_1 = "javierarias17.dll@gmail.com";
    private static final String EXPECTED_DATE_FORMAT = "dd/MM/yyyy";

    @InjectMocks
    private GeneratePerformanceReportUseCase generatePerformanceReportUseCase;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private AuthenticationGateway authenticationGateway;

    @Mock
    private MessagePublisherGateway messagePublisherGateway;

    @Test
    void shouldExecuteSuccessfullyWhenAllOperationsSucceed() {
        List<String> adminEmails = List.of(ADMIN_EMAIL_1);
        Report report = new Report();
        report.setTotalLoansCount(TOTAL_LOANS_COUNT);
        report.setTotalLoanAmount(TOTAL_LOAN_AMOUNT);

        when(authenticationGateway.getAdminEmails()).thenReturn(Mono.just(adminEmails));
        when(reportRepository.getApprovedLoansCount()).thenReturn(Mono.just(report));
        when(messagePublisherGateway.sendPerformanceReport(
                report.getTotalLoansCount(), 
                report.getTotalLoanAmount(),
                LocalDate.now().format(DateTimeFormatter.ofPattern(EXPECTED_DATE_FORMAT)),
                adminEmails))
                .thenReturn(Mono.empty());

        StepVerifier.create(generatePerformanceReportUseCase.execute())
                .verifyComplete();
    }

    @Test
    void shouldPropagateAuthenticationGatewayError() {
        when(authenticationGateway.getAdminEmails()).thenReturn(Mono.error(new RuntimeException("Authentication service unavailable")));

        StepVerifier.create(generatePerformanceReportUseCase.execute())
                .expectErrorMatches(PerformanceReportException.class::isInstance)
                .verify();
    }

    @Test
    void shouldPropagateReportRepositoryError() {
        List<String> adminEmails = List.of(ADMIN_EMAIL_1);
        when(authenticationGateway.getAdminEmails()).thenReturn(Mono.just(adminEmails));
        when(reportRepository.getApprovedLoansCount()).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        StepVerifier.create(generatePerformanceReportUseCase.execute())
                .expectErrorMatches(PerformanceReportException.class::isInstance)
                .verify();
    }

    @Test
    void shouldPropagateMessagePublisherGatewayError() {
        List<String> adminEmails = List.of(ADMIN_EMAIL_1);
        Report report = new Report();
        report.setTotalLoansCount(TOTAL_LOANS_COUNT);
        report.setTotalLoanAmount(TOTAL_LOAN_AMOUNT);

        when(authenticationGateway.getAdminEmails()).thenReturn(Mono.just(adminEmails));
        when(reportRepository.getApprovedLoansCount()).thenReturn(Mono.just(report));
        when(messagePublisherGateway.sendPerformanceReport(
                report.getTotalLoansCount(), 
                report.getTotalLoanAmount(),
                LocalDate.now().format(DateTimeFormatter.ofPattern(EXPECTED_DATE_FORMAT)),
                adminEmails))
                .thenReturn(Mono.error(new RuntimeException("Queue service unavailable")));

        StepVerifier.create(generatePerformanceReportUseCase.execute())
                .expectErrorMatches(PerformanceReportException.class::isInstance)
                .verify();
    }
}