package tobbit.movieproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tobbit.movieproject.model.*;
import tobbit.movieproject.repository.MovieRepository;
import tobbit.movieproject.utils.Constants;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class MovieService implements Constants {

    private final MovieRepository movieRepository;
    private String requestUrl = "";

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
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
                    movieRepository.save(search.getSearch().get(i));
                }
            }
        }
        return allMovies;
    }

    private void convertURL(String title) {
        title = URLEncoder.encode(title, StandardCharsets.UTF_8);
        requestUrl = OMDB_SEARCH_URL
                .replaceAll("TITLE", title);
    }
}