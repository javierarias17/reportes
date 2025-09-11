package co.com.pragma.sqs.listener;

import co.com.pragma.model.report.Report;
import co.com.pragma.usecase.incrementapprovedloanscounter.inport.IncrementApprovedLoansCounterUseCaseInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final IncrementApprovedLoansCounterUseCaseInPort getTotalApprovedLoansUseCaseInPort;

    @Override
    public Mono<Void> apply(Message message) {
        System.out.println(message.body());
        Report report=new Report("approvedLoans", 1L);
        return getTotalApprovedLoansUseCaseInPort.execute(report);
    }
}
