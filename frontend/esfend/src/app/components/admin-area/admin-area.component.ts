import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ConnectorService } from '../../services/connect.service';
import { OrderManagementComponent } from './order-management/order-management.component';

@Component({
  selector: 'app-admin-area',
  standalone: true,
  imports: [RouterModule,OrderManagementComponent],
  templateUrl: './admin-area.component.html',
  styleUrl: './admin-area.component.css'
})
export class AdminAreaComponent {

  constructor(private connectorService:ConnectorService){}
  
  logOut(){
    this.connectorService.logout();
   }
}
