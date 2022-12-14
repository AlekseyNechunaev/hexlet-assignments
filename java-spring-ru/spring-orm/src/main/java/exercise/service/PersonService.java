package exercise.service;

import exercise.model.Person;

public interface PersonService {

    Iterable<Person> findAll();

    Person findById(Long id);

    void save(Person person);

    void update(Long id, Person otherPerson);

    void delete(Long id);
}
