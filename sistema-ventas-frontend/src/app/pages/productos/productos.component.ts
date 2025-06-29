import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {Producto} from "../../modelo/Producto";
import {ProductoService} from "../../services/producto.service";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, 
  MatHeaderRowDef, 
  MatRow, 
  MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {CurrencyPipe, DatePipe, NgClass, NgIf, SlicePipe} from "@angular/common";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatDialog} from "@angular/material/dialog";
import {ProductoDialogComponent} from "./producto-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatTooltip} from "@angular/material/tooltip";
import {MatChip} from "@angular/material/chips";
import {MatSort, MatSortHeader} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [
    MatTable,
    NgIf,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    CurrencyPipe,
    DatePipe,
    NgClass,
    MatButton,
    MatIcon,
    MatIconButton,
    MatFormField,
    MatLabel,
    MatInput,
    FormsModule,
    MatTooltip,
    MatChip,
    MatSort,
    MatSortHeader,
    MatPaginator,
    SlicePipe
  ],
  template: `
    <div class="productos-container">
      <!-- Header -->
      <div class="header-section">
        <div class="title-section">
          <h1 class="page-title">
            <mat-icon class="title-icon">inventory_2</mat-icon>
            Gestión de Productos
          </h1>
          <p class="page-subtitle">Administra tu catálogo de productos con imágenes</p>
        </div>
        
        <button mat-raised-button color="primary" (click)="openDialog()" class="add-button">
          <mat-icon>add_circle</mat-icon>
          Nuevo Producto
        </button>
      </div>

      <!-- Filtros -->
      <div class="filters-section">
        <mat-form-field appearance="outline" class="search-field">
          <mat-label>Buscar productos</mat-label>
          <input matInput 
                 [(ngModel)]="filtroTexto" 
                 (input)="aplicarFiltro()"
                 placeholder="Buscar por código, nombre o categoría...">
          <mat-icon matSuffix>search</mat-icon>
        </mat-form-field>
        
        <div class="stats-chips">
          <mat-chip class="stat-chip total">
            <mat-icon>inventory</mat-icon>
            Total: {{dataSource.data.length}}
          </mat-chip>
          <mat-chip class="stat-chip active">
            <mat-icon>check_circle</mat-icon>
            Activos: {{getProductosActivos()}}
          </mat-chip>
          <mat-chip class="stat-chip low-stock">
            <mat-icon>warning</mat-icon>
            Stock Bajo: {{getStockBajo()}}
          </mat-chip>
        </div>
      </div>

      <!-- Tabla -->
      <div class="table-container">
        <mat-table [dataSource]="dataSource" class="productos-table" matSort>
          <!-- Imagen -->
          <ng-container matColumnDef="imagen">
            <mat-header-cell *matHeaderCellDef class="imagen-column">
              <mat-icon>image</mat-icon>
              Imagen
            </mat-header-cell>
            <mat-cell *matCellDef="let producto" class="imagen-column">
              <div class="product-image-container">
                <!-- Imagen con múltiples fallbacks -->
                <img [src]="getImagenUrlOrDefault(producto.imagen)" 
                     [alt]="producto.nombre"
                     class="product-image"
                     (error)="onImageError($event, producto)"
                     loading="lazy">
                
                <!-- Placeholder de respaldo (oculto por defecto) -->
                <div class="no-image-placeholder" 
                     style="display: none;"
                     [style.display]="shouldShowPlaceholder(producto) ? 'flex' : 'none'">
                  <mat-icon>image_not_supported</mat-icon>
                </div>
              </div>
            </mat-cell>
          </ng-container>

          <!-- Código -->
          <ng-container matColumnDef="codigo">
            <mat-header-cell *matHeaderCellDef mat-sort-header>
              <mat-icon>code</mat-icon>
              Código
            </mat-header-cell>
            <mat-cell *matCellDef="let producto">
              <span class="codigo-badge">{{producto.codigo}}</span>
            </mat-cell>
          </ng-container>

          <!-- Nombre -->
          <ng-container matColumnDef="nombre">
            <mat-header-cell *matHeaderCellDef mat-sort-header>
              <mat-icon>shopping_bag</mat-icon>
              Producto
            </mat-header-cell>
            <mat-cell *matCellDef="let producto">
              <div class="product-info">
                <span class="product-name">{{producto.nombre}}</span>
                <span *ngIf="producto.descripcion" class="product-description">
                  {{producto.descripcion | slice:0:50}}{{producto.descripcion && producto.descripcion.length > 50 ? '...' : ''}}
                </span>
              </div>
            </mat-cell>
          </ng-container>

          <!-- Stock -->
          <ng-container matColumnDef="cantidad">
            <mat-header-cell *matHeaderCellDef mat-sort-header>
              <mat-icon>inventory</mat-icon>
              Stock
            </mat-header-cell>
            <mat-cell *matCellDef="let producto">
              <span class="stock-badge" 
                    [ngClass]="getStockClass(producto.cantidad || 0)">
                <mat-icon class="stock-icon">{{getStockIcon(producto.cantidad || 0)}}</mat-icon>
                {{producto.cantidad || 0}}
              </span>
            </mat-cell>
          </ng-container>

          <!-- Precio -->
          <ng-container matColumnDef="precioVenta">
            <mat-header-cell *matHeaderCellDef mat-sort-header>
              <mat-icon>sell</mat-icon>
              Precio
            </mat-header-cell>
            <mat-cell *matCellDef="let producto">
              <div class="price-info">
                <span class="price-venta">{{producto.precioVenta | currency:'PEN':'symbol':'1.2-2'}}</span>
                <span *ngIf="producto.costoCompra" class="price-costo">
                  Costo: {{producto.costoCompra | currency:'PEN':'symbol':'1.2-2'}}
                </span>
              </div>
            </mat-cell>
          </ng-container>

          <!-- Categoría -->
          <ng-container matColumnDef="categoria">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>category</mat-icon>
              Categoría
            </mat-header-cell>
            <mat-cell *matCellDef="let producto">
              <mat-chip *ngIf="producto.categoria" class="categoria-chip">
                {{producto.categoria.nombre}}
              </mat-chip>
              <span *ngIf="!producto.categoria" class="no-categoria">Sin categoría</span>
            </mat-cell>
          </ng-container>

          <!-- Estado -->
          <ng-container matColumnDef="estado">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>toggle_on</mat-icon>
              Estado
            </mat-header-cell>
            <mat-cell *matCellDef="let producto">
              <mat-chip [ngClass]="producto.estado ? 'estado-activo' : 'estado-inactivo'">
                <mat-icon>{{producto.estado ? 'check_circle' : 'cancel'}}</mat-icon>
                {{producto.estado ? 'Activo' : 'Inactivo'}}
              </mat-chip>
            </mat-cell>
          </ng-container>

          <!-- Fecha -->
          <ng-container matColumnDef="fechaCreacion">
            <mat-header-cell *matHeaderCellDef mat-sort-header>
              <mat-icon>schedule</mat-icon>
              Creado
            </mat-header-cell>
            <mat-cell *matCellDef="let producto">
              {{producto.fechaCreacion | date:'dd/MM/yyyy'}}
            </mat-cell>
          </ng-container>

          <!-- Acciones -->
          <ng-container matColumnDef="acciones">
            <mat-header-cell *matHeaderCellDef class="acciones-column">
              <mat-icon>settings</mat-icon>
              Acciones
            </mat-header-cell>
            <mat-cell *matCellDef="let producto" class="acciones-column">
              <div class="action-buttons">
                <button mat-icon-button 
                        color="primary" 
                        (click)="openDialog(producto)"
                        matTooltip="Editar producto">
                  <mat-icon>edit</mat-icon>
                </button>
                
                <button mat-icon-button 
                        [color]="producto.estado ? 'warn' : 'accent'"
                        (click)="toggleEstado(producto)"
                        [matTooltip]="producto.estado ? 'Desactivar' : 'Activar'">
                  <mat-icon>{{producto.estado ? 'toggle_off' : 'toggle_on'}}</mat-icon>
                </button>
                
                <button mat-icon-button 
                        color="warn" 
                        (click)="eliminarProducto(producto)"
                        matTooltip="Eliminar producto">
                  <mat-icon>delete</mat-icon>
                </button>
              </div>
            </mat-cell>
          </ng-container>

          <mat-header-row *matHeaderRowDef="columnas"></mat-header-row>
          <mat-row *matRowDef="let row; columns: columnas;" class="producto-row"></mat-row>
        </mat-table>

        <!-- Paginación -->
        <mat-paginator [pageSizeOptions]="[5, 10, 20, 50]"
                       [pageSize]="10"
                       showFirstLastButtons>
        </mat-paginator>
      </div>

      <!-- Mensaje cuando no hay datos -->
      <div *ngIf="dataSource.data.length === 0" class="no-data">
        <mat-icon class="no-data-icon">inventory_2</mat-icon>
        <h3>No hay productos registrados</h3>
        <p>Comienza agregando tu primer producto al catálogo</p>
        <button mat-raised-button color="primary" (click)="openDialog()">
          <mat-icon>add</mat-icon>
          Agregar Producto
        </button>
      </div>
    </div>
  `,
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit, AfterViewInit {
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  
  dataSource = new MatTableDataSource<Producto>();
  columnas: string[] = ['imagen', 'codigo', 'nombre', 'cantidad', 'precioVenta', 'categoria', 'estado', 'fechaCreacion', 'acciones'];
  filtroTexto = '';
  failedImages = new Set<string>(); // Track de imágenes que fallaron

  constructor(
    private productoService: ProductoService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  cargarProductos(): void {
    // Limpiar el estado de imágenes fallidas
    this.failedImages.clear();
    
    this.productoService.listar().subscribe({
      next: (productos) => {
        this.dataSource.data = productos;
      },
      error: (error) => {
        console.error('Error al cargar productos:', error);
        this.snackBar.open('Error al cargar productos', 'Cerrar', { duration: 3000 });
      }
    });
  }

  aplicarFiltro(): void {
    const filtro = this.filtroTexto.toLowerCase();
    this.dataSource.filter = filtro;
    
    this.dataSource.filterPredicate = (producto: Producto, filter: string) => {
      const searchText = (
        producto.codigo?.toLowerCase() + ' ' +
        producto.nombre?.toLowerCase() + ' ' +
        producto.descripcion?.toLowerCase() + ' ' +
        producto.categoria?.nombre?.toLowerCase()
      ).trim();
      
      return searchText.includes(filter);
    };

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openDialog(producto?: Producto): void {
    const dialogRef = this.dialog.open(ProductoDialogComponent, {
      width: '800px',
      maxHeight: '90vh',
      data: { producto: producto },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cargarProductos();
      }
    });
  }

  toggleEstado(producto: Producto): void {
    if (producto.estado) {
      this.productoService.desactivar(producto.id!).subscribe({
        next: () => {
          this.snackBar.open('Producto desactivado', 'Cerrar', { duration: 3000 });
          this.cargarProductos();
        },
        error: (error) => {
          console.error('Error al desactivar producto:', error);
          this.snackBar.open('Error al desactivar producto', 'Cerrar', { duration: 3000 });
        }
      });
    } else {
      // Reactivar producto
      const productoActualizado = { ...producto, estado: true };
      this.productoService.actualizar(producto.id!, productoActualizado).subscribe({
        next: () => {
          this.snackBar.open('Producto activado', 'Cerrar', { duration: 3000 });
          this.cargarProductos();
        },
        error: (error) => {
          console.error('Error al activar producto:', error);
          this.snackBar.open('Error al activar producto', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }

  eliminarProducto(producto: Producto): void {
    if (confirm(`¿Está seguro de eliminar el producto "${producto.nombre}"?`)) {
      this.productoService.eliminar(producto.id!).subscribe({
        next: () => {
          this.snackBar.open('Producto eliminado', 'Cerrar', { duration: 3000 });
          this.cargarProductos();
        },
        error: (error) => {
          console.error('Error al eliminar producto:', error);
          this.snackBar.open('Error al eliminar producto', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }

  getImagenUrl(nombreImagen: string): string {
    return this.productoService.getImagenUrl(nombreImagen);
  }

  getImagenUrlOrDefault(nombreImagen?: string): string {
    return this.productoService.getImagenUrlOrDefault(nombreImagen);
  }

  onImageError(event: any, producto?: Producto): void {
    const imageUrl = event.target.src;
    console.log(`Error cargando imagen: ${imageUrl}`);
    
    // Marcar esta imagen como fallida
    if (producto?.id) {
      this.failedImages.add(producto.id.toString());
    }
    
    // Si falla la imagen del servidor, usar la imagen por defecto
    if (imageUrl.includes('imagenes/') || imageUrl.includes('localhost:8085')) {
      event.target.src = '/imagen.jpg'; // Imagen por defecto
    } else if (imageUrl.includes('/imagen.jpg')) {
      // Si también falla la imagen por defecto, ocultar imagen y mostrar placeholder
      console.log('Error cargando imagen por defecto, mostrando placeholder');
      event.target.style.display = 'none';
    }
  }

  shouldShowPlaceholder(producto: Producto): boolean {
    return producto.id ? this.failedImages.has(producto.id.toString()) : false;
  }

  getStockClass(cantidad: number): string {
    if (cantidad <= 5) return 'stock-critico';
    if (cantidad <= 15) return 'stock-bajo';
    if (cantidad <= 50) return 'stock-medio';
    return 'stock-alto';
  }

  getStockIcon(cantidad: number): string {
    if (cantidad <= 5) return 'error';
    if (cantidad <= 15) return 'warning';
    return 'check_circle';
  }

  getProductosActivos(): number {
    return this.dataSource.data.filter(p => p.estado).length;
  }

  getStockBajo(): number {
    return this.dataSource.data.filter(p => (p.cantidad || 0) <= 10).length;
  }
}
