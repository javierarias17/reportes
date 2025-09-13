package co.com.pragma.usecase.getreport;

import co.com.pragma.model.report.Report;
import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.usecase.getreport.inport.GetReportUseCaseInPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetReportUseCase implements GetReportUseCaseInPort {

    private final ReportRepository reportRepository;

    @Override
    public Mono<Report> execute() {
        return reportRepository.getApprovedLoansCount();
    }
}
