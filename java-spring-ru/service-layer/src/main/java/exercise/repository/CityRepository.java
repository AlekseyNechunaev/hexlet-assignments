package exercise.repository;

import exercise.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByNameIsStartingWithIgnoreCase(String startSymbols);

    List<City> findAllByOrderByNameAsc();
}
