import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MovieService } from 'src/app/provider/movie/movie.service';
import { FormControl } from '@angular/forms';
declare var $ : any;
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  searchResult: string[] = [];
  topRatedMovies: any;
  myControl = new FormControl();
  userInput: any;

  constructor(private _router: Router, private _movieService: MovieService) { 
    this.highRatedMovies();
  }

  ngOnInit() {
  }

  searchMovies(userInput) {
    if (userInput.length >= 2) {
      this._movieService.fetchMoviesByTitle(userInput).subscribe(res => {
        this.searchResult = res.json();
      })
    }
  }

  clearOptions(userInput) {
    if (userInput.length < 2) {
      this.searchResult = [];
    }
  }

  highRatedMovies() {
    this._movieService.getHighRatedMovies().subscribe(res => {
      this.topRatedMovies = res.json();
    });
  }


  movieDetail(selectedMovie) {
    this._router.navigateByData({
      url: ['/movie', selectedMovie.imdbID],
      data: [selectedMovie]
    });
    localStorage.setItem('selectedMovie', JSON.stringify(selectedMovie));
  }

  showPlot() {
    $('.card-reveal').show();
  }

  hidePlot() {
    $('.card-reveal').hide();
  }

  logout() {
    this._router.navigateByData({
      url: ["/login"],
      data: null
    });
  }
}
