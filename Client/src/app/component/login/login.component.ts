import { Component, OnInit, NgZone } from '@angular/core';
import { CLIENT_ID } from 'src/app/constants/constants';
import _axios from 'axios';
import { Router } from '@angular/router';
import { GoogleAuthService } from 'src/app/provider/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private _router: Router, private _ngZone: NgZone, private _authService: GoogleAuthService) { this.loadGapi() }

  ngOnInit() { }

  loadGapi() {
    gapi.load('auth2', function () {
      gapi.auth2.init({
        client_id: CLIENT_ID,
        scope: "https://www.googleapis.com/auth/calendar.events",
      });
    });
  }

  login() {
    gapi.auth2.getAuthInstance().grantOfflineAccess()
      .then(authResult => {
        this._authService.verifyUser(authResult.code)
          .subscribe(res => {
            console.log(res);
            this.storeUser();
            this._ngZone.run(() => this._router.navigateByData({ url: ['/dashboard'], data: 0 }));
          });
      });
  }

  storeUser() {
    let currentEmail = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail();
    let currentName = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getName();
    let currentImg = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getImageUrl();
    localStorage.setItem('currentUser', currentEmail);
    localStorage.setItem('currentName', currentName);
    localStorage.setItem('currentImg', currentImg);
  }
}
