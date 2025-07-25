import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CarouselModule } from 'ngx-bootstrap/carousel';


@Component({
  selector: 'app-landing',
  standalone:true,
  imports:[CarouselModule,CommonModule],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent {
  constructor(private router: Router) {}

  slides = [
    { image: 'https://static.vecteezy.com/system/resources/previews/003/684/696/original/online-shopping-concept-for-presentation-slide-template-people-buys-clothes-or-shoes-on-store-website-paying-for-purchases-with-credit-card-at-mobile-app-illustration-for-layout-design-vector.jpg' },
   // { image: 'https://static.vecteezy.com/system/resources/previews/000/211/681/original/shopping-sale-banner-neon-vector.jpg' },
    { image: 'assets/carousel3.jpg' }
  ];

  navigateToLogin() {
    this.router.navigate(['/login']);
  }

  navigateToRegister() {
    this.router.navigate(['/signup']);
  }
}
