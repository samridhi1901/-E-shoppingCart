import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ConnectorService } from './connect.service'; 
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(
    private connectorService: ConnectorService,
    private router: Router,
    private dialog: MatDialog,
    private toastr: ToastrService
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    try {
      // ✅ Check if running in browser & sessionStorage exists
      if (typeof window !== 'undefined' && 'sessionStorage' in window) {
        const role = sessionStorage.getItem('role');

        if (role === 'ADMIN') {
          return true;
        } else if (role !== null) {
          this.toastr.warning('Access Denied', 'You do not have the necessary permissions.');
          this.router.navigate(['/unauthorized']); // or any fallback route
          return false;
        }
      }
    } catch (err) {
      console.error('Error accessing sessionStorage:', err);
    }

    this.toastr.info('Not Logged In', 'Please log in to access this page.');
    this.router.navigate(['/login']);
    return false;
  }
}
