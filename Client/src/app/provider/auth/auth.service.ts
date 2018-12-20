import { Injectable, NgZone, Output } from '@angular/core';
import { HttpService } from '../http/http.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GoogleAuthService {
  public auth2: any;

  constructor(private _httpService: HttpService, private zone: NgZone, private http: HttpClient) { }

  public verifyToken(value) {
    return this._httpService.post("/storeauthcode", value);
  }

  public verifyUserCredential() {
    return this._httpService.get("/api/getUserCredential");
  }

  public authenticateUser() {
    return this._httpService.get("/api/userEvent");
  }
}

