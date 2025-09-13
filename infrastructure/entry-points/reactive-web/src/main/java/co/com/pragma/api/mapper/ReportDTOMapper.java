package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.ReportDTO;
import co.com.pragma.model.report.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ReportDTOMapper {
    ReportDTO toResponse(Report report);
}
