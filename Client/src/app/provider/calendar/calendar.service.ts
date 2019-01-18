import { Injectable } from '@angular/core';
import { HttpService } from '../http/http.service';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private _httpService: HttpService) { }

  public getAllEvent(){
    return this._httpService.get('/api/getAllEvent');
  }

  public createEvent(value){
    return this._httpService.post('/api/scheduleEvent', value);
  }
  
}
