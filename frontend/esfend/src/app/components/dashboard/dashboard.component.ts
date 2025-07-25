import { Component } from '@angular/core';
import { CartStatusComponent } from '../cart-status/cart-status.component';
import { Router, RouterModule } from '@angular/router';
import { SearchComponent } from '../search/search.component';
import { ProductCategoryMenuComponent } from '../product-category-menu/product-category-menu.component';
import { ProductListComponent } from '../product-list/product-list.component';
//import { LoginStatusComponent } from '../login-status/login-status.component';
import { ProductService } from '../../services/product.service';
import { ConnectorService } from '../../services/connect.service';
//import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CartStatusComponent,RouterModule,SearchComponent,ProductCategoryMenuComponent,ProductListComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {


  constructor(private productService:ProductService,private connectorservice:ConnectorService){

  }

  navigateToOrderHistory(){

  }
 logOut(){
  this.connectorservice.logout();
 }
}
