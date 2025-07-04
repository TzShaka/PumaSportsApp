import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-ventas',
  standalone: true,
  templateUrl: './ventas.html',
  styleUrls: ['./ventas.scss'],
  imports: [CommonModule, FormsModule, HttpClientModule]
})
export class Ventas implements OnInit {
  ventas: any[] = [];
  nuevaVenta: any = {
    clienteNombre: '',
    clienteEmail: '',
    clienteTelefono: '',
    fecha: new Date().toISOString(),
    detalles: []
  };
  detalle = { productoId: null, descripcion: '', cantidad: 1, precioUnitario: 0.0, descuento: 0.0 };

  mostrarFormulario = false;
  mensaje = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarVentas();
  }

  cargarVentas() {
    this.http.get<any[]>('http://localhost:8085/api/ventas').subscribe(data => {
      this.ventas = data;
    });
  }

  agregarDetalle() {
    if (this.detalle.descripcion && this.detalle.cantidad > 0 && this.detalle.precioUnitario > 0) {
      this.nuevaVenta.detalles.push({ ...this.detalle });
      this.detalle = { productoId: null, descripcion: '', cantidad: 1, precioUnitario: 0.0, descuento: 0.0 };
    }
  }

  quitarDetalle(i: number) {
    this.nuevaVenta.detalles.splice(i, 1);
  }

  guardarVenta() {
    this.http.post('http://localhost:8085/api/ventas', this.nuevaVenta).subscribe(() => {
      this.cargarVentas();
      this.mostrarFormulario = false;
      this.mensaje = 'Venta registrada con éxito';
      setTimeout(() => this.mensaje = '', 3000);
      this.nuevaVenta = {
        clienteNombre: '',
        clienteEmail: '',
        clienteTelefono: '',
        fecha: new Date().toISOString(),
        detalles: []
      };
    });
  }

  cancelar() {
    this.mostrarFormulario = false;
    this.nuevaVenta = {
      clienteNombre: '',
      clienteEmail: '',
      clienteTelefono: '',
      fecha: new Date().toISOString(),
      detalles: []
    };
  }
}
