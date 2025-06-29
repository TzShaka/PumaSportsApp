import {Component, OnInit, ViewChild} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef,
  MatRow, MatRowDef,
  MatTable, MatTableDataSource
} from "@angular/material/table";
import {DatePipe, NgClass, NgIf} from "@angular/common";
import {Categoria} from "../../modelo/Categoria";
import {CategoriaService} from "../../services/categoria.service";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CategoriaDialogComponent} from "./categoria-dialog.component";
import {MatSort} from "@angular/material/sort";
import {MatTooltip} from "@angular/material/tooltip";

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    NgClass,
    MatHeaderRow,
    MatRow,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRowDef,
    MatRowDef,
    MatButton,
    MatIcon,
    MatIconButton,
    MatFormField,
    MatInput,
    MatLabel,
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    MatSort,
    DatePipe,
    MatTooltip
  ],
  templateUrl: './categorias.component.html',
  styleUrl: './categorias.component.css'
})
export class CategoriasComponent implements OnInit {
  @ViewChild(MatSort) sort!: MatSort;
  
  dataSource = new MatTableDataSource<Categoria>();
  columnas: string[] = ['id', 'nombre', 'estado', 'fechaCreacion', 'acciones'];
  cargando = false;
  filtro = '';

  constructor(
    private categoriaService: CategoriaService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  cargarCategorias(): void {
    this.cargando = true;
    this.categoriaService.listar().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener categorías', err);
        this.mostrarMensaje('Error al cargar las categorías');
        this.cargando = false;
      }
    });
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }

  abrirDialogo(categoria?: Categoria): void {
    const dialogRef = this.dialog.open(CategoriaDialogComponent, {
      width: '400px',
      data: categoria ? { ...categoria } : null
    });

    dialogRef.afterClosed().subscribe(resultado => {
      if (resultado) {
        if (resultado.id) {
          this.actualizarCategoria(resultado);
        } else {
          this.crearCategoria(resultado);
        }
      }
    });
  }

  crearCategoria(categoria: Categoria): void {
    this.categoriaService.crear(categoria).subscribe({
      next: () => {
        this.mostrarMensaje('Categoría creada exitosamente');
        this.cargarCategorias();
      },
      error: (err) => {
        console.error('Error al crear categoría', err);
        this.mostrarMensaje('Error al crear la categoría');
      }
    });
  }

  actualizarCategoria(categoria: Categoria): void {
    if (categoria.id) {
      this.categoriaService.actualizar(categoria.id, categoria).subscribe({
        next: () => {
          this.mostrarMensaje('Categoría actualizada exitosamente');
          this.cargarCategorias();
        },
        error: (err) => {
          console.error('Error al actualizar categoría', err);
          this.mostrarMensaje('Error al actualizar la categoría');
        }
      });
    }
  }

  eliminarCategoria(categoria: Categoria): void {
    if (!categoria.id) {
      this.mostrarMensaje('Error: Categoría sin ID válido');
      return;
    }

    if (confirm(`¿Está seguro de eliminar la categoría "${categoria.nombre}"?`)) {
      console.log('Eliminando categoría con ID:', categoria.id);
      
      this.categoriaService.eliminar(categoria.id).subscribe({
        next: () => {
          console.log('Categoría eliminada exitosamente');
          this.mostrarMensaje('Categoría eliminada exitosamente');
          this.cargarCategorias();
        },
        error: (err) => {
          console.error('Error completo al eliminar categoría:', err);
          console.error('Status:', err.status);
          console.error('Message:', err.message);
          console.error('Error body:', err.error);
          
          let mensaje = 'Error al eliminar la categoría';
          if (err.status === 404) {
            mensaje = 'Categoría no encontrada';
          } else if (err.status === 409) {
            mensaje = 'No se puede eliminar: la categoría tiene productos asociados. Use desactivar en su lugar.';
          } else if (err.status === 403) {
            mensaje = 'No tienes permisos para eliminar esta categoría';
          } else if (err.status === 500) {
            mensaje = 'Error interno del servidor. Posiblemente la categoría tiene productos asociados.';
          } else if (err.error?.error) {
            mensaje = err.error.error;
          } else if (err.error?.message) {
            mensaje = err.error.message;
          }
          
          this.mostrarMensaje(mensaje);
        }
      });
    }
  }

  cambiarEstado(categoria: Categoria): void {
    if (categoria.id) {
      if (categoria.estado) {
        // Desactivar
        this.categoriaService.desactivar(categoria.id).subscribe({
          next: () => {
            this.mostrarMensaje('Categoría desactivada');
            this.cargarCategorias();
          },
          error: (err) => {
            console.error('Error al desactivar categoría', err);
            this.mostrarMensaje('Error al desactivar la categoría');
          }
        });
      } else {
        // Activar (actualizar con estado true)
        const categoriaActualizada = { ...categoria, estado: true };
        this.categoriaService.actualizar(categoria.id, categoriaActualizada).subscribe({
          next: () => {
            this.mostrarMensaje('Categoría activada');
            this.cargarCategorias();
          },
          error: (err) => {
            console.error('Error al activar categoría', err);
            this.mostrarMensaje('Error al activar la categoría');
          }
        });
      }
    }
  }

  private mostrarMensaje(mensaje: string): void {
    this.snackBar.open(mensaje, 'Cerrar', {
      duration: 3000,
      horizontalPosition: 'right',
      verticalPosition: 'top'
    });
  }
}
