import { Injectable } from '@angular/core';
import { HttpService } from '../http/http.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _httpService: HttpService) { }

  public getAllUsers() {
    return this._httpService.get('/api/allUsers');
  }

  public createGroup(value){
    return this._httpService.post('/api/createGroup', value);
  }

  public getUserGroup(value){
    return this._httpService.get(`/api/userGroup/?currentUser=${value}`);
  }
  
}
