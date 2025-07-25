export class OrderDetail {
  fullName: string;
  fullAddress: string;
  contactNumber: string;
  alternateContactNumber: string;
  productName: string;
  quantity: number;
  transactionId: string;
  profileId!:string|null;

  constructor(
    fullName: string,
    fullAddress: string,
    contactNumber: string,
    alternateContactNumber: string,
    productName: string,
    quantity: number,
    transactionId: string
  ) {
    this.fullName = fullName;
    this.fullAddress = fullAddress;
    this.contactNumber = contactNumber;
    this.alternateContactNumber = alternateContactNumber;
    this.productName = productName;
    this.quantity = quantity;
    this.transactionId = transactionId;
  }
}
