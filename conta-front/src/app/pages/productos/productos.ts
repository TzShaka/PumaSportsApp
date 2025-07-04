
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-productos',
  standalone: true,
  templateUrl: './productos.html',
  styleUrls: ['./productos.scss'],
  imports: [CommonModule, FormsModule, HttpClientModule]
})
export class Productos implements OnInit {
  productos: any[] = [];
  disenios: any[] = [];

  mostrarFormulario = false;
  modoEdicion = false;
  mensaje = '';

  nuevoProducto: any = {
    idProducto: null,
    nombreProducto: '',
    cantidadProducida: 1,
    idDisenio: null
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.cargarProductos();
    this.cargarDisenios();
  }

  cargarProductos() {
    this.http.get<any[]>('http://localhost:8085/api/productos').subscribe(data => this.productos = data);
  }

  cargarDisenios() {
    this.http.get<any[]>('http://localhost:8085/api/disenos').subscribe(data => this.disenios = data);
  }

  abrirFormulario(prod?: any) {
    if (prod) {
      this.nuevoProducto = { ...prod, idDisenio: prod.disenio.idDisenio };
      this.modoEdicion = true;
    } else {
      this.nuevoProducto = { idProducto: null, nombreProducto: '', cantidadProducida: 1, idDisenio: null };
      this.modoEdicion = false;
    }
    this.mostrarFormulario = true;
  }

  guardarProducto(form: any) {
    if (!form.valid || !this.nuevoProducto.idDisenio) return;

    const productoParaEnviar = {
      idProducto: this.nuevoProducto.idProducto,
      nombreProducto: this.nuevoProducto.nombreProducto,
      cantidadProducida: this.nuevoProducto.cantidadProducida,
      diseno: {
        idDisenio: this.nuevoProducto.idDisenio
      }
    };

    const req = this.modoEdicion
      ? this.http.put(`http://localhost:8085/api/productos/${productoParaEnviar.idProducto}`, productoParaEnviar)
      : this.http.post('http://localhost:8085/api/productos', productoParaEnviar);

    req.subscribe(() => {
      this.cargarProductos(); 
      this.cerrarModal();
      this.mostrarMensaje(this.modoEdicion ? 'Producto actualizado' : 'Producto creado');
    });
  }


  eliminarProducto(id: number) {
    if (confirm('¿Eliminar este producto?')) {
      this.http.delete(`http://localhost:8085/api/productos/${id}`).subscribe(() => {
        this.cargarProductos();
        this.mostrarMensaje('Producto eliminado');
      });
    }
  }

  cerrarModal() {
    this.mostrarFormulario = false;
    this.modoEdicion = false;
  }

  mostrarMensaje(text: string) {
    this.mensaje = text;
    setTimeout(() => this.mensaje = '', 3000);
  }
}
