import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { Product } from '../../common/product';
import { ProductService } from '../../services/product.service';
import { Cart } from '../../common/cart';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit {

  //product!: Product;
  product: Product = new Product('', '', '', '', '', '', 0, 0);


  constructor(
    private cartService: CartService,
    private activatedRoute: ActivatedRoute,
    private productService: ProductService,
  
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(() => {
      this.handleProductDetails(this.product)
    })
  }


  // handleProductDetails() {
  //   const theProductId: number = +this.activatedRoute.snapshot.paramMap.get('id')!
  //   this.productService.getOneProductById(theProductId).subscribe(
  //     (data: any) => {
  //       this.product = data
  //     })
  // }


  handleProductDetails(product:Product) {
   // console.log("yes")
    this.productService.getOneProductById(product.productId).subscribe(
      data => {
        this.product = data;
      },
      error => {
        console.error('Error fetching product details', error);
      }
    );
  }

  addToCart(theProduct:Product) {
    const profileId = sessionStorage.getItem("userId");
    if (profileId === null) {
      console.error('User is not logged in.');
      return;
    }

    this.cartService.addProductToCart(theProduct.productId, profileId).subscribe(
      (cart: Cart) => {
       
      },
      error => {
        console.error('Error adding product to cart:', error);

      }
    );
  }
  // addToCart(product: Product) {
  // //  console.log(`Adding to cart: ${this.product.name}, ${this.product.unitPrice}`)
  //   const theCartItem = new CartItem(this.product);
  //   this.cartService.addProductToCart(theCartItem);
  // }
}
