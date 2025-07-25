import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { User } from '../../common/user';
import { ConnectorService } from '../../services/connect.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  userForms: FormGroup = new FormGroup({
    fullName: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]),
    emailId: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$/),
    ]),
    mobileNumber: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]),
    gender: new FormControl('', [Validators.required, Validators.pattern(/^(Male|Female|Other)$/)]),
    image: new FormControl('')
  });
  
  
  

  registeredObject: any;
  registerObj: User;

  constructor(
    private toastr: ToastrService,
    private snackBar: MatSnackBar,
    private connector: ConnectorService,
    private router: Router
  ) {
    this.registerObj = new User(
      
      '',
      '',
      '',
      0,
      '',
      '',
    
    );
  }

  submitHandler(): void {
  
    if (this.userForms.valid ) {
      this.toastr.info('Signing up....', 'Please Wait');
      this.registerObj = new User(
        this.userForms.get('fullName')?.value,
        this.userForms.get('emailId')?.value,
        this.userForms.get('password')?.value,
        this.userForms.get('mobileNumber')?.value,
        this.userForms.get('gender')?.value,
        this.userForms.get('image')?.value
      );
      

      this.connector.doSignUp(this.registerObj).subscribe(
        (val) => {
          this.toastr.success(
            'You have been successfully signed up....',
            'Please Log in'
          );
          this.registeredObject = val;
          console.log(this.registeredObject);
          this.router.navigateByUrl('/login'); // Navigate after successful sign-up
        },
        (error) => {
          if (error.status === '403') {
            this.toastr.error('Invalid credentials', 'Sign Up Error');
          } else {
            this.toastr.error(
              'Failed to sign up. Please try again later.',
              'Error'
            );
          }
        }
      );
    } else {

      if (this.userForms.get('fullName')?.invalid) {
        this.snackBar.open('Username must be alteast 5 characters');
      }
      if (this.userForms.get('emailId')?.invalid) {
        this.toastr.error('Please give a valid email');
      }
      if (this.userForms.get('password')?.invalid) {
        this.toastr.error('Password isnt strong enough');
      }

      if (this.userForms.get('mobileNumber')?.invalid) {
        if(this.registerObj.mobileNumber===null){
          this.toastr.error('mobileNumber is required');
        }
        this.toastr.error('mobileNumber must be a 10-digit number');
      }
      
      if (this.userForms.get('gender')?.invalid) {
        this.toastr.error('Gender is required');
      }
    }
  }
 
}
