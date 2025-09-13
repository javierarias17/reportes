package co.com.pragma.usecase.updatereport;

import co.com.pragma.usecase.updateReport.UpdateReportUseCase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.pragma.model.report.gateways.ReportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;


import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class UpdateReportUseCaseTest {

    @InjectMocks
    private UpdateReportUseCase updateReportUseCase;

    @Mock
    private ReportRepository reportRepository;

    @Test
    void shouldUpdateReportSuccessfully() {
        BigDecimal amount = new BigDecimal("1500000");
        when(reportRepository.update(amount)).thenReturn(Mono.empty());

        StepVerifier.create(updateReportUseCase.execute(amount))
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenRepositoryFails() {
        BigDecimal amount = new BigDecimal("2000000");
        RuntimeException exception = new RuntimeException("DB error");
        when(reportRepository.update(amount)).thenReturn(Mono.error(exception));

        StepVerifier.create(updateReportUseCase.execute(amount))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("DB error"))
                .verify();
    }
}