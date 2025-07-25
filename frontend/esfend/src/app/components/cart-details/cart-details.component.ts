import { Component, OnInit } from '@angular/core';
import { Cart } from '../../common/cart';
import { CartService } from '../../services/cart.service';
import { CommonModule } from '@angular/common';
import { Product } from '../../common/product';
import { CheckoutComponent } from '../checkout/checkout.component';
import { RouterModule, Router } from '@angular/router';
import { SimpleProduct } from '../../common/simpleproduct';
import { ProductService } from '../../services/product.service';
 
@Component({
  selector: 'app-cart-details',
  standalone: true,
  imports: [CommonModule, CheckoutComponent, RouterModule],
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.scss']
})
export class CartDetailsComponent implements OnInit {
 
  cartItems: Cart = new Cart('', []);
  selectedProducts: SimpleProduct[] = [];
 
  constructor(
    private cartService: CartService,
    private router: Router,
    private productservice: ProductService
  ) {}
 
  ngOnInit(): void {
    this.listCartDetails();
  }
 
  get hasProducts(): boolean {
    return !!this.cartItems?.productsAdded?.length;
  }
 
  listCartDetails(): void {
    const cardId = sessionStorage.getItem('userId');
    if (!cardId) {
      console.error("User ID not found in sessionStorage");
      return;
    }
 
    this.cartService.getCartById(cardId).subscribe({
      next: (data: any) => {
        if (data && data.productsAdded) {
          this.cartItems = data;
          this.cartService.cartItems = [...data.productsAdded];
        } else {
          this.cartItems = new Cart('', []);
          this.cartService.cartItems = [];
        }
      },
      error: err => {
        console.error("Error fetching cart:", err);
      }
    });
  }
 
  updateProduct(product: Product): void {
    const profileId = sessionStorage.getItem("userId");
    if (!profileId) {
      console.log("No Profile Id Found");
      return;
    }
 
    this.cartService.updateCartProducts(product, profileId).subscribe({
      next: data => {
        this.cartService.cartItems = [...data.productsAdded];
      },
      error: err => {
        console.error("Error updating product:", err);
      }
    });
  }
 
  incrementQuantity(product: Product): void {
    this.productservice.getOneProductById(product.productId).subscribe({
      next: productDetails => {
        if (product.quantity < productDetails.quantity) {
          product.quantity += 1;
          this.updateProduct(product);
        } else {
          alert("Cannot add more than available stock");
        }
      },
      error: err => {
        console.error("Error fetching product details:", err);
      }
    });
  }
 
  decrementQuantity(product: Product): void {
    if (product.quantity <= 1) {
      alert("Quantity cannot be less than 1. Remove the item if you don't want it.");
      return;
    }
 
    product.quantity -= 1;
    this.updateProduct(product);
  }
 
  remove(product: Product): void {
    const profileId = sessionStorage.getItem("userId");
    if (!profileId) {
      console.log("No Profile Id Found");
      return;
    }
 
    this.cartService.removeProductFromCart(profileId).subscribe({
      next: () => {
        this.listCartDetails();
      },
      error: err => {
        console.error("Error removing product:", err);
      }
    });
  }
 
  proceedToCheckout(productId: string): void {
    const selectedItems: Product[] = this.cartItems.productsAdded;
 
    this.selectedProducts = selectedItems.map(item => ({
      productName: item.productName,
      totalPrice: item.totalPrice
    }));
 
    this.cartService.setSelectedProducts(selectedItems);
 
    this.router.navigate(['/checkout'], {
      queryParams: { product: productId }
    });
  }
 
  proceedToCheckouts(product: Product): void {
    this.router.navigate(['/checkout'], {
      queryParams: {
        productId: product.productId,
        quantity: product.quantity
      }
    });
  }
 
  similarProducts(productType: string): void {
    this.router.navigate(['/dashboard'], {
      queryParams: { category: productType }
    });
  }
}