import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Cart } from '../common/cart';
import { Product } from '../common/product';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private baseUrl = 'http://localhost:8086/carts';

  cartItems: Product[] = [];
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);
  private cart = new BehaviorSubject<Product[]>([]);
  cart$ = this.cart.asObservable();

  private selectedProducts = new BehaviorSubject<Product[]>([]);
  selectedProducts$ = this.selectedProducts.asObservable();


  constructor(private httpClient: HttpClient) { }

  private getHttpOptions() {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
      })
    };
  }

  
addProductToCart(productId: string, profileId: string, quantity: number = 1): Observable<Cart> {
  const url = `${this.baseUrl}/addtocart/${productId}/${profileId}/${quantity}`;
  return this.httpClient.post<Cart>(url, null, this.getHttpOptions());
}

  getCartById(cartId: string | null): Observable<Cart> {
    const url = `${this.baseUrl}/getCartById/${cartId}`;

    return this.httpClient.get<Cart>(url, this.getHttpOptions());
  }

  getCartTotal(cartId: string): Observable<number> {
    const url = `${this.baseUrl}/total/${cartId}`;

    return this.httpClient.get<number>(url, this.getHttpOptions());
  }

  updateCartProducts(updatedProduct: Product, profileId: string): Observable<Cart> {
    const url = `${this.baseUrl}/updateCart/${profileId}`;

    return this.httpClient.put<Cart>(url, updatedProduct, this.getHttpOptions());
  }

  removeProductFromCart(profileId: string): Observable<void> {
    const url = `${this.baseUrl}/delete/${profileId}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + sessionStorage.getItem('token')
    });
    return this.httpClient.delete<void>(url, this.getHttpOptions());
  }
  setSelectedProducts(products: Product[]): void {
    this.selectedProducts.next(products);
  }

  getSelectedProduct(productId: string | null): any {

    const product = this.cartItems.filter((product) => {
      return product.productId == productId
    });

    return product;
  }

}

