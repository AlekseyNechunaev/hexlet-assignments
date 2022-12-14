package exercise.service;

import exercise.handlers.PersonNotFoundException;
import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(() ->
                new PersonNotFoundException("person with id = " + id + "not found"));
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void update(Long id, Person otherPerson) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new PersonNotFoundException("person with id = " + id + "not found"));
        person.setFirstName(otherPerson.getFirstName());
        person.setLastName(otherPerson.getLastName());
        personRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
