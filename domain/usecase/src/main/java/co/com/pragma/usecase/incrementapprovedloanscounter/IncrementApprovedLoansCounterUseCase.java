package co.com.pragma.usecase.incrementapprovedloanscounter;

import co.com.pragma.model.report.Report;
import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.usecase.incrementapprovedloanscounter.inport.IncrementApprovedLoansCounterUseCaseInPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class IncrementApprovedLoansCounterUseCase implements IncrementApprovedLoansCounterUseCaseInPort {

    private final ReportRepository reportRepository;

    @Override
    public Mono<Void> execute(Report report) {
        return reportRepository.incrementApprovedLoansCounter(report).then();
    }
}
