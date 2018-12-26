import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { CalendarService } from 'src/app/provider/calendar/calendar.service';
import { MovieService } from 'src/app/provider/movie/movie.service';

@Component({
  selector: 'app-moviepage',
  templateUrl: './moviepage.component.html',
  styleUrls: ['./moviepage.component.css']
})
export class MoviepageComponent implements OnInit, OnDestroy {

  selectedMovie: any;
  movieDescription: any[] = [];

  constructor(private _router: Router, private _calendarService: CalendarService, private _movieService : MovieService) {
    this.selectedMovie = JSON.parse(localStorage.getItem('selectedMovie'));
   }

  ngOnInit() {
    this.fetchMovieDescription(this.selectedMovie.imdbID);
  }

  ngOnDestroy() {
    this.movieDescription = [];
    localStorage.removeItem("selectedMovie");
  }

  goBack() {
    this._router.navigateByData({
      url: ['/dashboard'],
      data: 0
    });
  }

  addEvent(event) {
    let newEvent = {
      'summary': 'Tonights scheduled movie: ' + this.selectedMovie.Title,
      'startDate': event.value,
      'createdBy': localStorage.getItem("currentUser")
    };
    this._calendarService.createEvent(newEvent).subscribe(res => console.log(res));
  }

  fetchMovieDescription(imdbID){
    this._movieService.fetchMovieDescription(imdbID).subscribe(res => this.movieDescription.push(res.json()));
  }

  showOnImdb() {
    window.location.href = `https://www.imdb.com/title/${this.selectedMovie.imdbID}`;
  }
}
