package co.com.pragma.usecase.getreport;

import co.com.pragma.model.report.Report;
import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.usecase.getreport.GetReportUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetReportUseCaseTest {

    @InjectMocks
    private GetReportUseCase getReportUseCase;

    @Mock
    private ReportRepository reportRepository;

    @Test
    void shouldReturnReportWhenRepositoryHasData() {
        Report report = new Report();
        report.setTotalLoansCount(5L);
        report.setTotalLoanAmount(new BigDecimal("10000000"));
        when(reportRepository.getApprovedLoansCount()).thenReturn(Mono.just(report));

        StepVerifier.create(getReportUseCase.execute())
                .expectNext(report)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyWhenRepositoryIsEmpty() {
        when(reportRepository.getApprovedLoansCount()).thenReturn(Mono.empty());
        StepVerifier.create(getReportUseCase.execute())
                .verifyComplete();
    }
}