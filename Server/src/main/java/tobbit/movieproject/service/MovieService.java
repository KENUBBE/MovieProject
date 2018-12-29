package tobbit.movieproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tobbit.movieproject.model.*;
import tobbit.movieproject.repository.MovieRepository;
import tobbit.movieproject.utils.Constants;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class MovieService implements Constants {

    private final MovieRepository movieRepository;
    private MongoOperations mongoOperations;
    private String requestUrl = "";

    @Autowired
    public MovieService(MovieRepository movieRepository, MongoOperations mongoOperations) {
        this.movieRepository = movieRepository;
        this.mongoOperations = mongoOperations;
    }

    public List<Movie> searchMovieByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        convertURL(title);
        List<Movie> allMovies = new ArrayList<>();
        List<SearchResult> searchResult = Collections.singletonList(restTemplate.getForObject(requestUrl, SearchResult.class));
        for (SearchResult search : searchResult) {
            for (int i = 0; i < search.getSearch().size(); i++) {
                if (search.getSearch().size() <= movieRepository.findMovieByTitle(title).size()) {
                    if (movieRepository.findMovieByTitle(title).get(i).getTitle().toLowerCase().contains(title.toLowerCase())) {
                        allMovies.add(movieRepository.findMovieByTitle(title).get(i));
                    }
                } else {
                    allMovies.add(search.getSearch().get(i));
                    setMovieDescription(search.getSearch().get(i));
                    movieRepository.save(search.getSearch().get(i));
                }
            }
        }
        return allMovies;
    }


    //TODO: Create Array with all values and loop through instead
    public List<Movie> highestRatedMovie() {
        Query query = new Query().with(new Sort(Sort.Direction.DESC, "movieDescription.imdbRating"));
        query.addCriteria(
                new Criteria().orOperator(
                        where("movieDescription.imdbRating").is("8.5").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.6").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.7").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.9").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.0").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.1").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.2").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.3").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.4").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.5").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.6").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.7").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("10.0").and("type").is("movie")
                )
        ).limit(5);

        return mongoOperations.find(query, Movie.class);
    }

    //TODO: Create Array with all values and loop through instead
    public List<Movie> topTvSeries() {
        Query query = new Query().with(new Sort(Sort.Direction.DESC, "movieDescription.imdbRating"));
        query.addCriteria(
                new Criteria().orOperator(
                        where("movieDescription.imdbRating").is("8.5").and("type").is("series"),
                        where("movieDescription.imdbRating").is("8.6").and("type").is("series"),
                        where("movieDescription.imdbRating").is("8.7").and("type").is("series"),
                        where("movieDescription.imdbRating").is("8.9").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.0").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.1").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.2").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.3").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.4").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.5").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.6").and("type").is("series"),
                        where("movieDescription.imdbRating").is("9.7").and("type").is("series"),
                        where("movieDescription.imdbRating").is("10.0").and("type").is("series")
                )
        ).limit(5);

        return mongoOperations.find(query, Movie.class);
    }

    //TODO: Create Array with all values and loop through instead
    public List<Movie> childrenMovie() {
        Query query = new Query().with(new Sort(Sort.Direction.DESC, "movieDescription.imdbRating"));
        query.addCriteria(
                new Criteria().orOperator(
                        where("movieDescription.imdbRating").is("8.0").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.1").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.2").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.3").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.4").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.5").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.6").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.7").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.8").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("8.9").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.0").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.1").and("movieDescription.genre").regex("Animation").and("type").is("movie"),
                        where("movieDescription.imdbRating").is("9.2").and("movieDescription.genre").regex("Animation").and("type").is("movie")
                )
        ).limit(5);

        return mongoOperations.find(query, Movie.class);
    }

    private void setMovieDescription(Movie movie) {
        RestTemplate restTemplate = new RestTemplate();
        convertIdSearchURL(movie.getImdbID());
        MovieDescription movieDescription = restTemplate.getForObject(requestUrl, MovieDescription.class);
        movie.setMovieDescription(movieDescription);
    }

    private void convertURL(String title) {
        title = URLEncoder.encode(title, StandardCharsets.UTF_8);
        requestUrl = OMDB_SEARCH_URL
                .replaceAll("TITLE", title);
    }

    private void convertIdSearchURL(String imdbID) {
        requestUrl = OMDB_ID_SEARCH_URL
                .replaceAll("IMDBID", imdbID);
    }
}