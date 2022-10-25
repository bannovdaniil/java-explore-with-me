package ru.practicum.ewm.mapper;

import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.dto.requests.RequestOutDto;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {


    public static RequestOutDto requestToOutDto(Request request) {
        return new RequestOutDto(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }

    public static List<RequestOutDto> requestsToListOutDto(List<Request> requests) {
        List<RequestOutDto> requestOutDtoList = new ArrayList<>();
        for (Request request : requests) {
            requestOutDtoList.add(requestToOutDto(request));
        }
        return requestOutDtoList;
    }
}
