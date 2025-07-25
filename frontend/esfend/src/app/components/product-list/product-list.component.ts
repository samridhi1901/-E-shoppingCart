import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { Cart } from '../../common/cart';
import { Product } from '../../common/product';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { Router } from '@angular/router';
import { CommonModule, CurrencyPipe } from '@angular/common';


@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [RouterLink, CurrencyPipe, CommonModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent {


  productsToDisplay: Product[] = [];
  allProducts: Product[] = [];
  currentCategoryId: number = 1;
  previousCategoryId: number = 1;
  searchMode: boolean = false;

  // new properties
  // thePageNumber: number = 1;
  // thePageSize: number = 10;
  // theTotalElements: number = 0;

  previousKeyword: string = '';
  inCart: boolean[] = [];
  cartItems: any;

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.productService.getProducts().subscribe(products => {
      this.productsToDisplay = products;
      this.allProducts = products;
      this.inCart = new Array(products.length).fill(false);
      const searchValue=this.route.snapshot.queryParamMap.get('category')
      if (searchValue !== null) {
        this.handleSearchProducts(searchValue)
      }
    });
  }


  // listProducts() {
  //   this.searchMode = this.route.snapshot.paramMap.has('keyword')
  //   if (this.searchMode) {
  //     this.handleSearchProducts();
  //   } else {
  //     this.handleListProducts();
  //   }
  // }


  handleSearchProducts(searchValue: string) {


    this.productsToDisplay = this.allProducts.filter((product) => {
      // console.log(product.category,searchValue)
      return product.productType.toLocaleLowerCase().includes(searchValue.toLocaleLowerCase());
    })
    

  }



  onCategorySelected(category: string) {
    this.productsToDisplay = this.allProducts.filter((product) => {
      return product.category === category
    })


  }

  addToCart(theProduct: Product, index: number) {
    const profileId = sessionStorage.getItem("userId");
    if (profileId == null) {
      console.log("No Profile Id Found")
      return;
    }


    if (!this.inCart[index]) {

      this.cartService.addProductToCart(theProduct.productId, profileId, 1).subscribe(
        (cart: Cart) => {
          alert('Product Added to Cart Successfully');
          this.inCart[index] = true;
          this.cartService.cartItems = [...cart.productsAdded];
        },
        error => {
          console.error('Error adding product to cart:', error);
        }
      );
      
    } else {
      this.goToCart();
    }
  }

  goToCart(): void {
    this.router.navigate(['/cart-details']);
    //    const cardId=sessionStorage.getItem('userId')
    //     this.cartService.getCartById(cardId)
    //       .subscribe((data:any) => {

    //           this.cartItems = data;
    //   });
  }
}
