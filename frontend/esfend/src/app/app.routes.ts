import { Routes } from '@angular/router';
import { LandingComponent } from './components/landing/landing.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProductListComponent } from './components/product-list/product-list.component';

import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AdminAreaComponent } from './components/admin-area/admin-area.component';
import { ProdManagementComponent } from './components/admin-area/prod-management/prod-management.component';
import { OrderManagementComponent } from './components/admin-area/order-management/order-management.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { OrderHistoryComponent } from './components/order-history/order-history.component';
import { OrderconfirmComponent } from './components/orderconfirm/orderconfirm.component';
import { AdminGuard } from './services/admin.guard';
import { UserGuard } from './services/user.guard';

export const routes: Routes = [
    { path: '', component: LandingComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent, pathMatch: 'full' },
  { path: 'signup', component: RegisterComponent, pathMatch: 'full' },
  { path: 'checkout', component: CheckoutComponent,canActivate: [UserGuard]},
  { path: 'checkout/:product', component: CheckoutComponent,canActivate: [UserGuard]},
  {path:'dashboard',component:DashboardComponent,canActivate: [UserGuard]},
  { path: 'products/:id', component: ProductDetailsComponent ,canActivate: [UserGuard]},
  { path: 'search/:keyword', component: ProductListComponent,canActivate: [UserGuard] },
  { path: 'category/:id', component: ProductListComponent ,canActivate: [UserGuard]},
  { path: 'category', component: ProductListComponent,canActivate: [UserGuard] },
  { path: 'products', component: ProductListComponent ,canActivate: [UserGuard]},
  {path:'orderhistory', component:OrderHistoryComponent,canActivate: [UserGuard]},
  { path: 'orderconfirm', component: OrderconfirmComponent,canActivate: [UserGuard] },


  { path: 'cart-details', component: CartDetailsComponent,canActivate: [UserGuard] },
  { path: 'admin', component: AdminAreaComponent, children:[
  { path: 'product-manage', component: ProdManagementComponent,canActivate: [AdminGuard] },
  { path: 'orders-manage', component: OrderManagementComponent,canActivate: [AdminGuard] },

  ],canActivate: [AdminGuard]}
];
