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
import {NgClass, NgIf, DatePipe} from "@angular/common";
import {Cliente} from "../../modelo/Cliente";
import {ClienteService} from "../../services/cliente.service";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ClienteDialogComponent} from "./cliente-dialog.component";
import {MatSort} from "@angular/material/sort";
import {MatTooltip} from "@angular/material/tooltip";

@Component({
  selector: 'app-clientes',
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
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent implements OnInit {
  @ViewChild(MatSort) sort!: MatSort;
  
  dataSource = new MatTableDataSource<Cliente>();
  columnas: string[] = ['id', 'dni', 'nombre', 'apellido', 'email', 'telefono', 'fechaRegistro', 'activo', 'acciones'];
  cargando = false;
  filtro = '';

  constructor(
    private clienteService: ClienteService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.cargarClientes();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  cargarClientes(): void {
    this.cargando = true;
    this.clienteService.listar().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener clientes', err);
        this.mostrarMensaje('Error al cargar los clientes');
        this.cargando = false;
      }
    });
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }

  abrirDialogo(cliente?: Cliente): void {
    const dialogRef = this.dialog.open(ClienteDialogComponent, {
      width: '600px',
      data: cliente ? { ...cliente } : null
    });

    dialogRef.afterClosed().subscribe(resultado => {
      if (resultado) {
        if (resultado.id) {
          this.actualizarCliente(resultado);
        } else {
          this.crearCliente(resultado);
        }
      }
    });
  }

  crearCliente(cliente: Cliente): void {
    this.clienteService.crear(cliente).subscribe({
      next: () => {
        this.mostrarMensaje('Cliente creado exitosamente');
        this.cargarClientes();
      },
      error: (err) => {
        console.error('Error al crear cliente', err);
        let mensaje = 'Error al crear el cliente';
        if (err.error?.message) {
          mensaje = err.error.message;
        } else if (err.status === 409) {
          mensaje = 'Ya existe un cliente con ese DNI o email';
        }
        this.mostrarMensaje(mensaje);
      }
    });
  }

  actualizarCliente(cliente: Cliente): void {
    if (cliente.id) {
      this.clienteService.actualizar(cliente.id, cliente).subscribe({
        next: () => {
          this.mostrarMensaje('Cliente actualizado exitosamente');
          this.cargarClientes();
        },
        error: (err) => {
          console.error('Error al actualizar cliente', err);
          let mensaje = 'Error al actualizar el cliente';
          if (err.error?.message) {
            mensaje = err.error.message;
          } else if (err.status === 409) {
            mensaje = 'Ya existe un cliente con ese DNI o email';
          }
          this.mostrarMensaje(mensaje);
        }
      });
    }
  }

  eliminarCliente(cliente: Cliente): void {
    if (cliente.id && confirm(`¿Está seguro de eliminar al cliente ${cliente.nombre} ${cliente.apellido || ''}?`)) {
      this.clienteService.eliminar(cliente.id).subscribe({
        next: () => {
          this.mostrarMensaje('Cliente eliminado exitosamente');
          this.cargarClientes();
        },
        error: (err) => {
          console.error('Error al eliminar cliente', err);
          this.mostrarMensaje('Error al eliminar el cliente');
        }
      });
    }
  }

  toggleActivo(cliente: Cliente): void {
    if (cliente.id) {
      const clienteActualizado = { ...cliente, activo: !cliente.activo };
      this.clienteService.actualizar(cliente.id, clienteActualizado).subscribe({
        next: () => {
          const estado = clienteActualizado.activo ? 'activado' : 'desactivado';
          this.mostrarMensaje(`Cliente ${estado} exitosamente`);
          this.cargarClientes();
        },
        error: (err) => {
          console.error('Error al cambiar estado del cliente', err);
          this.mostrarMensaje('Error al cambiar el estado del cliente');
        }
      });
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
