package ru.practicum.ewm.mapper;

import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.dto.requests.RequestOutDto;

public class RequestMapper {


    public static RequestOutDto requestToDto(Request request) {
        return new RequestOutDto(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }
}
