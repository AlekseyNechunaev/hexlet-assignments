package exercise.service;

import exercise.entity.People;

import java.util.List;

public interface PeopleService {

    List<People> findAll();

    People findById(Long id);

    void save(People people);

}
