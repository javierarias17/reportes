package co.com.pragma.dynamodb;

import co.com.pragma.dynamodb.helper.TemplateAdapterOperations;
import co.com.pragma.model.report.Report;
import co.com.pragma.model.report.gateways.ReportRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<Report, String, ReportEntity>  implements ReportRepository {

    private static final String APPROVED_LOANS_ID="approvedLoans";

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, Report.class), "Report" /*index is optional*/);
    }

    public Mono<List<Report>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<Report>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return queryByIndex(queryExpression, "secondary_index" /*index is optional if you define in constructor*/);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
                .build();
    }

    @Override
    public Mono<Report> update(BigDecimal amount) {
        return getById(APPROVED_LOANS_ID)
                .flatMap(existing -> {
                    existing.setTotalLoansCount(existing.getTotalLoansCount() + 1);
                    existing.setTotalLoanAmount(existing.getTotalLoanAmount().add(amount));
                    return save(existing);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    Report report = new Report();
                    report.setId(APPROVED_LOANS_ID);
                    report.setTotalLoansCount(1L);
                    report.setTotalLoanAmount(amount);
                    return save(report);
                }));
    }

    @Override
    public Mono<Report> getApprovedLoansCount() {
        return getById(APPROVED_LOANS_ID)
                .switchIfEmpty(Mono.defer(() -> {
                    Report newReport = new Report();
                    newReport.setId(APPROVED_LOANS_ID);
                    newReport.setTotalLoanAmount(BigDecimal.ZERO);
                    newReport.setTotalLoansCount(0L);
                    return Mono.just(newReport);
                }));
    }
}
