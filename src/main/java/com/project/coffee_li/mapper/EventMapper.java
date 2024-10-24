package com.project.coffee_li.mapper;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.model.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    /**
     * Converts list of event entities into list of event DTOs.
     *
     * @param eventEntities list of event entities.
     * @return list of event DTOs.
     */
    List<EventDTO> toDTOs(List<EventEntity> eventEntities);

    /**
     * Converts event entity into event dto.
     *
     * @param eventEntity entity.
     * @return event dto.
     */
    EventDTO toDTO(EventEntity eventEntity);

    /**
     * Converts event to create A.K.A. event dto into event entity.
     *
     * @param eventToCreate event to create A.K.A. event dto.
     * @return event entity.
     */
    EventEntity fromDTO(EventDTO eventToCreate);

    /**
     * Update targeted event with updateEventDTO data.
     *
     * @param updateEventDTO      update data.
     * @param targetedEventEntity target event to update.
     */
    void updateEventFromDTO(EventDTO updateEventDTO, @MappingTarget EventEntity targetedEventEntity);
}
