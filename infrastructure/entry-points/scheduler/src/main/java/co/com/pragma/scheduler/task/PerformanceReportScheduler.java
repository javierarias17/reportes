package co.com.pragma.scheduler.task;

import co.com.pragma.usecase.generateperformancereport.inport.GeneratePerformanceReportUseCaseInPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Component
@RequiredArgsConstructor
public class PerformanceReportScheduler {

    private final GeneratePerformanceReportUseCaseInPort generatePerformanceReportUseCaseInPort;

    @Scheduled(cron = "0 0 20 * * ?")
    public void generateDailyPerformanceReport() {       
        generatePerformanceReportUseCaseInPort.execute()
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(result -> log.info("Performance report generated successfully"))
                .doOnError(error -> log.error("Error generating performance report: {}", error.getMessage(), error))
                .subscribe();
    }
}
