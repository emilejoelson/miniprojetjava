package joelsonfatima.mapper;

import joelsonfatima.dto.request.TechnicalVisitDtoRequest;
import joelsonfatima.dto.response.TechnicalVisitDtoResponse;
import joelsonfatima.entity.TechnicalVisit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TechnicalVisitMapper {
    TechnicalVisitMapper INSTANCE = Mappers.getMapper(TechnicalVisitMapper.class);
    TechnicalVisit visitDtoRequestToTechnicalVisit(TechnicalVisitDtoRequest visitDtoRequest);
    @Mappings({
            @Mapping(target = "vehicleLicensePlate", source = "technicalVisit.vehicle_technical_visit.licensePlate"),
            @Mapping(target = "attestationId", source = "technicalVisit.attestation.id"),
            @Mapping(target = "description", source = "technicalVisit.attestation.description")
    })
    TechnicalVisitDtoResponse visitToTechnicalVisitDtoResponse(TechnicalVisit technicalVisit);
}
