package co.com.pragma.usecase.gettotalapprovedloans;

import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.usecase.gettotalapprovedloans.inport.GetTotalApprovedLoansUseCaseInPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetTotalApprovedLoansUseCase implements GetTotalApprovedLoansUseCaseInPort {

    private final ReportRepository reportRepository;

    @Override
    public Mono<Long> execute() {
        return reportRepository.getApprovedLoansCount();
    }
}
