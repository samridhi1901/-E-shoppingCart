export class order {
    orderId: string;
    totalPrice: number;
    orderStatus: string;
    productName: string;
    quantity: number;
    contactNumber: string;
    fulladdress: string;
    profileId: string;
    transactionId: string;
  
    constructor(
      orderId: string,
      totalPrice: number,
      orderStatus: string,
      productName: string,
      quantity: number,
      contactNumber: string,
      fulladdress: string,
      profileId: string,
      transactionId: string
    ) {
      this.orderId = orderId;
      this.totalPrice = totalPrice;
      this.orderStatus = orderStatus;
      this.productName = productName;
      this.quantity = quantity;
      this.contactNumber = contactNumber;
      this.fulladdress = fulladdress;
      this.profileId = profileId;
      this.transactionId = transactionId;
    }
  }
  