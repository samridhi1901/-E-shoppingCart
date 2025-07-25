import { Component } from '@angular/core';
import { Product } from '../../../common/product';
import { ProductService } from '../../../services/product.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-prod-management',
  standalone: true,
  imports: [FormsModule,ReactiveFormsModule,CommonModule,RouterModule],
  templateUrl: './prod-management.component.html',
  styleUrl: './prod-management.component.css'
})
export class ProdManagementComponent {

  productsToDisplay: Product[] = [];
  allProducts: Product[] = [];
  productForm: FormGroup;
  editMode: boolean = false;
  selectedProduct: Product | null = null;
  currentSection: string = 'list';
  productIdToBeEdited!:string;
  constructor(private productService: ProductService, private fb: FormBuilder,  private router:Router) {
    this.productForm = this.fb.group({
      productName: ['', Validators.required],
      productType:['',Validators.required],
      category:['',Validators.required],
      description: ['', Validators.required],
    // price: ['', Validators.required],
      image: [''],  // Optional field
      totalPrice:[0,[Validators.required]],
      quantity: [0, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit() {
    this.handleListProducts();
  }

  handleListProducts() {
    this.productService.getProducts().subscribe((data) => {
      this.productsToDisplay = data;
      this.allProducts = data;
    });
  }

  handleDeleteProduct(productId: string) {
    this.productService.deleteProduct(productId).subscribe(() => {
      this.productsToDisplay = this.productsToDisplay.filter(product => product.productId !== productId);
      this.allProducts = this.allProducts.filter(product => product.productId !== productId);
    });
  }

  handleEditProduct(product: Product) {
    this.currentSection='add'
    this.editMode = true;
    this.selectedProduct = product;
    this.productIdToBeEdited=product.productId;
    this.productForm.patchValue(product);
  }

  handleUpdateProduct() {
    if (this.productForm.valid) {
      const updatedProduct = this.productForm.value as Product;
      updatedProduct.productId=this.productIdToBeEdited
      this.productService.updateProduct(updatedProduct).subscribe(() => {
        const index = this.productsToDisplay.findIndex(product => product.productId === updatedProduct.productId);
        if (index !== -1) {
          this.productsToDisplay[index] = updatedProduct;
        }
        this.editMode = false;
        this.selectedProduct = null;
        
        this.productForm.reset();
      });
    }
  }

  handleCancelEdit() {
    this.editMode = false;
    this.selectedProduct = null;
    this.productForm.reset();
  }

  // handleAddProduct() {
  //   if (this.productForm.valid) {
  //     const newProduct = this.productForm.value as Product;
  //     this.productService.addProduct(newProduct).subscribe((addedProduct) => {
  //       this.productsToDisplay.push(addedProduct);
  //       this.allProducts.push(addedProduct);
  //       this.productForm.reset();
  //     });
  //   }
  // }

  handleAddProduct() {
    if (this.productForm.valid) {
      const newProduct = this.productForm.value as Product;
      this.productService.addProduct(newProduct).subscribe(
        addedProduct => {
          this.productsToDisplay.push(addedProduct);
          this.allProducts.push(addedProduct);
          this.productForm.reset();
          console.log('Product added successfully:', addedProduct);
          // Optionally show a success message
          this.router.navigate(['/admin/product-manage']);
        },
        error => {
          console.error('Error adding product:', error);
          // Optionally show an error message
        }
      );
    } else {
      console.warn('Product form is invalid');
    }
  }
  


  showSection(section: string) {
    this.currentSection = section;
    if (section === 'add') {
      this.editMode = false;
      this.productForm.reset();
    }
  }
}
