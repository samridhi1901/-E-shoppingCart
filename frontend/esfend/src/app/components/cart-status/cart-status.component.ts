import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { RouterLink } from '@angular/router';
import { CurrencyPipe } from '@angular/common';


@Component({
  selector: 'app-cart-status',
   standalone:true,
   imports:[RouterLink,CurrencyPipe],
  templateUrl: './cart-status.component.html',
  styleUrls: ['./cart-status.component.scss']
})
export class CartStatusComponent implements OnInit {

  totalPrice: number = 0.00;
  totalQuantity: number = 0;

  constructor(private cartService: CartService) {

  }
  ngOnInit(): void {
    this.updateCartStatus();
  }


  //subscribe to cart totalPrice
  updateCartStatus() {

    this.cartService.totalPrice.subscribe(
      data => this.totalPrice = data
    )

    this.cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data
    )



  }



}
