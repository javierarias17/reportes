package co.com.pragma.model.report;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Report {
    private String id;
    private Long totalLoansCount;
    private BigDecimal totalLoanAmount;
}
