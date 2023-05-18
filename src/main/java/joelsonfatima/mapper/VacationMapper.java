package joelsonfatima.mapper;

import joelsonfatima.dto.request.VacationDtoRequest;
import joelsonfatima.dto.response.VacationDtoResponse;
import joelsonfatima.entity.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VacationMapper {
    VacationMapper INSTANCE = Mappers.getMapper(VacationMapper.class);
    Vacation vacationDtoRequestToVacation(VacationDtoRequest vacationDtoRequest);
    @Mapping(target = "driverCin", source = "vacation.driver.cin")
    VacationDtoResponse vacationToVacationDtoResponse(Vacation vacation);
}
