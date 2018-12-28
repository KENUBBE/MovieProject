import { Injectable } from '@angular/core';
import { HttpService } from '../http/http.service';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private _httpService: HttpService) { }

  public fetchMoviesByTitle(value){
    return this._httpService.get(`/api/fetchMovieByTitle/?title=${value}`)
  }

  public fetchMovieDescription(value){
    console.log(value);
    return this._httpService.getFromOMDB(`/?i=${value}&apikey=82d59939`);
  }


  public getHighRatedMovies(){
    return this._httpService.get('/api/highRatedMovies');
  }
}
