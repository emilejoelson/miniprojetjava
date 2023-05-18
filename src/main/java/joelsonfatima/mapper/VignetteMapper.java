package joelsonfatima.mapper;

import joelsonfatima.dto.request.VignetteDtoRequest;
import joelsonfatima.dto.response.VignetteDtoResponse;
import joelsonfatima.entity.Vignette;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VignetteMapper {
    VignetteMapper INSTANCE = Mappers.getMapper(VignetteMapper.class);
    Vignette vignetteDtoRequestToVignette(VignetteDtoRequest vignetteDtoRequest);
    VignetteDtoResponse vignetteToVignetteDtoResponse(Vignette vignette);
}
