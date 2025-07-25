import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { OrderService } from '../../../services/order.service';
import {order} from '../../../common/order'


@Component({
  selector: 'app-order-management',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-management.component.html',
  styleUrl: './order-management.component.css'
})
export class OrderManagementComponent {

  orders: order[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getOrders().subscribe((data) => {
      this.orders = data;
    });
  }

//   handleDeleteOrder(orderId: number) {
//     this.orderService.deleteOrder(orderId).subscribe(() => {
//       this.orders = this.orders.filter(order => order.id !== orderId);
//     });
// }

}
