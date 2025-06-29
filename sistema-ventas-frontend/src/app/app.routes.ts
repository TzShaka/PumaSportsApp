import { Routes } from '@angular/router';
import {LoginComponent} from "./pages/login-component/login-component.component";
import {CategoriasComponent} from "./pages/categorias/categorias.component";
import {HomeLayoutComponent} from "./pages/home-layout.component";
import {AuthGuard} from "./guards/auth.guard";
import {ClientesComponent} from "./pages/clientes/clientes.component";
import {ProductosComponent} from "./pages/productos/productos.component";
import {VentasComponent} from "./pages/ventas/ventas.component";
import {ComprasComponent} from "./pages/compras/compras.component";
import {PagosComponent} from "./pages/pagos/pagos.component";
import {UsuariosComponent} from "./pages/usuarios/usuarios.component";

export const routes: Routes = [
  {
    path: '',
    component: HomeLayoutComponent,
    canActivate: [AuthGuard], // 🔒 Protege el layout principal
    children: [
      { path: 'categorias', component: CategoriasComponent },
      { path: 'cliente', component: ClientesComponent },
      { path: 'producto', component: ProductosComponent },
      { path: 'venta', component: VentasComponent },
      { path: 'compra', component: ComprasComponent},
      { path: 'pago', component: PagosComponent },
      { path: 'usuario', component: UsuariosComponent },
      { path: '', redirectTo: 'categorias', pathMatch: 'full' }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' }
];
