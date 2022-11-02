package exercise.repository;

import exercise.domain.Category;
import exercise.domain.query.QCategory;

import java.util.List;
import java.util.Optional;

public class CategoryRepository {

    public List<Category> getAllCategories() {
        return new QCategory()
                .findList();
    }

    public Optional<Category> getCategoryByID(Long id) {
        return new QCategory()
                .id.eq(id)
                .findOneOrEmpty();
    }
}
