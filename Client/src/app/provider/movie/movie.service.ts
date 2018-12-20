import { Injectable } from '@angular/core';
import { HttpService } from '../http/http.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private _httpService: HttpService) { }

  public fetchMoviesByTitle(value){
    return this._httpService.get(`/api/fetchMovieByTitle/?title=${value}`)
  }
}
