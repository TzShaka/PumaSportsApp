import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {CategoriasComponent} from "./pages/categorias/categorias.component";
import {LoginComponent} from "./pages/login-component/login-component.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CategoriasComponent, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'sistema-ventas-frontend';
}
