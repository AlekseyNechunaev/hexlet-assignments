package exercise.service;

import exercise.dto.ExtendedCityInfo;
import exercise.dto.ShortCityDto;

import java.util.List;

public interface WeatherService {

    ExtendedCityInfo findInfoById(Long id);

    List<ShortCityDto> findAllInfo(String cityNameContainsSymbols);

}
