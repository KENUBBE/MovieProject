import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.css']
})
export class UserpageComponent implements OnInit {

  userName: any;
  userEmail: any;
  userImg: any;

  constructor(private _router : Router) { }

  ngOnInit() {
    this.getUserInfo();
  }

  getUserInfo() {
    this.userName = localStorage.getItem('currentName');
    this.userEmail = localStorage.getItem('currentUser');
    this.userImg = localStorage.getItem('currentImg');
  }

  goBack() {
    this._router.navigateByData({
      url: ['/dashboard'],
      data: 0
    });
  }

}
