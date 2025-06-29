import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../modelo/LoginRequest";
import {Observable} from "rxjs";
import {LoginResponse} from "../modelo/LoginResponse";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl = 'http://localhost:8085/auth/login';

  constructor(private http: HttpClient) {}

  login(loginData: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.authUrl, loginData);
  }
}
