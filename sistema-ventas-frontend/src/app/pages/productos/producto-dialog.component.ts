import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ProductoService} from '../../services/producto.service';
import {CategoriaService} from '../../services/categoria.service';
import {Producto} from '../../modelo/Producto';
import {Categoria} from '../../modelo/Categoria';
import {CommonModule} from '@angular/common';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-producto-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatCheckboxModule
  ],
  template: `
    <div class="producto-dialog">
      <h2 mat-dialog-title class="dialog-title">
        <mat-icon class="title-icon">{{ data.producto ? 'edit' : 'add_circle' }}</mat-icon>
        {{ data.producto ? 'Editar Producto' : 'Nuevo Producto' }}
      </h2>

      <form [formGroup]="productoForm" (ngSubmit)="onSubmit()" class="product-form">
        <div class="form-grid">
          <!-- Código -->
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Código</mat-label>
            <input matInput formControlName="codigo" placeholder="Ej: PROD001">
            <mat-icon matSuffix>code</mat-icon>
            <mat-error *ngIf="productoForm.get('codigo')?.hasError('required')">
              El código es requerido
            </mat-error>
          </mat-form-field>

          <!-- Nombre -->
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Nombre del Producto</mat-label>
            <input matInput formControlName="nombre" placeholder="Ej: Laptop Gaming">
            <mat-icon matSuffix>shopping_bag</mat-icon>
            <mat-error *ngIf="productoForm.get('nombre')?.hasError('required')">
              El nombre es requerido
            </mat-error>
          </mat-form-field>

          <!-- Descripción -->
          <mat-form-field appearance="outline" class="full-width description-field">
            <mat-label>Descripción</mat-label>
            <textarea matInput formControlName="descripcion" rows="3" 
                     placeholder="Descripción detallada del producto"></textarea>
            <mat-icon matSuffix>description</mat-icon>
          </mat-form-field>

          <!-- Categoría -->
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Categoría</mat-label>
            <mat-select formControlName="categoriaId">
              <mat-option *ngFor="let categoria of categorias" [value]="categoria.id">
                {{ categoria.nombre }}
              </mat-option>
            </mat-select>
            <mat-icon matSuffix>category</mat-icon>
            <mat-error *ngIf="productoForm.get('categoriaId')?.hasError('required')">
              Seleccione una categoría
            </mat-error>
          </mat-form-field>

          <!-- Cantidad -->
          <mat-form-field appearance="outline">
            <mat-label>Cantidad en Stock</mat-label>
            <input matInput type="number" formControlName="cantidad" min="0">
            <mat-icon matSuffix>inventory</mat-icon>
            <mat-error *ngIf="productoForm.get('cantidad')?.hasError('min')">
              La cantidad debe ser mayor o igual a 0
            </mat-error>
          </mat-form-field>

          <!-- Precio de Venta -->
          <mat-form-field appearance="outline">
            <mat-label>Precio de Venta</mat-label>
            <input matInput type="number" formControlName="precioVenta" min="0" step="0.01">
            <span matPrefix>S/ </span>
            <mat-icon matSuffix>sell</mat-icon>
            <mat-error *ngIf="productoForm.get('precioVenta')?.hasError('min')">
              El precio debe ser mayor a 0
            </mat-error>
          </mat-form-field>

          <!-- Costo de Compra -->
          <mat-form-field appearance="outline">
            <mat-label>Costo de Compra</mat-label>
            <input matInput type="number" formControlName="costoCompra" min="0" step="0.01">
            <span matPrefix>S/ </span>
            <mat-icon matSuffix>shopping_cart</mat-icon>
            <mat-error *ngIf="productoForm.get('costoCompra')?.hasError('min')">
              El costo debe ser mayor o igual a 0
            </mat-error>
          </mat-form-field>
        </div>

        <!-- Sección de Imagen -->
        <div class="image-section">
          <h3 class="image-section-title">
            <mat-icon>image</mat-icon>
            Imagen del Producto
          </h3>

          <!-- Área de Upload -->
          <div class="upload-container" 
               (dragover)="onDragOver($event)" 
               (dragleave)="onDragLeave($event)"
               (drop)="onDrop($event)"
               [class.drag-over]="isDragOver"
               (click)="fileInput.click()">
            
            <!-- Vista cuando no hay imagen -->
            <div *ngIf="!imagenPreview" class="upload-placeholder">
              <mat-icon class="upload-icon">cloud_upload</mat-icon>
              <p class="upload-text">Arrastra una imagen aquí o haz clic para seleccionar</p>
              <p class="upload-hint">Formatos: JPG, PNG, GIF (Máx. 5MB)</p>
            </div>

            <!-- Vista con imagen -->
            <div *ngIf="imagenPreview" class="image-preview-container">
              <img [src]="imagenPreview" 
                   alt="Preview" 
                   class="image-preview"
                   (error)="onImagePreviewError($event)">
              <div class="image-overlay">
                <button type="button" mat-icon-button class="remove-image-btn" 
                        (click)="removeImage($event)">
                  <mat-icon>close</mat-icon>
                </button>
              </div>
            </div>
          </div>

          <input #fileInput type="file" 
                 (change)="onFileSelected($event)" 
                 accept="image/*" 
                 style="display: none;">
        </div>

        <!-- Botones -->
        <div class="dialog-actions">
          <button type="button" mat-stroked-button (click)="onCancel()" class="cancel-btn">
            <mat-icon>cancel</mat-icon>
            Cancelar
          </button>
          <button type="submit" mat-raised-button color="primary" 
                  [disabled]="!productoForm.valid || isLoading" class="save-btn">
            <mat-icon *ngIf="!isLoading">{{ data.producto ? 'save' : 'add' }}</mat-icon>
            <mat-icon *ngIf="isLoading" class="loading-icon">hourglass_empty</mat-icon>
            {{ isLoading ? 'Guardando...' : (data.producto ? 'Actualizar' : 'Crear Producto') }}
          </button>
        </div>
      </form>
    </div>
  `,
  styles: [`
    .producto-dialog {
      width: 100%;
      max-width: 600px;
      padding: 0;
    }

    .dialog-title {
      display: flex;
      align-items: center;
      gap: 12px;
      color: #1976d2;
      margin-bottom: 24px;
      font-size: 24px;
      font-weight: 500;
    }

    .title-icon {
      font-size: 28px;
      width: 28px;
      height: 28px;
    }

    .product-form {
      display: flex;
      flex-direction: column;
      gap: 20px;
    }

    .form-grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 16px;
    }

    .full-width {
      grid-column: 1 / -1;
    }

    .description-field textarea {
      resize: vertical;
      min-height: 80px;
    }

    .image-section {
      margin-top: 24px;
    }

    .image-section-title {
      display: flex;
      align-items: center;
      gap: 8px;
      color: #424242;
      font-size: 16px;
      font-weight: 500;
      margin-bottom: 16px;
    }

    .upload-container {
      border: 2px dashed #ddd;
      border-radius: 12px;
      padding: 32px;
      text-align: center;
      cursor: pointer;
      transition: all 0.3s ease;
      background: #fafafa;
      position: relative;
      min-height: 200px;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .upload-container:hover {
      border-color: #1976d2;
      background: #f3f8ff;
    }

    .upload-container.drag-over {
      border-color: #1976d2;
      background: #e3f2fd;
      transform: scale(1.02);
    }

    .upload-placeholder {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 12px;
    }

    .upload-icon {
      font-size: 48px;
      width: 48px;
      height: 48px;
      color: #1976d2;
    }

    .upload-text {
      font-size: 16px;
      color: #424242;
      margin: 0;
      font-weight: 500;
    }

    .upload-hint {
      font-size: 14px;
      color: #757575;
      margin: 0;
    }

    .image-preview-container {
      position: relative;
      width: 100%;
      height: 200px;
    }

    .image-preview {
      width: 100%;
      height: 100%;
      object-fit: cover;
      border-radius: 8px;
    }

    .image-overlay {
      position: absolute;
      top: 8px;
      right: 8px;
    }

    .remove-image-btn {
      background: rgba(0, 0, 0, 0.7);
      color: white;
      width: 32px;
      height: 32px;
    }

    .remove-image-btn:hover {
      background: rgba(244, 67, 54, 0.8);
    }

    .dialog-actions {
      display: flex;
      justify-content: flex-end;
      gap: 12px;
      margin-top: 32px;
      padding-top: 24px;
      border-top: 1px solid #e0e0e0;
    }

    .cancel-btn {
      min-width: 120px;
    }

    .save-btn {
      min-width: 140px;
    }

    .loading-icon {
      animation: spin 1s linear infinite;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }

    @media (max-width: 600px) {
      .form-grid {
        grid-template-columns: 1fr;
      }
      
      .upload-container {
        padding: 24px 16px;
        min-height: 150px;
      }
      
      .dialog-actions {
        flex-direction: column;
      }
      
      .cancel-btn, .save-btn {
        width: 100%;
      }
    }
  `]
})
export class ProductoDialogComponent implements OnInit {
  productoForm: FormGroup;
  categorias: Categoria[] = [];
  selectedFile: File | null = null;
  imagenPreview: string | null = null;
  isDragOver = false;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ProductoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { producto?: Producto },
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private snackBar: MatSnackBar
  ) {
    this.productoForm = this.fb.group({
      codigo: ['', [Validators.required]],
      nombre: ['', [Validators.required]],
      descripcion: [''],
      categoriaId: ['', [Validators.required]],
      cantidad: [0, [Validators.min(0)]],
      precioVenta: [0, [Validators.min(0.01)]],
      costoCompra: [0, [Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.cargarCategorias();
    
    if (this.data.producto) {
      this.cargarDatosProducto();
    }
  }

  cargarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (categorias) => {
        this.categorias = categorias.filter(cat => cat.estado);
      },
      error: (error) => {
        console.error('Error al cargar categorías:', error);
        this.snackBar.open('Error al cargar categorías', 'Cerrar', { duration: 3000 });
      }
    });
  }

  cargarDatosProducto(): void {
    if (this.data.producto) {
      const producto = this.data.producto;
      this.productoForm.patchValue({
        codigo: producto.codigo,
        nombre: producto.nombre,
        descripcion: producto.descripcion || '',
        categoriaId: producto.categoria?.id || '',
        cantidad: producto.cantidad || 0,
        precioVenta: producto.precioVenta || 0,
        costoCompra: producto.costoCompra || 0
      });

      // Cargar imagen si existe, sino usar imagen por defecto
      if (producto.imagen) {
        this.imagenPreview = this.productoService.getImagenUrl(producto.imagen);
      } else {
        this.imagenPreview = '/imagen.jpg'; // Imagen por defecto desde public
      }
    }
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.processFile(file);
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = false;
    
    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.processFile(files[0]);
    }
  }

  processFile(file: File): void {
    // Validar tipo de archivo
    if (!file.type.startsWith('image/')) {
      this.snackBar.open('Solo se permiten archivos de imagen', 'Cerrar', { duration: 3000 });
      return;
    }

    // Validar tamaño (5MB)
    if (file.size > 5 * 1024 * 1024) {
      this.snackBar.open('La imagen no debe superar los 5MB', 'Cerrar', { duration: 3000 });
      return;
    }

    this.selectedFile = file;

    // Crear preview
    const reader = new FileReader();
    reader.onload = (e) => {
      this.imagenPreview = e.target?.result as string;
    };
    reader.readAsDataURL(file);
  }

  removeImage(event: Event): void {
    event.stopPropagation();
    this.selectedFile = null;
    this.imagenPreview = null;
  }

  onImagePreviewError(event: any): void {
    console.log('Error cargando preview de imagen');
    // Si falla el preview, usar imagen por defecto
    if (event.target.src.includes('imagenes/')) {
      event.target.src = '/imagen.jpg';
    } else {
      // Si también falla la imagen por defecto, remover el preview
      this.imagenPreview = null;
      this.snackBar.open('Error al cargar la imagen', 'Cerrar', { duration: 3000 });
    }
  }

  onSubmit(): void {
    if (this.productoForm.valid) {
      this.isLoading = true;
      const formValue = this.productoForm.value;
      
      const producto: Producto = {
        codigo: formValue.codigo,
        nombre: formValue.nombre,
        descripcion: formValue.descripcion,
        cantidad: formValue.cantidad,
        precioVenta: formValue.precioVenta,
        costoCompra: formValue.costoCompra,
        categoria: { id: formValue.categoriaId } as Categoria
      };

      if (this.data.producto) {
        // Actualizar producto existente
        this.productoService.actualizarConImagen(this.data.producto.id!, producto, this.selectedFile || undefined)
          .subscribe({
            next: (result) => {
              this.snackBar.open('Producto actualizado exitosamente', 'Cerrar', { duration: 3000 });
              this.dialogRef.close(result);
            },
            error: (error) => {
              console.error('Error al actualizar producto:', error);
              this.snackBar.open('Error al actualizar el producto', 'Cerrar', { duration: 3000 });
              this.isLoading = false;
            }
          });
      } else {
        // Crear nuevo producto
        this.productoService.crearConImagen(producto, this.selectedFile || undefined)
          .subscribe({
            next: (result) => {
              this.snackBar.open('Producto creado exitosamente', 'Cerrar', { duration: 3000 });
              this.dialogRef.close(result);
            },
            error: (error) => {
              console.error('Error al crear producto:', error);
              this.snackBar.open('Error al crear el producto', 'Cerrar', { duration: 3000 });
              this.isLoading = false;
            }
          });
      }
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
