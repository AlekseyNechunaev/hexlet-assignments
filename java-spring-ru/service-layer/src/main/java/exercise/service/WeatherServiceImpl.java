package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.client.HttpClient;
import exercise.dto.ExtendedCityInfo;
import exercise.dto.ShortCityDto;
import exercise.exception.CityNotFoundException;
import exercise.model.City;
import exercise.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final CityRepository cityRepository;
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private static final String CLIENT_URL = "http://weather/api/v2/cities/";

    @Autowired
    public WeatherServiceImpl(CityRepository cityRepository, HttpClient client, ObjectMapper objectMapper) {
        this.cityRepository = cityRepository;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public ExtendedCityInfo findInfoById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("city not found"));
        String result = client.get(buildUrl(city.getName()));
        try {
            return objectMapper.readValue(result, ExtendedCityInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShortCityDto> findAllInfo(String cityNameContainsSymbols) {
        List<City> cities;
        if (cityNameContainsSymbols == null) {
            cities = cityRepository.findAllByOrderByNameAsc();
        } else {
            cities = cityRepository.findByNameIsStartingWithIgnoreCase(cityNameContainsSymbols);
            if (cities.isEmpty()) {
                return Collections.emptyList();
            }
        }
        List<ExtendedCityInfo> extendedCityInfos = cities.stream()
                .map(city -> {
                    String result = client.get(buildUrl(city.getName()));
                    try {
                        return objectMapper.readValue(result, ExtendedCityInfo.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

        return extendedCityInfos.stream()
                .map(extendedCityInfo -> new ShortCityDto(
                        extendedCityInfo.getTemperature(),
                        extendedCityInfo.getName()))
                .collect(Collectors.toList());

    }

    private String buildUrl(String cityName) {
        return CLIENT_URL + cityName;
    }
}
