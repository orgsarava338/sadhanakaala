package orgsarava338.sadhanakaala.api.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import orgsarava338.sadhanakaala.api.dto.request.TimerDTO;
import orgsarava338.sadhanakaala.api.dto.response.Timer;
import orgsarava338.sadhanakaala.api.dto.response.TimerListItem;
import orgsarava338.sadhanakaala.persistence.entity.TimerEntity;

@Mapper(componentModel = "spring")
public interface TimerMapper {

    TimerEntity toEntity(TimerDTO dto);

    Timer toResponse(TimerEntity entity);

    List<TimerListItem> toListItemResponse(List<TimerEntity> entities);
}
