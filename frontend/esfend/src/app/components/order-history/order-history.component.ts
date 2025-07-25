import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { order } from '../../common/order';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-order-history',
  standalone:true,
  imports:[CommonModule],
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.scss']
})
export class OrderHistoryComponent implements OnInit {

  orders: order[] = []; 

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    const profileId = sessionStorage.getItem('userId');
    if (profileId) {
      this.orderService.getOrderByProfileId(profileId).subscribe(
        (data) => {
           this.orders=data
        },
        (error) => {
          console.error('Error fetching order details', error);
        }
      );
    } else {
      console.error('No profile ID found in session storage');
    }
  }

}
