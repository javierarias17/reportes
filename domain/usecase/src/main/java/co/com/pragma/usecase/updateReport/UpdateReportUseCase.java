package co.com.pragma.usecase.updateReport;

import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.usecase.updateReport.inport.UpdateReportUseCaseInPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UpdateReportUseCase implements UpdateReportUseCaseInPort {

    private final ReportRepository reportRepository;

    @Override
    public Mono<Void> execute(BigDecimal amount) {
        return reportRepository.update(amount).then();
    }
}