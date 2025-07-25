import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router
} from '@angular/router';
import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { ConnectorService } from './connect.service';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(
    private connectorService: ConnectorService,
    private router: Router,
    private dialog: MatDialog,
    private toastr: ToastrService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    // Ensure sessionStorage is only accessed in the browser
    if (isPlatformBrowser(this.platformId)) {
      const role = sessionStorage.getItem('role');
      if (role !== null) {
        if (role === 'USER' || role === 'ADMIN') {
          return true;
        } else {
          this.toastr.error(
            'Access Denied',
            'You do not have the necessary permissions to access this page.'
          );
          return false;
        }
      } else {
        this.toastr.info('Not Logged In', 'Please log in to access this page.');
        this.router.navigate(['/login']);
        return false;
      }
    }

    // Fallback for non-browser environments (SSR)
    return false;
  }
}
