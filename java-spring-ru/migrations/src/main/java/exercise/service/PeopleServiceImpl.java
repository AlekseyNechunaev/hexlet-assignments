package exercise.service;

import exercise.entity.People;
import exercise.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public List<People> findAll() {
        return peopleRepository.findAll();
    }

    @Override
    public People findById(Long id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Override
    public void save(People people) {
        peopleRepository.save(people);
    }
}
