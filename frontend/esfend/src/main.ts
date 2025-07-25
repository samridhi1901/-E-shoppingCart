import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { importProvidersFrom } from '@angular/core';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

const appConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withFetch()), // ✅ Enable Fetch API for HttpClient
    importProvidersFrom(
      BrowserAnimationsModule,
      ToastrModule.forRoot({
        positionClass: 'toast-top-right',
        timeOut: 3000,
        preventDuplicates: true,
      })
    ),
    provideAnimationsAsync(),
  ],
};

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
