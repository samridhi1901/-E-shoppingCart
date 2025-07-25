import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { CommonModule, CurrencyPipe, NgFor } from '@angular/common';
import { order } from '../../common/order';
import { OrderDetail } from '../../common/orderdetail';

@Component({
  selector: 'app-orderconfirm',
  standalone: true,
  imports: [CommonModule,CurrencyPipe],
  templateUrl: './orderconfirm.component.html',
  styleUrl: './orderconfirm.component.css'
})
export class OrderconfirmComponent {

  orders: order[] = []; 


  constructor(private router: Router,private orderService:OrderService) {
    this.orders.push(this.orderService.completedOrder)
  }

  ngOnInit(): void {
    // setTimeout(() => {
    //   this.router.navigateByUrl('/dashboard');
    // }, 5000); // Redirects after 5 seconds
    // const profileId = sessionStorage.getItem('userId');
    // if (profileId) {
    //   this.orderService.getOrderByProfileId(profileId).subscribe(order => {
    //     this.orders = order;
    //   });
    // }
  
  }
  

  // fetchOrderDetails(): void {
  //   const profileId = sessionStorage.getItem('userId');
  //   if (profileId) {
  //     this.orderService.getOrderByProfileId(profileId).subscribe(
  //       (data) => {
  //          this.orders=data;
  //       });
  //     }
  //   }
  
  
}
