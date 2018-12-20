package tobbit.movieproject.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tobbit.movieproject.model.Movie;
import tobbit.movieproject.service.MovieService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/api/fetchMovieByTitle")
    public ResponseEntity<List<Movie>> getMovieByTitle(@RequestParam String title) {
        return new ResponseEntity<>(movieService.searchMovieByTitle(title), HttpStatus.OK);
    }
}
