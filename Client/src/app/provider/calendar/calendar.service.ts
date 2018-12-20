import { Injectable } from '@angular/core';
import { HttpService } from '../http/http.service';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private _httpService: HttpService) { }

  public getUserCalendar(){
    return this._httpService.get("/api/userEvent");
  }
}
