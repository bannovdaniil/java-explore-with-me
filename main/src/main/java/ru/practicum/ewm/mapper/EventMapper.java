package ru.practicum.ewm.mapper;

import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.events.EventInDto;
import ru.practicum.ewm.model.dto.events.EventOutDto;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static Event dtoInToEvent(EventInDto eventInDto, Category category) {
        return Event.builder()
                .annotation(eventInDto.getAnnotation())
                .category(category)
                .description(eventInDto.getDescription())
                .location(LocationMapper.dtoToLocation(eventInDto.getLocation()))
                .title(eventInDto.getTitle())
                .eventDate(eventInDto.getEventDate())
                .paid(eventInDto.getPaid())
                .participantLimit(eventInDto.getParticipantLimit())
                .requestModeration(eventInDto.getRequestModeration())
                .build();
    }

    public static EventOutDto eventToOutDto(Event event) {
        return EventOutDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.categoryToDtoOut(event.getCategory()))
                .initiator(UserMapper.userToDto(event.getInitiator()))
                .location(LocationMapper.locationToDto(event.getLocation()))
                .title(event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .views(event.getViews())
                .build();
    }

    public static List<EventOutDto> eventToListOutDto(List<Event> listEvents) {
        List<EventOutDto> eventOutDtos = new ArrayList<>();
        for (Event event : listEvents) {
            eventOutDtos.add(eventToOutDto(event));
        }
        return eventOutDtos;
    }
}
