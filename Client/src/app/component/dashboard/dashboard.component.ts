import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MovieService } from 'src/app/provider/movie/movie.service';
import { FormControl } from '@angular/forms';
import { CLIENT_ID } from 'src/app/constants/constants';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  searchResult: string[] = [];
  topRatedMovies: any;
  topTvSeries: any;
  topChildrenMovies: any;
  myControl = new FormControl();
  userInput: any;
  logoutInterval: any;

  constructor(private _router: Router, private _movieService: MovieService) { }

  ngOnInit() {
    this.loadGapi();
    this.highRatedMovies();
    this.highRatedTvSeries();
    this.highRatedChildrenMovies();
    this.logoutInterval = setInterval(() => {
      this.logoutDueInactivity();
    }, 1800000);
  }

  loadGapi() {
    gapi.load('auth2', function () {
      console.log("GAPI LOADED");
      gapi.auth2.init({
        client_id: CLIENT_ID,
        scope: "https://www.googleapis.com/auth/calendar.events"
      });
    });
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

  highRatedTvSeries() {
    this._movieService.getTopTvSeries().subscribe(res => {
      this.topTvSeries = res.json();
    });
  }

  highRatedChildrenMovies() {
    this._movieService.getChildrenMovies().subscribe(res => {
      this.topChildrenMovies = res.json();
    });
  }

  movieDetail(selectedMovie) {
    this._router.navigateByData({
      url: ['/movie', selectedMovie.imdbID],
      data: [selectedMovie]
    });
    localStorage.setItem('selectedMovie', JSON.stringify(selectedMovie));
  }

  logout() {
    gapi.auth2.getAuthInstance().signOut().then(this.redirectToLogin());
    localStorage.clear();
  }

  logoutDueInactivity() {
    localStorage.clear();
  }

  redirectToMyPage() {
    this._router.navigateByData({
      url: ['/user', localStorage.getItem('currentUser')],
      data: 0
    });
  }

  redirectToLogin() {
    console.log('User logged out.');
    this._router.navigateByData({
      url: ['/login'],
      data: 0
    });
  }

}
