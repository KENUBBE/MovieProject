package tobbit.movieproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tobbit.movieproject.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
