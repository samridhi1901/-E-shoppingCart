import { Component } from '@angular/core';

import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';




import { Router } from '@angular/router';
import { ConnectorService } from '../../services/connect.service';
import Login from '../../common/login';
import { AuthService } from '../../services/auth.service';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  userForms: FormGroup = new FormGroup({
    emailId: new FormControl('', [Validators.required]),
    password: new FormControl('', [
      Validators.required,
      // Validators.minLength(6),
      // Validators.pattern(''),
    ]),
  });

  loginObj: Login;

  loggedInObj: any;
  constructor(
    private toastr: ToastrService,
    private connector: ConnectorService,
    private authService: AuthService,
    private router: Router,
    
   
  ) {
    this.loginObj = new Login('', '');
  }

  loginHandler() {
    console.log('here 1');
    if (this.userForms.valid) {
      this.toastr.info('Logging in....', 'Please Wait');
      console.log('here 92');

      this.loginObj = new Login(
        this.userForms.get('emailId')?.value,
        this.userForms.get('password')?.value
      );
      console.log('here 3');

      this.connector.doLogin(this.loginObj).subscribe(
        (val) => {
          console.log('here 4');
          this.loggedInObj = val;
          console.log(
            'here sir ' + this.loggedInObj.Message,
            this.loggedInObj.Token
          );
          // this.authService.setToken(this.loggedInObj.Token);
          const role = this.authService.getUserRole();
          // console.log(role,"role")
          if (val.role == 'USER') {
            
            this.toastr.success('Logged in successfully as User', 'Success');
            console.log(val);
            sessionStorage.setItem("token", val.Token);
            sessionStorage.setItem("userId",val.userId);
                sessionStorage.setItem("username", val.username);
                sessionStorage.setItem("role", val.role);
            
            // this.router.navigateByUrl('/dashboard');
            this.router.navigate(['/dashboard'])
            return;
          } else {
            this.toastr.success('Logged in successfully as Admin', 'Success');
            console.log(val);
            sessionStorage.setItem("token", val.Token);
            sessionStorage.setItem("adminId",val.userId);
                sessionStorage.setItem("adminname", val.username);
                sessionStorage.setItem("role", val.role);
            this.router.navigateByUrl('/admin');
          }
        },
        (error) => {
          if (error.status == '403') {
            this.toastr.error('Invalid credentials', 'Login Error');
          } else {
            this.toastr.error(
              'Failed to login. Please try again later.',
              'Error'
            );
          }
        }
      );
      console.log(this.loggedInObj);
    } else {
      this.toastr.error('Please fill all the fields');
    }
  }
  signInHander(event: Event) {
    event.preventDefault();
    this.router.navigateByUrl('/signup');
  }
}
