import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Categoria} from "../modelo/Categoria";

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  private apiUrl = 'http://localhost:8085/categoria'; // Ajusta el puerto si es necesario

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  listar(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl, {
      headers: this.getHeaders()
    });
  }

  obtenerPorId(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  crear(categoria: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(this.apiUrl, categoria, {
      headers: this.getHeaders()
    });
  }

  actualizar(id: number, categoria: Categoria): Observable<Categoria> {
    return this.http.put<Categoria>(`${this.apiUrl}/${id}`, categoria, {
      headers: this.getHeaders()
    });
  }

  eliminar(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    console.log('URL de eliminación:', url);
    console.log('Headers:', this.getHeaders());
    
    return this.http.delete<void>(url, {
      headers: this.getHeaders()
    });
  }

  desactivar(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/desactivar/${id}`, {}, {
      headers: this.getHeaders()
    });
  }
}
