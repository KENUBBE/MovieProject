package tobbit.movieproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tobbit.movieproject.model.Movie;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    @Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
    List<Movie> findMovieByTitle(String title);
}
