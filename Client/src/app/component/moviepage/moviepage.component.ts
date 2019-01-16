import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { CalendarService } from 'src/app/provider/calendar/calendar.service';
import { UserService } from 'src/app/provider/user/user.service';

@Component({
  selector: 'app-moviepage',
  templateUrl: './moviepage.component.html',
  styleUrls: ['./moviepage.component.css']
})
export class MoviepageComponent implements OnInit, OnDestroy {

  selectedMovie: any;
  movieDescription: any[] = [];
  currentUser: any;
  userGroup: any[] = [];
  attendees: string;
  allEvents: any[] = [];
  forbiddenDays: any[] = [];
  tempStartDate: Date;
  tempEndDate: Date;
  min = new Date(Date.now());

  constructor(private _router: Router, private _calendarService: CalendarService, private _userService: UserService) {
    this.selectedMovie = JSON.parse(localStorage.getItem('selectedMovie'));
    this.currentUser = localStorage.getItem('currentUser');
  }

  ngOnInit() {
    this.getAllEvents();
    this.showGroups();
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

  showGroups() {
    this._userService.getUserGroup(this.currentUser).subscribe(res => {
      this.userGroup = res.json();
    });
  }

  getAllEvents() {
    this._calendarService.getAllEvent().subscribe(res => {
      this.allEvents = res.json();
      this.filterForbiddenDays();
    });
  }

  filterForbiddenDays() {
    this.allEvents.forEach(d => {
      let day = {
        'start': new Date(d.startDate.value),
        'end': new Date(d.endDate.value)
      };
      day.start.setSeconds(0);
      day.end.setSeconds(0);
      this.forbiddenDays.push(day);
    });
  }

  public myFilter = (d: Date): boolean => {
    return this.forbiddenDays.every(day => {
      return d.toString().substring(0, 15) !== day.start.toString().substring(0, 15);
    });
  }

  addEvent(date) {
    console.log(this.forbiddenDays)
    let newEvent = {
      'summary': 'Tonights scheduled movie: ' + this.selectedMovie.Title,
      'startDate': date.value,
      'createdBy': localStorage.getItem("currentUser"),
    };

    this._calendarService.createEvent(newEvent).subscribe(res => {
      if (res.status = 200) {
        document.getElementById('eventInputSuccess').innerHTML = 'HAVE FUN!';
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
