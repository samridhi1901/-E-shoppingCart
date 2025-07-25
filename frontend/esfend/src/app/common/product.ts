// export class Product {

//   constructor(
//     public id: number,
//     public sku: string,
//     public name: string,
//     public description: string,
//     public unitPrice: number,
//     public imageUrl: string,
//     public active: boolean,
//     public unitsInStock: number,
//     public dateCreated: Date,
//     public lastUpdated: Date
//   ) { }
// }
export class Product {
  constructor(
    public productId: string,
    public productType: string,
    public productName: string,
    public category: string,
    public image: string,
    public description: string,
    public quantity: number,
    public totalPrice: number
  ) { }
}
