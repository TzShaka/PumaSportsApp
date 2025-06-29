import {Categoria} from "./Categoria";

export interface Producto {
  id?: number;
  codigo: string;
  nombre: string;
  descripcion?: string;
  cantidad?: number;
  precioVenta?: number;
  costoCompra?: number;
  estado?: boolean;
  fechaCreacion?: string;
  fechaActualizacion?: string;
  categoria?: Categoria;
  imagen?: string;
}
