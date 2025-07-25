import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import Login from '../common/login';
import { User } from '../common/user';
import { Router } from '@angular/router';
//import { v4 as uuidv4 } from 'uuid'; // Import UUID generator




@Injectable({
  providedIn: 'root',
})
export class ConnectorService {
  private apiUrl = 'http://localhost:8086/';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ',
    }),
  };
  constructor(private httpClient: HttpClient,private router:Router) {}

  // doLogin(login: Login): Observable<any> {
  //   const httpOptions = {
  //     headers: new HttpHeaders({
  //       'Content-Type': 'application/json',
  //      'Access-Control-Allow-Origin':"*"
        
  //     }),
  //   };
  //   const data = {
  //     email: login.emailId,
  //     password: login.password,
  //   };
  //   return this.httpClient
  //     .post<any>(this.apiUrl + 'profiles/login', data, httpOptions)
  //     .pipe(catchError(this.errorHandler));
  // }
  doLogin(login: Login): Observable<any> {
    console.log('here 6');
    const data = {
      email: login.emailId,
      password: login.password,
    };
    console.log(data);
    return this.httpClient
      .post<any>(this.apiUrl + 'profiles/login', data, this.httpOptions)
      .pipe(catchError(this.errorHandler));
  }

  doSignUp(user: User): Observable<any> {
    console.log("yes")
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    const data = {
      profileId: user.profileId, // Use uuidv4() to generate unique ID
      fullName: user.fullName,
      emailId: user.emailId,
      password: user.password,
      mobileNumber: user.mobileNumber,
      // dateOfBirth: user.dateOfBirth,
      gender: user.gender,
      role: user.role || 'USER',
      
    };
    console.log(data);
    return this.httpClient
      .post(this.apiUrl + 'profiles/register', data, httpOptions)
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(() => error);
  }

  logout(): void {
   sessionStorage.clear()
    this.router.navigate(['/login']); // or '/' for home page
  }
}
