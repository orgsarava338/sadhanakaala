package com.sadhanakaala.api.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.sadhanakaala.api.dto.request.TimerSessionDTO;
import com.sadhanakaala.api.dto.response.TimerSession;
import com.sadhanakaala.api.dto.response.TimerSessionListItem;
import com.sadhanakaala.persistence.entity.TimerSessionEntity;

@Mapper(componentModel = "spring")
public interface TimerSessionMapper {

    TimerSessionEntity toEntity(TimerSessionDTO dto);

    TimerSession toResponse(TimerSessionEntity entity);

    List<TimerSessionListItem> toListItemResponse(List<TimerSessionEntity> entities);
}
