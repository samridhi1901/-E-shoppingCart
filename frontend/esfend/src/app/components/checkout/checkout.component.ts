 import { CommonModule } from '@angular/common';
import { Component, Injector, NgZone, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { CheckoutService } from '../../services/checkout.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { Product } from '../../common/product';
import { OrderDetail } from '../../common/orderdetail';
import { ProductService } from '../../services/product.service';
 
import { OrderService } from '../../services/order.service';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { order } from '../../common/order';
 
declare var Razorpay: any;
 
@Component({
  selector: 'app-checkout',
  standalone:true,
  imports:[FormsModule,ReactiveFormsModule,CommonModule,MatFormField,MatLabel,RouterLink],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
 
   
    selectedProducts: Product[] = [];
    order:any;
 
    orderDetails: OrderDetail = {
      profileId:'',
      fullName: '',
      fullAddress: '',
      contactNumber: '',
      alternateContactNumber: '',
      transactionId: '',
      productName:'',
      quantity: 0,
    }
 
    constructor(private activatedRoute: ActivatedRoute,
      private orderservice:OrderService,
      private router: Router,
      private cartservice:CartService,
      private injector: Injector) { }
 
    ngOnInit(): void {
      // this.selectedProducts = this.cartservice.getSelectedProducts();
      const productId = this.activatedRoute.snapshot.queryParamMap.get('product');
     
      this.selectedProducts=this.cartservice.getSelectedProduct(productId)
     
     
    }
 
    public placeOrder(orderForm: NgForm) {
      this.orderservice.placeOrder(this.orderDetails).subscribe(
        (resp) => {
         
          this.orderservice.completedOrder=resp;
          orderForm.reset();
         
          const ngZone = this.injector.get(NgZone);
          ngZone.run(
            () => {
              this.router.navigate(["/orderconfirm"]);
              this.orderservice.orders =[this.orderDetails];
            }
          );
        },
        (err) => {
          console.log(err);
        }
      );
    }
 
 
 
    getCalculatedGrandTotal(): number {
      let grandTotal = 0;
   
      for (let product of this.selectedProducts) {
        // Calculate the total price for each product
        const totalPrice = product.totalPrice * (product.quantity || 1); // Using 1 if quantity is undefined
        grandTotal += totalPrice;
      }
   
      return grandTotal;
    }
   
 
    createTransactionAndPlaceOrder(orderForm: NgForm) {
       for (let product of this.selectedProducts) {
    if (!product.quantity || product.quantity <= 0) {
      alert(`Please enter a valid quantity for product: ${product.productName}`);
      return;
    }
  }
      let amount = this.getCalculatedGrandTotal();
      this.orderservice.createTransaction(amount).subscribe(
        (response) => {
          console.log(response);
          this.openTransactioModal(response, orderForm);
        },
        (error) => {
          console.log(error);
        }
      );
 
    }
 
    openTransactioModal(response: any, orderForm: NgForm) {
      var options = {
        order_id: response.orderId,
        key: response.key,
        amount: response.amount,
        currency: response.currency,
        name: 'Estore',
        description: 'Payment of online shopping',
        image: 'https://cdn.pixabay.com/photo/2023/01/22/13/46/swans-7736415_640.jpg',
        handler: (response: any) => {
          if(response!= null && response.razorpay_payment_id != null) {
            this.processResponse(response, orderForm);
          } else {
            alert("Payment failed..")
          }
         
        },
        prefill : {
          name:'LPY',
          email: 'LPY@GMAIL.COM',
          contact: '9399228814'
        },
        notes: {
          address: 'Online Shopping'
        },
        theme: {
          color: '#F37254'
        },
      };
 
      var razorPayObject = new Razorpay(options);
      razorPayObject.open();
    }
 
    processResponse(resp: any, orderForm:NgForm) {
      this.orderDetails.transactionId = resp.razorpay_payment_id;
      this.orderDetails.productName=this.selectedProducts[0].productName;
      this.orderDetails.quantity=this.selectedProducts[0].quantity;
      this.orderDetails.profileId=sessionStorage.getItem('userId');
     
      this.placeOrder(orderForm);
 }
}
 