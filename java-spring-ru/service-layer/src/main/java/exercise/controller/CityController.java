package exercise.controller;

import exercise.dto.ExtendedCityInfo;
import exercise.dto.ShortCityDto;
import exercise.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CityController {

    private final WeatherService weatherService;

    @Autowired
    public CityController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/search")
    public List<ShortCityDto> findAllInfo(@RequestParam(name = "name", required = false) String name) {
        return weatherService.findAllInfo(name);
    }

    @GetMapping("/cities/{id}")
    public ExtendedCityInfo findInfoById(@PathVariable Long id) {
        System.out.println(id);
        return weatherService.findInfoById(id);
    }
}

