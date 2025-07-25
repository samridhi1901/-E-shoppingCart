import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../common/product';
import { map, Observable } from 'rxjs';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = 'http://localhost:8086/';

  private categoryUrl = 'http://localhost:8086/products/categories';

  private searchUrl = 'http://localhost:8086/products/';

  private getByIdUrl = 'http://localhost:8086/products/getProduct';

  productsTobeDisplayed:Array<Product>
 
  initialProducts:Array<Product>

  private getHttpOptions() {
    return {
      headers: new HttpHeaders({
       'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
      })
    };
  }
  constructor (private httpClient: HttpClient) {
    this.productsTobeDisplayed=[]
    this.initialProducts=[]
   }

  
  getProducts():Observable< Product[]> {    
    return this.httpClient.get<Array<Product>>(this.searchUrl+"all",this.getHttpOptions())    
  }

  getProductListPaginate(
    thePage: number,
    thePageSize: number,
    theCategoryId: number): Observable<ApiResponseProduct> {

    const searchUrl = `${ this.baseUrl }/search/category?id=${ theCategoryId }`
      + `&page=${ thePage }&size=${ thePageSize }`;

    return this.httpClient.get<ApiResponseProduct>(searchUrl,this.getHttpOptions())
      // .pipe(map(response => response));
  }

  getProductCategories(): Observable<Product[]> {
  
    return this.httpClient.get<Array<Product>>(this.categoryUrl,this.getHttpOptions())
      // .pipe(map(response => response.content));

  }


  addProduct(product: Product): Observable<Product> {

    const searchUrl = `${ this.searchUrl }product`
    return this.httpClient.post<Product>(searchUrl, product,this.getHttpOptions());
  }

  updateProduct(product: Product): Observable<Product> {
    const searchUrl = `${ this.searchUrl }update`
    // console.log(product.productId)
    return this.httpClient.put<Product>(searchUrl, product,this.getHttpOptions());
  }

  deleteProduct(productId: string): Observable<void> {
    const searchUrl = `${ this.searchUrl }${productId}`

    return this.httpClient.delete<void>(searchUrl,this.getHttpOptions());
  }


  // searchProductsPaginate(
  //   thePage: number,
  //   thePageSize: number,
  //   theKeyword: string): Observable<ApiResponseProduct> {

  //   const searchUrl = `${ this.searchUrl }?name=${ theKeyword }`
  //     + `&page=${ thePage }&size=${ thePageSize }`;

  //   return this.httpClient.get<ApiResponseProduct>(searchUrl)
  //     .pipe(map(response => response));
  // }

  getOneProductById(id: string): Observable<Product> {

    const url = `${ this.getByIdUrl }/${ id }`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + sessionStorage.getItem('token')
    });
    console.log("product details coming..")
    return this.httpClient.get<Product>(url,{headers})
      .pipe(map(product => product));
      
  }

  getByCategory(category:string){
    const searchUrl = `${ this.searchUrl }${category}`
    return this.httpClient.delete<void>(searchUrl,this.getHttpOptions());


  }


}

interface ApiResponseProduct {
  content: Product[];
  totalPages: number;
  size: number;
  totalElements: number,
  number: number,
  first: boolean,
  last: boolean,
  empty: boolean;

}

// interface ResponseProductCategory {
//   content: Product[];
// }
