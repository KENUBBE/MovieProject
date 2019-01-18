import { Injectable, NgZone, Output } from '@angular/core';
import { HttpService } from '../http/http.service';

@Injectable({
  providedIn: 'root'
})
export class GoogleAuthService {
 
  constructor(private _httpService: HttpService) { }

  public verifyUser(value) {
    return this._httpService.post('/api/verifyUser', value);
  }
  
}

