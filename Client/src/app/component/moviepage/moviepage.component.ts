import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-moviepage',
  templateUrl: './moviepage.component.html',
  styleUrls: ['./moviepage.component.css']
})
export class MoviepageComponent implements OnInit {

  selectedMovie: any;

  constructor(private _router: Router) { }

  ngOnInit() {
    this.selectedMovie = JSON.parse(localStorage.getItem('selectedMovie'));
  }

  goBack() {
    this._router.navigateByData({
      url: ['/dashboard'],
      data: 0
    });
  }
  
  showOnImdb(){
    window.location.href = `https://www.imdb.com/title/${this.selectedMovie.imdbID}`;
  }
}
