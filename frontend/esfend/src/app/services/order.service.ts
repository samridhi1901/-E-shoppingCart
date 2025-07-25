import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { OrderDetail } from '../common/orderdetail';
import { order } from '../common/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  orders:OrderDetail[]=[];
  completedOrder!:any;
  private baseUrl = 'http://localhost:8086/orders/';

  constructor(private httpClient:HttpClient) { }

  private getHttpOptions() {
    return {
      headers: new HttpHeaders({
       'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
      })
    };
  }

  getOrders():Observable< order[]> {    
    
    return this.httpClient.get<Array<order>>(this.baseUrl+"all",this.getHttpOptions())    
  }

  getOrderById(orderId:string):Observable<order>{
    return this.httpClient.get<order>(this.baseUrl+"orderId", this.getHttpOptions())
  }

  
  public placeOrder(orderDetails: OrderDetail) {
    // console.log(orderDetails)
   
    return this.httpClient.post("http://localhost:8086/orders/placeOrder", orderDetails,this.getHttpOptions());
  }
createTransaction(order: any) {
  console.log(order);
  return this.httpClient.post(`http://localhost:8086/orders/createTransaction/${order}`,{}, this.getHttpOptions());
}


  getOrderByProfileId(profileId: string): Observable<order[]> {
    
    return this.httpClient.get<order[]>(`${this.baseUrl}user/${profileId}`, this.getHttpOptions());
  }
}

