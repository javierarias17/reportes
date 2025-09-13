package co.com.pragma.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class ReportEntity {

    private String id;
    private Long totalLoansCount;
    private BigDecimal totalLoanAmount;


    public ReportEntity() {
    }

    public ReportEntity(String id, Long totalLoansCount, BigDecimal totalLoanAmount) {
        this.id = id;
        this.totalLoansCount = totalLoansCount;
        this.totalLoanAmount = totalLoanAmount;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("totalLoansCount")
    public Long getTotalLoansCount() {
        return totalLoansCount;
    }

    public void setTotalLoansCount(Long totalLoansCount) {
        this.totalLoansCount = totalLoansCount;
    }

    @DynamoDbAttribute("totalLoanAmount")
    public BigDecimal getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }
}
