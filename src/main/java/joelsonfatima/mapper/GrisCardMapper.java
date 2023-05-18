package joelsonfatima.mapper;

import joelsonfatima.dto.request.GrisCardDtoRequest;
import joelsonfatima.dto.response.GrisCardDtoResponse;
import joelsonfatima.entity.GrisCard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GrisCardMapper {
    GrisCardMapper INSTANCE = Mappers.getMapper(GrisCardMapper.class);
    GrisCard grisCardDtoRequestToGrisCard(GrisCardDtoRequest grisCardDtoRequest);
    GrisCardDtoResponse grisCardToGrisCardDtoResponse(GrisCard grisCard);
}
