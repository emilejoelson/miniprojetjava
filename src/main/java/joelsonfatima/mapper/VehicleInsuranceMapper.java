package joelsonfatima.mapper;

import joelsonfatima.dto.request.VehicleInsuranceDtoRequest;
import joelsonfatima.dto.response.VehicleInsuranceDtoResponse;
import joelsonfatima.entity.VehicleInsurance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleInsuranceMapper {
    VehicleInsuranceMapper INSTANCE = Mappers.getMapper(VehicleInsuranceMapper.class);
    VehicleInsurance toEntity(VehicleInsuranceDtoRequest request);
    VehicleInsuranceDtoResponse toDto(VehicleInsurance vehicleInsurance);
}
