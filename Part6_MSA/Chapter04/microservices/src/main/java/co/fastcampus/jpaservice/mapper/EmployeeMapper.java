package co.fastcampus.jpaservice.mapper;

import co.fastcampus.jpaservice.dto.EmployeeDTO;
import co.fastcampus.jpaservice.entity.Employee;
import co.fastcampus.jpaservice.mapper.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends EntityMapper<Employee, EmployeeDTO> {
}
