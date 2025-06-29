import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-almacen',
  standalone: true,
  templateUrl: './almacen.html',
  styleUrls: ['./almacen.scss'],
  imports: [CommonModule, FormsModule, HttpClientModule]
})
export class Almacen implements OnInit {
  materiales: any[] = [];
  materialesFiltrados: any[] = [];
  busqueda: string = '';
  mostrarFormulario: boolean = false;
  modoEdicion: boolean = false; // ← reemplaza a materialEditando
  mensaje: string = '';

  nuevoMaterial: any = {
    idMaterial: null,
    nombreMaterial: '',
    tipoMaterial: '',
    composicion: '',
    propiedadesClave: '',
    usosComunes: '',
    requiereMezcla: false,
    proveedoresSugeridos: '',
    costoUnitarioAprox: 0,
    unidadMedida: '',
    stockDisponible: 0
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.cargarMateriales();
  }

  cargarMateriales() {
    this.http.get<any[]>('http://localhost:8085/api/materiales').subscribe(data => {
      this.materiales = data;
      this.materialesFiltrados = data;
    });
  }

  filtrarMateriales() {
    this.materialesFiltrados = this.materiales.filter(m =>
      m.nombreMaterial.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }

  guardarMaterial() {
    const url = this.modoEdicion
      ? `http://localhost:8085/api/materiales/${this.nuevoMaterial.idMaterial}`
      : 'http://localhost:8085/api/materiales';

    const request = this.modoEdicion
      ? this.http.put(url, this.nuevoMaterial)
      : this.http.post(url, this.nuevoMaterial);

    request.subscribe(() => {
      this.cargarMateriales();
      this.mostrarMensaje(this.modoEdicion ? "Material actualizado correctamente" : "Material creado exitosamente");
      this.cancelarFormulario();
    });
  }

  editarMaterial(material: any) {
    this.nuevoMaterial = { ...material };
    this.modoEdicion = true;
    this.mostrarFormulario = true;
  }

  eliminarMaterial(id: number) {
    const confirmar = confirm('¿Estás seguro de eliminar este material?');
    if (!confirmar) return;

    this.http.delete(`http://localhost:8085/api/materiales/${id}`).subscribe(() => {
      this.cargarMateriales();
      this.mostrarMensaje("Material eliminado correctamente");
    });
  }

  cancelarFormulario() {
    this.nuevoMaterial = {
      idMaterial: null,
      nombreMaterial: '',
      tipoMaterial: '',
      composicion: '',
      propiedadesClave: '',
      usosComunes: '',
      requiereMezcla: false,
      proveedoresSugeridos: '',
      costoUnitarioAprox: 0,
      unidadMedida: '',
      stockDisponible: 0
    };
    this.modoEdicion = false;
    this.mostrarFormulario = false;
  }

  cerrarModal() {
    this.cancelarFormulario(); // cierra y limpia
  }

  mostrarMensaje(texto: string) {
    this.mensaje = texto;
    setTimeout(() => {
      this.mensaje = '';
    }, 3000);
  }
}
