package ru.practicum.ewm.mapper;

import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.dto.locations.LocationDto;

public class LocationMapper {

    public static LocationDto locationToDto(Location location) {
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }

    public static Location dtoToLocation(LocationDto locationDto) {
        return new Location(
                null,
                locationDto.getLat(),
                locationDto.getLon()
        );
    }
}
