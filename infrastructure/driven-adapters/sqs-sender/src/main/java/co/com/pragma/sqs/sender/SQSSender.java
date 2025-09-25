package co.com.pragma.sqs.sender;

import co.com.pragma.model.outport.MessagePublisherGateway;
import co.com.pragma.sqs.sender.config.SQSSenderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements MessagePublisherGateway {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;

    public static final String DAILY_PERFORMANCE_REPORT_QUEUE = "/dailyPerformanceReportQueue";

    public Mono<String> send(String message, String url) {
        return Mono.fromCallable(() -> buildRequest(message, url))
                .doOnNext(request -> log.info("Sending message to SQS. QueueUrl={}, Body={}",
                        request.queueUrl(), request.messageBody()))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.info("Sending message successfully. MessageId={}",
                        response.messageId()))
                .doOnError(error -> log.error("Error to sending message to SQS", error))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message, String url) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl()+url)
                .messageBody(message)
                .build();
    }

    @Override
    public Mono<String> sendPerformanceReport(Long totalLoansCount, BigDecimal totalLoanAmount, String date, List<String> lstAdminEmails ) {
        String emailsJson = lstAdminEmails.stream()
                .map(email -> "\"" + email + "\"")
                .collect(Collectors.joining(", ", "[", "]"));

        return send(String.format("{\"totalLoansCount\": \"%s\", \"totalLoanAmount\": \"%.2f\", \"date\": \"%s\", \"emails\": %s}",
                totalLoansCount, totalLoanAmount, date, emailsJson), DAILY_PERFORMANCE_REPORT_QUEUE);
    }
}
