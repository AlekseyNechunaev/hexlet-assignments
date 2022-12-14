package exercise.controller;

import exercise.common.utils.Mapper;
import exercise.dto.PersonDto;
import exercise.model.Person;
import exercise.service.PersonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PeopleController.PATH)
public class PeopleController {

    public static final String PATH = "/people";
    private final PersonService personService;
    private final Mapper mapper;

    public PeopleController(PersonService personService, Mapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.findById(id);
    }

    @GetMapping(path = "")
    public Iterable<Person> getPeople() {
        return personService.findAll();
    }

    @PostMapping
    public void createPerson(@RequestBody PersonDto personDto) {
        personService.save(mapper.mapToEntity(personDto));
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody PersonDto personDto) {
        personService.update(id, mapper.mapToEntity(personDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }
}
