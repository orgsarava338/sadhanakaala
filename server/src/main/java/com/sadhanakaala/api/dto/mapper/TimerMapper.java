package com.sadhanakaala.api.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.sadhanakaala.api.dto.request.TimerDTO;
import com.sadhanakaala.api.dto.response.Timer;
import com.sadhanakaala.api.dto.response.TimerListItem;
import com.sadhanakaala.persistence.entity.TimerEntity;

@Mapper(componentModel = "spring")
public interface TimerMapper {

    TimerEntity toEntity(TimerDTO dto);

    Timer toResponse(TimerEntity entity);

    List<TimerListItem> toListItemResponse(List<TimerEntity> entities);
}
