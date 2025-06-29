import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {Categoria} from '../../modelo/Categoria';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-categoria-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    NgIf
  ],
  template: `
    <h2 mat-dialog-title class="text-xl font-semibold mb-4">
      {{ data ? 'Editar Categoría' : 'Nueva Categoría' }}
    </h2>

    <form [formGroup]="categoriaForm" (ngSubmit)="onSubmit()">
      <mat-dialog-content class="space-y-4">
        <!-- Nombre -->
        <mat-form-field appearance="outline" class="w-full">
          <mat-label>Nombre de la categoría</mat-label>
          <input matInput 
                 formControlName="nombre" 
                 placeholder="Ingrese el nombre de la categoría"
                 maxlength="100">
          <mat-error *ngIf="categoriaForm.get('nombre')?.hasError('required')">
            El nombre es obligatorio
          </mat-error>
          <mat-error *ngIf="categoriaForm.get('nombre')?.hasError('minlength')">
            El nombre debe tener al menos 2 caracteres
          </mat-error>
          <mat-error *ngIf="categoriaForm.get('nombre')?.hasError('maxlength')">
            El nombre no puede exceder 100 caracteres
          </mat-error>
        </mat-form-field>

        <!-- Estado (solo para edición) -->
        <div *ngIf="data">
          <mat-checkbox formControlName="estado" class="text-gray-700">
            Categoría activa
          </mat-checkbox>
        </div>
      </mat-dialog-content>

      <mat-dialog-actions align="end" class="space-x-2 pt-4">
        <button mat-button 
                type="button" 
                (click)="onCancel()"
                class="text-gray-600">
          Cancelar
        </button>
        <button mat-raised-button 
                color="primary" 
                type="submit"
                [disabled]="categoriaForm.invalid"
                class="bg-blue-600 hover:bg-blue-700">
          {{ data ? 'Actualizar' : 'Crear' }}
        </button>
      </mat-dialog-actions>
    </form>
  `
})
export class CategoriaDialogComponent implements OnInit {
  categoriaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<CategoriaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Categoria | null
  ) {
    this.categoriaForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      estado: [true]
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.categoriaForm.patchValue({
        nombre: this.data.nombre,
        estado: this.data.estado ?? true
      });
    }
  }

  onSubmit(): void {
    if (this.categoriaForm.valid) {
      const categoria: Categoria = {
        ...this.data,
        ...this.categoriaForm.value
      };
      this.dialogRef.close(categoria);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
