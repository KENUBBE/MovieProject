import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { baseApiUrl } from '../../constants/constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: Http) { } 

  public get(endpoint: string): Observable<any> {
    return this.http.get(baseApiUrl + endpoint);
  }

  public post(endpoint: string, body: any): Observable<any> {
    const headers = new Headers({'content-type': 'application/json; charset=utf-8', 'Access-Control-Allow-Origin': '*',
    'X-Requested-With': 'XMLHttpRequest'});
    return this.http.post(baseApiUrl + endpoint, body, {headers: headers});
  }
}
