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

  constructor(private _router: Router, private _calendarService: CalendarService, private _movieService: MovieService) {
    this.selectedMovie = JSON.parse(localStorage.getItem('selectedMovie'));
  }

  ngOnInit() {
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
    this._calendarService.createEvent(newEvent).subscribe(res => {
      if (res.status = 200) {
        document.getElementById('eventInputSuccess').innerText = 'HAVE FUN!';
        document.getElementById('eventInputSuccess').classList.add('eventInputSuccess');
      } else {
        alert('Something went wrong, please try again!');
      }
    });
  }

  showOnImdb() {
    window.open(`https://www.imdb.com/title/${this.selectedMovie.imdbID}`);
  }

}
