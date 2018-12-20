import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MovieService } from 'src/app/provider/movie/movie.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  allMovies: string[] = [];
  myControl = new FormControl();
  userInput: any;

  constructor(private _router: Router, private _movieService: MovieService) { }

  ngOnInit() {
  }

  getMovies(userInput) {
    if (userInput.length >= 2) {
      this._movieService.fetchMoviesByTitle(userInput).subscribe(res => {
        this.allMovies = res.json();
      })
    }
  }

  clearOptions(userInput) {
    if (userInput.length < 2) {
      this.allMovies = [];
    }
  }

  movieDetail(selectedMovie) {
    this._router.navigateByData({
      url: ['/movie', selectedMovie.imdbID],
      data: [selectedMovie]
    });
    localStorage.setItem('selectedMovie', JSON.stringify(selectedMovie));
  }

  logout() {
    this._router.navigateByData({
      url: ["/login"],
      data: null
    });
  }
}
