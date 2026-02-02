package orgsarava338.sadhanakaala.api.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import orgsarava338.sadhanakaala.api.dto.request.TimerSessionDTO;
import orgsarava338.sadhanakaala.api.dto.response.TimerSession;
import orgsarava338.sadhanakaala.api.dto.response.TimerSessionListItem;
import orgsarava338.sadhanakaala.persistence.entity.TimerSessionEntity;

@Mapper(componentModel = "spring")
public interface TimerSessionMapper {

    TimerSessionEntity toEntity(TimerSessionDTO dto);

    TimerSession toResponse(TimerSessionEntity entity);

    List<TimerSessionListItem> toListItemResponse(List<TimerSessionEntity> entities);
}
