package tasks.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import tasks.model.CategoriesList;

public interface CategoriesRepository extends MongoRepository<CategoriesList, String> {

  Optional<CategoriesList> findByUserId(String userId);

}
