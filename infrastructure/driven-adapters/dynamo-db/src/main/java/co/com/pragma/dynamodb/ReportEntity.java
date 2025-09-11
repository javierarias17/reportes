package co.com.pragma.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class ReportEntity {

    private String id;
    private Long atr1;

    public ReportEntity() {
    }

    public ReportEntity(String id, Long atr1) {
        this.id = id;
        this.atr1 = atr1;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("counter")
    public Long getAtr1() {
        return atr1;
    }

    public void setAtr1(Long atr1) {
        this.atr1 = atr1;
    }
}
