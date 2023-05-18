package joelsonfatima.mapper;

import joelsonfatima.dto.request.DriverLicenseDtoRequest;
import joelsonfatima.dto.response.DriverLicenseDtoResponse;
import joelsonfatima.entity.DriverLicense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverLicenseMapper {
    DriverLicenseMapper INSTANCE = Mappers.getMapper(DriverLicenseMapper.class);
    DriverLicense licenseDtoRequestToDriverLicense(DriverLicenseDtoRequest licenseDto);
    @Mapping(target = "driverCin", source = "license.driver.cin")
    DriverLicenseDtoResponse licenseToDriverLicenseDtoResponse(DriverLicense license);
}
