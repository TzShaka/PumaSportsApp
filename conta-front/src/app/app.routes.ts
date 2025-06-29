import { Routes } from '@angular/router';
import { Reportes } from './pages/reportes/reportes';
import { Pedidos } from './pages/pedidos/pedidos';
import { Almacen } from './pages/almacen/almacen';
import { Disenios } from './pages/disenos/disenos';
import { Productos } from './pages/productos/productos';
import { Dashboard } from './pages/dashboard/dashboard';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: Dashboard },
  { path: 'productos', component: Productos },
  { path: 'disenos', component: Disenios },
  { path: 'almacen', component: Almacen },
  { path: 'pedidos', component: Pedidos },
  { path: 'reportes', component: Reportes },
];
