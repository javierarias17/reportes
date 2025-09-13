package co.com.pragma.api.dto;

import java.math.BigDecimal;

public record ReportDTO(String id,
     Long totalLoansCount,
     BigDecimal totalLoanAmount
) {
}