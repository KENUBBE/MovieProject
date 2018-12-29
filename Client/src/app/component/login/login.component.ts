import { Component, OnInit } from '@angular/core';
import { CLIENT_ID } from 'src/app/constants/constants';
import _axios from 'axios';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() { this.loadGapi() }

  ngOnInit() { }

  loadGapi() {
    gapi.load('auth2', function () {
      console.log("GAPI LOADED");
      gapi.auth2.init({
        client_id: CLIENT_ID,
        scope: "https://www.googleapis.com/auth/calendar.events"
      });
    });
  }

  login() {
    gapi.auth2.getAuthInstance().grantOfflineAccess().then(this.signInCallback)
  };

  // TODO: REFACTOR
  signInCallback(authResult) {
    if (authResult.code) {
      let axiosConfig = {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          'Access-Control-Allow-Origin': '*',
          'X-Requested-With': 'XMLHttpRequest'
        }
      };

      _axios.post('http://localhost:8080/verifyUser', authResult.code, axiosConfig)
        .then(data => {
          if (data.status == 200) {
            gapi.auth2.getAuthInstance().currentUser.get().reloadAuthResponse();
            let currentUser = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail();
            localStorage.setItem('currentUser', currentUser);
            window.location.replace("http://localhost:4200/dashboard"); // TODO: NAVIGATEWITHDATA INSTEAD!!!!!
          }
        }).catch(err => console.log("ERROR: ", err))
    }
  }
}
