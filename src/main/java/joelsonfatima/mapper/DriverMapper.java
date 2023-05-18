package joelsonfatima.mapper;

import joelsonfatima.dto.request.DriverDtoRequest;
import joelsonfatima.dto.response.DriverDtoResponse;
import joelsonfatima.dto.response.DriverUserDtoResponse;
import joelsonfatima.entity.Driver;
import joelsonfatima.entity.DriverTripDtoResponse;
import joelsonfatima.entity.Trip;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {
    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);
    Driver driverDtoRequestToDriver(DriverDtoRequest driverDtoRequest);
    DriverDtoResponse driverToDriverDtoResponse(Driver driver);
    @Mappings
    ({
            @Mapping(target = "userId", source = "driver.user.id"),
            @Mapping(target = "username", source = "driver.user.username"),
            @Mapping(target = "roleName", source = "driver.user.role.name")
    })
    DriverUserDtoResponse driverToDriverUserDtoResponse(Driver driver);
    DriverTripDtoResponse tripToDriverTripDtoResponse(Trip trip);
}
