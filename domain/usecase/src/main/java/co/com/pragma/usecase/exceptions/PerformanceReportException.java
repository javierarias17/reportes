package co.com.pragma.usecase.exceptions;

import lombok.Getter;

@Getter
public class PerformanceReportException extends BusinessException {
    
    public PerformanceReportException(String message) {
        super(message);
    }
    
    public PerformanceReportException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
