package joelsonfatima.mapper;

import joelsonfatima.dto.request.TripDtoRequest;
import joelsonfatima.dto.response.TripDtoResponse;
import joelsonfatima.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);
    Trip tripDtoRequestToTrio(TripDtoRequest tripDtoRequest);
    @Mappings({
            @Mapping(target = "driverCin", source = "trip.driver.cin"),
            @Mapping(target = "vehicleLicensePlate", source = "trip.vehicle.licensePlate")
    })
    TripDtoResponse tripToTripDtoResponse(Trip trip);
}
