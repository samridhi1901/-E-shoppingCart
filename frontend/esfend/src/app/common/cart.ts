// import { Product } from "./product";

// export class Cart {
//   cartId: string;
//   productId: string;
//   productName: string;
//   totalPrice: number;
//   quantity: number;

//   constructor(cartId: string, productId: string, productName: string, totalPrice: number, quantity: number) {
//     this.cartId = cartId;
//     this.productId = productId;
//     this.productName = productName;
//     this.totalPrice = totalPrice;
//     this.quantity = quantity;
//   }
// }
// cart.model.ts

import { Product } from "./product";

export class Cart {
  cartId: string;
  productsAdded: Product[];

  constructor(cartId: string,  productsAdded: Product[]) {
    this.cartId = cartId;
   
    this.productsAdded = productsAdded;
  }
}
