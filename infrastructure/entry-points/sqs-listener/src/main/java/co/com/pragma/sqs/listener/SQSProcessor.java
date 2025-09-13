package co.com.pragma.sqs.listener;

import co.com.pragma.usecase.updateReport.inport.UpdateReportUseCaseInPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final UpdateReportUseCaseInPort updateReportUseCaseInPort;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> apply(Message message) {
        try {
            log.info("Listening message. {}",message.body());
            JsonNode json = objectMapper.readTree(message.body());
            BigDecimal amount = new BigDecimal(json.get("amount").asText());
            return updateReportUseCaseInPort.execute(amount);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
