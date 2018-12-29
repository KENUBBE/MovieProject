import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GuardService implements CanActivate {

  constructor(private _router: Router) {}

  canActivate() {
    if (localStorage.getItem('currentUser') != null) {
      return true;
    } else {
      this._router.navigateByData({
        url: ['/'],
        data: 0
      });
    }
  }

}
