import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CalendarService } from 'src/app/provider/calendar/calendar.service';

@Component({
  selector: 'app-moviepage',
  templateUrl: './moviepage.component.html',
  styleUrls: ['./moviepage.component.css']
})
export class MoviepageComponent implements OnInit {

  selectedMovie: any;

  constructor(private _router: Router, private _calendarService: CalendarService) { }

  ngOnInit() {
    this.selectedMovie = JSON.parse(localStorage.getItem('selectedMovie'));
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
    console.log(event.value);
  }

  showOnImdb() {
    window.location.href = `https://www.imdb.com/title/${this.selectedMovie.imdbID}`;
  }
}
