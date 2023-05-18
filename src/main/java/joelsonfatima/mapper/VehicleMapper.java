package joelsonfatima.mapper;

import joelsonfatima.dto.request.VehicleDtoRequest;
import joelsonfatima.dto.response.*;
import joelsonfatima.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);
    Vehicle vehicleDtoRequestToVehicle(VehicleDtoRequest vehicleDtoRequest);
    VehicleDtoResponse vehicleToVehicleDtoResponse(Vehicle vehicle);
    VehicleAllFieldsDtoResponse toVehicleAllFieldsDtoResponse(Vehicle vehicle);
    VehicleGrisCardDtoResponse toVehicleGrisCardDtoResponse(Vehicle vehicle);
    VehicleInsuranceDtoResponse toVehicleInsuranceDtoResponse(Vehicle vehicle);
    VehicleTechnicalVisitDtoResponse toVehicleTechnicalVisitDtoResponse(Vehicle vehicle);
    VehicleVignetteDtoResponse toVehicleVignetteDtoResponse(Vehicle vehicle);
    VehicleWithInsuranceDtoResponse toVehicleWithInsuranceDtoResponse(Vehicle vehicle);
}
