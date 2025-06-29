import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {Cliente} from '../../modelo/Cliente';
import {NgIf} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-cliente-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    NgIf,
    MatIconModule
  ],
  template: `
    <h2 mat-dialog-title class="text-xl font-semibold mb-4 flex items-center">
      <mat-icon class="mr-2">{{ data ? 'edit' : 'person_add' }}</mat-icon>
      {{ data ? 'Editar Cliente' : 'Nuevo Cliente' }}
    </h2>

    <form [formGroup]="clienteForm" (ngSubmit)="onSubmit()">
      <mat-dialog-content class="space-y-4 max-h-96 overflow-y-auto">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <!-- DNI -->
          <mat-form-field appearance="outline" class="w-full">
            <mat-label>DNI</mat-label>
            <input matInput 
                   formControlName="dni" 
                   placeholder="12345678"
                   maxlength="15">
            <mat-icon matSuffix>badge</mat-icon>
            <mat-error *ngIf="clienteForm.get('dni')?.hasError('required')">
              El DNI es obligatorio
            </mat-error>
            <mat-error *ngIf="clienteForm.get('dni')?.hasError('pattern')">
              El DNI debe contener solo números
            </mat-error>
            <mat-error *ngIf="clienteForm.get('dni')?.hasError('minlength')">
              El DNI debe tener al menos 8 dígitos
            </mat-error>
          </mat-form-field>

          <!-- Nombre -->
          <mat-form-field appearance="outline" class="w-full">
            <mat-label>Nombre</mat-label>
            <input matInput 
                   formControlName="nombre" 
                   placeholder="Juan"
                   maxlength="50">
            <mat-icon matSuffix>person</mat-icon>
            <mat-error *ngIf="clienteForm.get('nombre')?.hasError('required')">
              El nombre es obligatorio
            </mat-error>
            <mat-error *ngIf="clienteForm.get('nombre')?.hasError('minlength')">
              El nombre debe tener al menos 2 caracteres
            </mat-error>
          </mat-form-field>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <!-- Apellido -->
          <mat-form-field appearance="outline" class="w-full">
            <mat-label>Apellido</mat-label>
            <input matInput 
                   formControlName="apellido" 
                   placeholder="Pérez"
                   maxlength="50">
            <mat-icon matSuffix>person_outline</mat-icon>
          </mat-form-field>

          <!-- Email -->
          <mat-form-field appearance="outline" class="w-full">
            <mat-label>Email</mat-label>
            <input matInput 
                   formControlName="email" 
                   type="email"
                   placeholder="juan@ejemplo.com"
                   maxlength="100">
            <mat-icon matSuffix>email</mat-icon>
            <mat-error *ngIf="clienteForm.get('email')?.hasError('required')">
              El email es obligatorio
            </mat-error>
            <mat-error *ngIf="clienteForm.get('email')?.hasError('email')">
              Ingrese un email válido
            </mat-error>
          </mat-form-field>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <!-- Teléfono -->
          <mat-form-field appearance="outline" class="w-full">
            <mat-label>Teléfono</mat-label>
            <input matInput 
                   formControlName="telefono" 
                   placeholder="987654321"
                   maxlength="20">
            <mat-icon matSuffix>phone</mat-icon>
            <mat-error *ngIf="clienteForm.get('telefono')?.hasError('pattern')">
              El teléfono solo debe contener números, espacios, guiones y paréntesis
            </mat-error>
          </mat-form-field>

          <!-- Dirección -->
          <mat-form-field appearance="outline" class="w-full">
            <mat-label>Dirección</mat-label>
            <input matInput 
                   formControlName="direccion" 
                   placeholder="Av. Principal 123"
                   maxlength="200">
            <mat-icon matSuffix>home</mat-icon>
          </mat-form-field>
        </div>

        <!-- Estado (solo para edición) -->
        <div *ngIf="data" class="pt-2">
          <mat-checkbox formControlName="activo" class="text-gray-700">
            Cliente activo
          </mat-checkbox>
        </div>
      </mat-dialog-content>

      <mat-dialog-actions align="end" class="space-x-2 pt-4">
        <button mat-button 
                type="button" 
                (click)="onCancel()"
                class="text-gray-600">
          <mat-icon>close</mat-icon>
          Cancelar
        </button>
        <button mat-raised-button 
                color="primary" 
                type="submit"
                [disabled]="clienteForm.invalid || guardando"
                class="bg-blue-600 hover:bg-blue-700">
          <mat-icon>{{ data ? 'save' : 'person_add' }}</mat-icon>
          {{ guardando ? 'Guardando...' : (data ? 'Actualizar' : 'Crear') }}
        </button>
      </mat-dialog-actions>
    </form>
  `
})
export class ClienteDialogComponent implements OnInit {
  clienteForm: FormGroup;
  guardando = false;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ClienteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Cliente | null
  ) {
    this.clienteForm = this.fb.group({
      dni: ['', [
        Validators.required, 
        Validators.pattern(/^\d+$/),
        Validators.minLength(8)
      ]],
      nombre: ['', [
        Validators.required, 
        Validators.minLength(2),
        Validators.maxLength(50)
      ]],
      apellido: ['', [Validators.maxLength(50)]],
      email: ['', [
        Validators.required, 
        Validators.email,
        Validators.maxLength(100)
      ]],
      telefono: ['', [
        Validators.pattern(/^[\d\s\-\(\)]+$/)
      ]],
      direccion: ['', [Validators.maxLength(200)]],
      activo: [true]
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.clienteForm.patchValue({
        dni: this.data.dni,
        nombre: this.data.nombre,
        apellido: this.data.apellido || '',
        email: this.data.email,
        telefono: this.data.telefono || '',
        direccion: this.data.direccion || '',
        activo: this.data.activo ?? true
      });
    }
  }

  onSubmit(): void {
    if (this.clienteForm.valid) {
      this.guardando = true;
      
      const cliente: Cliente = {
        ...this.data,
        ...this.clienteForm.value
      };

      // Limpiar campos vacíos opcionales
      if (!cliente.apellido?.trim()) cliente.apellido = undefined;
      if (!cliente.telefono?.trim()) cliente.telefono = undefined;
      if (!cliente.direccion?.trim()) cliente.direccion = undefined;

      this.dialogRef.close(cliente);
    } else {
      // Marcar todos los campos como tocados para mostrar errores
      this.clienteForm.markAllAsTouched();
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  // Método para obtener el mensaje de error de un campo específico
  getErrorMessage(fieldName: string): string {
    const field = this.clienteForm.get(fieldName);
    if (field?.hasError('required')) {
      return `${this.getFieldLabel(fieldName)} es obligatorio`;
    }
    if (field?.hasError('email')) {
      return 'Ingrese un email válido';
    }
    if (field?.hasError('pattern')) {
      return this.getPatternError(fieldName);
    }
    if (field?.hasError('minlength')) {
      const requiredLength = field.errors?.['minlength'].requiredLength;
      return `Debe tener al menos ${requiredLength} caracteres`;
    }
    return '';
  }

  private getFieldLabel(fieldName: string): string {
    const labels: { [key: string]: string } = {
      dni: 'El DNI',
      nombre: 'El nombre',
      email: 'El email',
      telefono: 'El teléfono',
      direccion: 'La dirección'
    };
    return labels[fieldName] || 'El campo';
  }

  private getPatternError(fieldName: string): string {
    if (fieldName === 'dni') {
      return 'El DNI debe contener solo números';
    }
    if (fieldName === 'telefono') {
      return 'El teléfono solo debe contener números, espacios, guiones y paréntesis';
    }
    return 'Formato inválido';
  }
}
