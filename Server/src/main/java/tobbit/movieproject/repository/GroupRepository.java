package tobbit.movieproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tobbit.movieproject.model.Group;
import tobbit.movieproject.model.User;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
    List<Group> findByCreatedBy(String currentUser);
    List<Group> findByMembers(List<User> members);
}
