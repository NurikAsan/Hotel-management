import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL = 'http://localhost:8080/'

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  register(signupRequest: any): Observable<any>{
    return this.http.post(BASIC_URL + "api/v1/auth/signup", signupRequest);
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post(BASIC_URL + "api/v1/auth/login", loginRequest)
  }

  logout(logoutRequest: any): Observable<any>{
    return this.http.post(BASIC_URL + "api/v1/auth/logout", logoutRequest)
  }

  change_password(user_id: number, password: any): Observable<any> {
    return this.http.post(BASIC_URL + "api/v1/auth/change_password", {user_id, password})
  }
}
