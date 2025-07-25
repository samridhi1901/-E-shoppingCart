import { Product } from "./product";

export class ProductCategory {
   categoryId:string;
  categoryName:string;
  
  constructor(product: Product) {
    this.categoryId= product.productId;
    this.categoryName = product.category;
}
}