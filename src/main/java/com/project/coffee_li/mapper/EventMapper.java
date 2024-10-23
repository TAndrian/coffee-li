package com.project.coffee_li.mapper;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.model.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    List<EventDTO> toDTOs(List<EventEntity> eventEntities);
}
