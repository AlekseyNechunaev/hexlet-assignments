package exercise.controller;

import exercise.common.utils.Mapper;
import exercise.dto.AddPeopleRequestDto;
import exercise.entity.People;
import exercise.service.PeopleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PeopleController.PATH)
public class PeopleController {

    public static final String PATH = "/people";
    private final Mapper mapper;
    private final PeopleService peopleService;

    public PeopleController(Mapper mapper, PeopleService peopleService) {
        this.mapper = mapper;
        this.peopleService = peopleService;
    }

    @PostMapping
    public void createPerson(@RequestBody AddPeopleRequestDto addPeopleRequestDto) {
        peopleService.save(mapper.mapToEntity(addPeopleRequestDto));
    }

    @GetMapping
    public List<People> findAll() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public People findById(@PathVariable Long id) {
        return peopleService.findById(id);
    }

}
