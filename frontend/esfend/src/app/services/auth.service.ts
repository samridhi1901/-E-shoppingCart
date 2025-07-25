import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpClient, HttpHeaders } from '@angular/common/http';



@Injectable({
  providedIn: 'root',
})

//use session storage
export class AuthService {
  private readonly jwtTokenKey = 'jwtToken';
  constructor(private http:HttpClient) {}

  // setToken(token: string): void {
  //   sessionStorage.setItem(this.jwtTokenKey, token);
  // }

  getToken(): string | null {
    const token = sessionStorage.getItem(this.jwtTokenKey);
    if (token && this.isTokenExpired(token)) {
      this.clearToken();
      return null;
    }
    return token;
  }
  clearToken(): void {
    sessionStorage.removeItem(this.jwtTokenKey);
  }
  isTokenExpired(token: string): boolean {
    if (!token) {
      return true;
    }
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    const expiry = new Date(tokenPayload.exp * 1000);
    return expiry <= new Date();
  }
  getUserRole(): string | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role;
  }

  
}
