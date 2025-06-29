import { Component } from '@angular/core';
import { RouterModule, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.html',
  styleUrls: ['./app.scss'],
  imports: [RouterModule, RouterLink, RouterLinkActive, RouterOutlet],
})
export class App {
  title = 'Tu Proyecto';
}
