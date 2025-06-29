import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Producto} from "../modelo/Producto";

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = 'http://localhost:8085/producto'; // Ajusta el puerto si es necesario

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  listar(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl, {
      headers: this.getHeaders()
    });
  }

  obtenerPorId(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  crear(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto, {
      headers: this.getHeaders()
    });
  }

  crearConImagen(producto: Producto, imagenFile?: File): Observable<Producto> {
    const formData = new FormData();
    
    // Agregar datos del producto
    formData.append('codigo', producto.codigo);
    formData.append('nombre', producto.nombre);
    if (producto.descripcion) formData.append('descripcion', producto.descripcion);
    if (producto.cantidad !== undefined) formData.append('cantidad', producto.cantidad.toString());
    if (producto.precioVenta !== undefined) formData.append('precioVenta', producto.precioVenta.toString());
    if (producto.costoCompra !== undefined) formData.append('costoCompra', producto.costoCompra.toString());
    if (producto.categoria?.id) formData.append('categoriaId', producto.categoria.id.toString());
    
    // Agregar imagen si existe
    if (imagenFile) {
      formData.append('imagen', imagenFile);
    }
    
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.post<Producto>(`${this.apiUrl}/crear-con-imagen`, formData, {
      headers: headers
    });
  }

  actualizar(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, producto, {
      headers: this.getHeaders()
    });
  }

  actualizarConImagen(id: number, producto: Producto, imagenFile?: File): Observable<Producto> {
    const formData = new FormData();
    
    // Agregar datos del producto
    formData.append('codigo', producto.codigo);
    formData.append('nombre', producto.nombre);
    if (producto.descripcion) formData.append('descripcion', producto.descripcion);
    if (producto.cantidad !== undefined) formData.append('cantidad', producto.cantidad.toString());
    if (producto.precioVenta !== undefined) formData.append('precioVenta', producto.precioVenta.toString());
    if (producto.costoCompra !== undefined) formData.append('costoCompra', producto.costoCompra.toString());
    if (producto.categoria?.id) formData.append('categoriaId', producto.categoria.id.toString());
    
    // Agregar imagen si existe
    if (imagenFile) {
      formData.append('imagen', imagenFile);
    }
    
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.put<Producto>(`${this.apiUrl}/${id}/actualizar-con-imagen`, formData, {
      headers: headers
    });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  desactivar(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/desactivar`, {}, {
      headers: this.getHeaders()
    });
  }

  actualizarCantidad(id: number, nuevaCantidad: number): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}/cantidad`, nuevaCantidad, {
      headers: this.getHeaders()
    });
  }

  // Helper para construir la URL completa de la imagen
  getImagenUrl(nombreImagen: string): string {
    return `http://localhost:8085/imagenes/${nombreImagen}`;
  }

  // Helper para imagen por defecto
  getImagenUrlOrDefault(nombreImagen?: string): string {
    if (nombreImagen && nombreImagen.trim() !== '') {
      // Verificar si el nombre de imagen es válido
      const validImageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.webp'];
      const hasValidExtension = validImageExtensions.some(ext => 
        nombreImagen.toLowerCase().includes(ext)
      );
      
      if (hasValidExtension) {
        return `http://localhost:8085/imagenes/${nombreImagen}`;
      }
    }
    
    // Imagen por defecto desde public folder
    return '/imagen.jpg';
  }

  // Verificar si una imagen existe (opcional - para uso futuro)
  checkImageExists(imageUrl: string): Promise<boolean> {
    return new Promise((resolve) => {
      const img = new Image();
      img.onload = () => resolve(true);
      img.onerror = () => resolve(false);
      img.src = imageUrl;
    });
  }
}
