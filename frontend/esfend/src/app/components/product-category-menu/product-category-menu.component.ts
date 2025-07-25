import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { ProductCategory } from '../../common/product-category';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Product } from '../../common/product';


@Component({
  selector: 'app-product-category-menu',
  standalone:true,
  imports:[RouterLink,CommonModule],
  templateUrl: './product-category-menu.component.html',
  styleUrls: ['./product-category-menu.component.scss']
})
export class ProductCategoryMenuComponent implements OnInit {

  productCategories: string[] = [];
  @Output() categorySelected: EventEmitter<string> = new EventEmitter<string>();
  constructor(private productService: ProductService) {
    
   }

   ngOnInit(): void {
    this.listProductCategories();
  }
  listProductCategories() {
    this.productService.getProductCategories().subscribe(
      (data: any) => {
        this.productCategories = data;
      }
    )
  }
  filterProducts(category:string){
    this.categorySelected.emit(category);
  }
}
