import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-disenios',
  standalone: true,
  templateUrl: './disenos.html',
  styleUrls: ['./disenos.scss'],
  imports: [CommonModule, FormsModule, HttpClientModule]
})
export class Disenios implements OnInit {
  disenios: any[] = [];
  materiales: any[] = [];
  busqueda: string = '';
  mostrarFormulario: boolean = false;
  modoEdicion: boolean = false;
  mensaje: string = '';

  materialSeleccionado = {
    idMaterial: null,
    cantidadUsada: 0
  };

  nuevoDisenio: any = {
    idDisenio: null,
    nombrePrenda: '',
    descripcion: '',
    fechaCreacion: new Date().toISOString().split('T')[0],
    materialesUsados: []
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarDisenios();
    this.cargarMateriales();
  }

  cargarDisenios() {
    this.http.get<any[]>('http://localhost:8085/api/disenos').subscribe(data => {
      this.disenios = data;
    });
  }

  cargarMateriales() {
    this.http.get<any[]>('http://localhost:8085/api/materiales').subscribe(data => {
      this.materiales = data;
    });
  }

  agregarMaterial() {
    console.log('Seleccionado:', this.materialSeleccionado);
    const material = this.materiales.find(m => m.idMaterial === this.materialSeleccionado.idMaterial);
    if (material && this.materialSeleccionado.cantidadUsada > 0) {
      this.nuevoDisenio.materialesUsados.push({
        idMaterial: material.idMaterial,
        nombreMaterial: material.nombreMaterial,
        cantidadUsada: this.materialSeleccionado.cantidadUsada
      });

      // Limpia el formulario de selección
      this.materialSeleccionado = { idMaterial: null, cantidadUsada: 0 };
    } else {
      alert('Selecciona un material y cantidad válida');
    }
  }


  eliminarMaterial(index: number) {
    this.nuevoDisenio.materialesUsados.splice(index, 1);
  }

  guardarDisenio() {
    console.log('🛰 Enviando al backend:', this.nuevoDisenio);

    const url = this.modoEdicion
      ? `http://localhost:8085/api/disenos/${this.nuevoDisenio.idDisenio}`
      : 'http://localhost:8085/api/disenos';

    const request = this.modoEdicion
      ? this.http.put(url, this.nuevoDisenio)
      : this.http.post(url, this.nuevoDisenio);

    request.subscribe(() => {
      this.cargarDisenios();
      this.cerrarModal();
      this.mostrarMensaje(this.modoEdicion ? 'Diseño actualizado' : 'Diseño creado');
    });
  }


  editarDisenio(disenio: any) {
    this.nuevoDisenio = JSON.parse(JSON.stringify(disenio));
    this.modoEdicion = true;
    this.mostrarFormulario = true;
  }

  eliminarDisenio(id: number) {
    if (confirm('¿Estás seguro de eliminar este diseño?')) {
      this.http.delete(`http://localhost:8085/api/disenos/${id}`).subscribe(() => {
        this.cargarDisenios();
        this.mostrarMensaje('Diseño eliminado');
      });
    }
  }

  cerrarModal() {
    this.mostrarFormulario = false;
    this.modoEdicion = false;
    this.nuevoDisenio = {
      idDisenio: null,
      nombrePrenda: '',
      descripcion: '',
      fechaCreacion: new Date().toISOString().split('T')[0],
      materialesUsados: []
    };
  }

  mostrarMensaje(texto: string) {
    this.mensaje = texto;
    setTimeout(() => this.mensaje = '', 3000);
  }
}
