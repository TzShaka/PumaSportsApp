import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes'; // si tienes rutas
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), // si tienes rutas definidas
    importProvidersFrom(HttpClientModule, FormsModule) // <-- AQUI IMPORTANTE
  ]
};
