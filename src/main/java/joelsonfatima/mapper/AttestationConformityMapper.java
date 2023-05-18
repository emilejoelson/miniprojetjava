package joelsonfatima.mapper;

import joelsonfatima.dto.request.AttestationConformityDtoRequest;
import joelsonfatima.dto.response.AttestationConformityDtoResponse;
import joelsonfatima.entity.AttestationConformity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttestationConformityMapper {
    AttestationConformityMapper INSTANCE = Mappers.getMapper(AttestationConformityMapper.class);
    AttestationConformity toEntity(AttestationConformityDtoRequest dtoRequest);
    AttestationConformityDtoResponse toDto(AttestationConformity attestationConformity);
}
