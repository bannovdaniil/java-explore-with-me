package ru.practicum.ewm.utils;

import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.events.EventInDto;
import ru.practicum.ewm.repository.CategoriesRepository;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

public class Utils {
    public static void checkTimeBeforeOrThrow(LocalDateTime eventDate, int hours) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(hours))) {
            throw new InvalidParameterException("Event Time must be, late then " + hours + " hours.");
        }
    }

    public static void setNotNullParamToEntity(EventInDto eventInDto, Event event, CategoriesRepository categoriesRepository)
            throws CategoryNotFoundException {
        if (eventInDto.getCategory() != null) {
            if (!categoriesRepository.existsById(eventInDto.getCategory())) {
                throw new CategoryNotFoundException("Category ID not found.");
            }
            event.setCategory(categoriesRepository.getReferenceById(eventInDto.getCategory()));
        }
        if (eventInDto.getAnnotation() != null) {
            event.setAnnotation(eventInDto.getAnnotation());
        }
        if (eventInDto.getDescription() != null) {
            event.setDescription(eventInDto.getDescription());
        }
        if (eventInDto.getTitle() != null) {
            event.setTitle(eventInDto.getTitle());
        }
        if (eventInDto.getPaid() != null) {
            event.setPaid(eventInDto.getPaid());
        }
        if (eventInDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventInDto.getParticipantLimit());
        }
        if (eventInDto.getRequestModeration() != null) {
            event.setRequestModeration(eventInDto.getRequestModeration());
        }
    }
}
