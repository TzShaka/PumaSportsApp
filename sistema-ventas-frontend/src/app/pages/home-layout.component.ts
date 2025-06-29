import { Component } from '@angular/core';
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {NgClass, NgForOf, NgIf, TitleCasePipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {MatTooltip} from "@angular/material/tooltip";

@Component({
  selector: 'app-home-layout',
  standalone: true,
  imports: [
    RouterLink,
    RouterOutlet,
    NgClass,
    NgForOf,
    TitleCasePipe,
    MatIcon,
    MatIconButton,
    NgIf,
    MatTooltip
  ],
  templateUrl: './home-layout.component.html',
  styleUrl: './home-layout.component.css'
})
export class HomeLayoutComponent {
  showSidebar = false; // Cambiado a false para que esté oculto por defecto en móvil
  accesos: any[] = [];
  userName: string = '';

  constructor(private router: Router) {
    const accesosData = localStorage.getItem('accesos');
    this.accesos = accesosData ? JSON.parse(accesosData) : [];
    this.userName = localStorage.getItem('userName') || 'Usuario';
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  toggleSidebar() {
    this.showSidebar = !this.showSidebar;
  }
}
