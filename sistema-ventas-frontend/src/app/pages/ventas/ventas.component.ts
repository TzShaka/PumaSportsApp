import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatTooltipModule } from '@angular/material/tooltip';

interface Venta {
  id: number;
  numero: string;
  cliente: string;
  fecha: string;
  hora: string;
  subtotal: number;
  igv: number;
  total: number;
  metodoPago: string;
  estado: string;
  vendedor: string;
}

@Component({
  selector: 'app-ventas',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MatTooltipModule
  ],
  template: `
    <div class="ventas-container">
      <!-- Header -->
      <div class="header-section">
        <div class="title-section">
          <h1 class="page-title">
            <mat-icon class="title-icon">point_of_sale</mat-icon>
            Gestión de Ventas
          </h1>
          <p class="page-subtitle">Administra las ventas realizadas y genera reportes</p>
        </div>
        
        <button mat-raised-button color="primary" class="add-button">
          <mat-icon>add_shopping_cart</mat-icon>
          Nueva Venta
        </button>
      </div>

      <!-- Métricas de Ventas -->
      <div class="metrics-grid">
        <mat-card class="metric-card sales">
          <mat-card-content>
            <div class="metric-content">
              <div class="metric-info">
                <h3>Ventas Hoy</h3>
                <p class="metric-number">{{ getVentasHoy() }}</p>
                <small>+12% vs ayer</small>
              </div>
              <mat-icon class="metric-icon">trending_up</mat-icon>
            </div>
          </mat-card-content>
        </mat-card>

        <mat-card class="metric-card revenue">
          <mat-card-content>
            <div class="metric-content">
              <div class="metric-info">
                <h3>Ingresos Hoy</h3>
                <p class="metric-number">S/ {{ getIngresosHoy() | number:'1.2-2' }}</p>
                <small>+8% vs ayer</small>
              </div>
              <mat-icon class="metric-icon">monetization_on</mat-icon>
            </div>
          </mat-card-content>
        </mat-card>

        <mat-card class="metric-card average">
          <mat-card-content>
            <div class="metric-content">
              <div class="metric-info">
                <h3>Ticket Promedio</h3>
                <p class="metric-number">S/ {{ getTicketPromedio() | number:'1.2-2' }}</p>
                <small>+5% vs ayer</small>
              </div>
              <mat-icon class="metric-icon">receipt_long</mat-icon>
            </div>
          </mat-card-content>
        </mat-card>

        <mat-card class="metric-card month">
          <mat-card-content>
            <div class="metric-content">
              <div class="metric-info">
                <h3>Ventas del Mes</h3>
                <p class="metric-number">S/ {{ getVentasMes() | number:'1.2-2' }}</p>
                <small>+18% vs mes anterior</small>
              </div>
              <mat-icon class="metric-icon">calendar_month</mat-icon>
            </div>
          </mat-card-content>
        </mat-card>
      </div>

      <!-- Tabla de Ventas -->
      <div class="table-container">
        <mat-table [dataSource]="ventas" class="ventas-table">
          <!-- Número -->
          <ng-container matColumnDef="numero">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>receipt</mat-icon>
              Número
            </mat-header-cell>
            <mat-cell *matCellDef="let venta">
              <span class="numero-badge">{{venta.numero}}</span>
            </mat-cell>
          </ng-container>

          <!-- Cliente -->
          <ng-container matColumnDef="cliente">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>person</mat-icon>
              Cliente
            </mat-header-cell>
            <mat-cell *matCellDef="let venta">
              {{venta.cliente}}
            </mat-cell>
          </ng-container>

          <!-- Fecha y Hora -->
          <ng-container matColumnDef="fecha">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>schedule</mat-icon>
              Fecha / Hora
            </mat-header-cell>
            <mat-cell *matCellDef="let venta">
              <div class="fecha-info">
                <span class="fecha">{{venta.fecha}}</span>
                <span class="hora">{{venta.hora}}</span>
              </div>
            </mat-cell>
          </ng-container>

          <!-- Total -->
          <ng-container matColumnDef="total">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>attach_money</mat-icon>
              Total
            </mat-header-cell>
            <mat-cell *matCellDef="let venta">
              <span class="total-amount">S/ {{venta.total | number:'1.2-2'}}</span>
            </mat-cell>
          </ng-container>

          <!-- Método de Pago -->
          <ng-container matColumnDef="metodoPago">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>payment</mat-icon>
              Pago
            </mat-header-cell>
            <mat-cell *matCellDef="let venta">
              <mat-chip [ngClass]="getMetodoPagoClass(venta.metodoPago)">
                <mat-icon>{{getMetodoPagoIcon(venta.metodoPago)}}</mat-icon>
                {{venta.metodoPago}}
              </mat-chip>
            </mat-cell>
          </ng-container>

          <!-- Estado -->
          <ng-container matColumnDef="estado">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>info</mat-icon>
              Estado
            </mat-header-cell>
            <mat-cell *matCellDef="let venta">
              <mat-chip [ngClass]="getEstadoClass(venta.estado)">
                <mat-icon>{{getEstadoIcon(venta.estado)}}</mat-icon>
                {{venta.estado}}
              </mat-chip>
            </mat-cell>
          </ng-container>

          <!-- Acciones -->
          <ng-container matColumnDef="acciones">
            <mat-header-cell *matHeaderCellDef>
              <mat-icon>settings</mat-icon>
              Acciones
            </mat-header-cell>
            <mat-cell *matCellDef="let venta">
              <div class="action-buttons">
                <button mat-icon-button color="primary" matTooltip="Ver detalles">
                  <mat-icon>visibility</mat-icon>
                </button>
                <button mat-icon-button color="accent" matTooltip="Imprimir">
                  <mat-icon>print</mat-icon>
                </button>
              </div>
            </mat-cell>
          </ng-container>

          <mat-header-row *matHeaderRowDef="columnas"></mat-header-row>
          <mat-row *matRowDef="let row; columns: columnas;" class="venta-row"></mat-row>
        </mat-table>
      </div>

      <!-- Empty State -->
      <div *ngIf="ventas.length === 0" class="no-data">
        <mat-icon class="no-data-icon">point_of_sale</mat-icon>
        <h3>No hay ventas registradas</h3>
        <p>Las ventas aparecerán aquí cuando se realicen transacciones</p>
      </div>
    </div>
  `,
  styles: [`
    .ventas-container {
      padding: 24px;
      max-width: 1400px;
      margin: 0 auto;
      background: #fafafa;
      min-height: 100vh;
    }

    .header-section {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: 32px;
      padding: 24px;
      background: linear-gradient(135deg, #4caf50 0%, #45a049 100%);
      border-radius: 16px;
      color: white;
      box-shadow: 0 8px 24px rgba(76, 175, 80, 0.15);
    }

    .title-section h1 {
      margin: 0;
      font-size: 28px;
      font-weight: 600;
      display: flex;
      align-items: center;
      gap: 12px;
    }

    .title-icon {
      font-size: 32px;
      width: 32px;
      height: 32px;
    }

    .page-subtitle {
      margin: 8px 0 0 44px;
      font-size: 16px;
      opacity: 0.9;
    }

    .add-button {
      background: white;
      color: #4caf50;
      font-weight: 600;
      padding: 12px 24px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      transition: all 0.3s ease;
    }

    .metrics-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      margin-bottom: 32px;
    }

    .metric-card {
      border-radius: 12px;
      box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
      transition: transform 0.3s ease;
    }

    .metric-card:hover {
      transform: translateY(-4px);
    }

    .metric-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .metric-info h3 {
      margin: 0 0 8px 0;
      color: #666;
      font-size: 14px;
      font-weight: 500;
    }

    .metric-number {
      margin: 0 0 4px 0;
      font-size: 24px;
      font-weight: bold;
    }

    .metric-info small {
      color: #4caf50;
      font-weight: 500;
    }

    .metric-icon {
      font-size: 48px;
      width: 48px;
      height: 48px;
      opacity: 0.3;
    }

    .metric-card.sales .metric-number { color: #2196f3; }
    .metric-card.revenue .metric-number { color: #4caf50; }
    .metric-card.average .metric-number { color: #ff9800; }
    .metric-card.month .metric-number { color: #9c27b0; }

    .table-container {
      background: white;
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
    }

    .ventas-table {
      width: 100%;
    }

    .mat-mdc-header-cell {
      background: #f8f9fa;
      color: #424242;
      font-weight: 600;
      font-size: 14px;
      padding: 16px 12px;
    }

    .numero-badge {
      background: linear-gradient(135deg, #e8eaf6, #c5cae9);
      color: #3f51b5;
      padding: 6px 12px;
      border-radius: 16px;
      font-size: 12px;
      font-weight: 600;
      font-family: 'Courier New', monospace;
    }

    .fecha-info {
      display: flex;
      flex-direction: column;
      gap: 2px;
    }

    .fecha {
      font-weight: 500;
      color: #212121;
      font-size: 14px;
    }

    .hora {
      font-size: 12px;
      color: #757575;
    }

    .total-amount {
      font-weight: 600;
      color: #2e7d32;
      font-size: 16px;
    }

    .metodo-efectivo {
      background: linear-gradient(135deg, #e8f5e8, #c8e6c9);
      color: #2e7d32;
    }

    .metodo-tarjeta {
      background: linear-gradient(135deg, #e3f2fd, #bbdefb);
      color: #1976d2;
    }

    .metodo-transferencia {
      background: linear-gradient(135deg, #f3e5f5, #e1bee7);
      color: #7b1fa2;
    }

    .estado-completada {
      background: linear-gradient(135deg, #e8f5e8, #c8e6c9);
      color: #2e7d32;
    }

    .estado-pendiente {
      background: linear-gradient(135deg, #fff3e0, #ffe0b2);
      color: #f57c00;
    }

    .action-buttons {
      display: flex;
      gap: 4px;
    }

    .no-data {
      text-align: center;
      padding: 80px 20px;
      background: white;
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    }

    .no-data-icon {
      font-size: 72px;
      color: #bdbdbd;
      margin-bottom: 16px;
    }

    @media (max-width: 768px) {
      .ventas-container {
        padding: 16px;
      }

      .header-section {
        flex-direction: column;
        gap: 16px;
        text-align: center;
      }

      .page-subtitle {
        margin: 8px 0 0 0;
      }

      .metrics-grid {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class VentasComponent implements OnInit {
  columnas: string[] = ['numero', 'cliente', 'fecha', 'total', 'metodoPago', 'estado', 'acciones'];
  
  ventas: Venta[] = [
    {
      id: 1,
      numero: 'VTA-001',
      cliente: 'Juan Pérez',
      fecha: '2024-12-20',
      hora: '10:30',
      subtotal: 1059.32,
      igv: 190.68,
      total: 1250.00,
      metodoPago: 'Efectivo',
      estado: 'Completada',
      vendedor: 'Ana García'
    },
    {
      id: 2,
      numero: 'VTA-002',
      cliente: 'María López',
      fecha: '2024-12-20',
      hora: '11:15',
      subtotal: 754.24,
      igv: 135.76,
      total: 890.00,
      metodoPago: 'Tarjeta',
      estado: 'Completada',
      vendedor: 'Carlos Ruiz'
    },
    {
      id: 3,
      numero: 'VTA-003',
      cliente: 'Pedro Silva',
      fecha: '2024-12-20',
      hora: '12:45',
      subtotal: 1779.66,
      igv: 320.34,
      total: 2100.00,
      metodoPago: 'Transferencia',
      estado: 'Pendiente',
      vendedor: 'Luis Morales'
    }
  ];

  constructor() { }

  ngOnInit(): void {
    // Aquí se cargarían las ventas desde el servicio
  }

  getVentasHoy(): number {
    const hoy = new Date().toISOString().split('T')[0];
    return this.ventas.filter(v => v.fecha === hoy).length;
  }

  getIngresosHoy(): number {
    const hoy = new Date().toISOString().split('T')[0];
    return this.ventas
      .filter(v => v.fecha === hoy && v.estado === 'Completada')
      .reduce((sum, v) => sum + v.total, 0);
  }

  getTicketPromedio(): number {
    const ventasCompletadas = this.ventas.filter(v => v.estado === 'Completada');
    if (ventasCompletadas.length === 0) return 0;
    const total = ventasCompletadas.reduce((sum, v) => sum + v.total, 0);
    return total / ventasCompletadas.length;
  }

  getVentasMes(): number {
    const mesActual = new Date().getMonth();
    const añoActual = new Date().getFullYear();
    return this.ventas
      .filter(v => {
        const fechaVenta = new Date(v.fecha);
        return fechaVenta.getMonth() === mesActual && 
               fechaVenta.getFullYear() === añoActual &&
               v.estado === 'Completada';
      })
      .reduce((sum, v) => sum + v.total, 0);
  }

  getMetodoPagoClass(metodo: string): string {
    switch(metodo) {
      case 'Efectivo': return 'metodo-efectivo';
      case 'Tarjeta': return 'metodo-tarjeta';
      case 'Transferencia': return 'metodo-transferencia';
      default: return '';
    }
  }

  getMetodoPagoIcon(metodo: string): string {
    switch(metodo) {
      case 'Efectivo': return 'payments';
      case 'Tarjeta': return 'credit_card';
      case 'Transferencia': return 'account_balance';
      default: return 'payment';
    }
  }

  getEstadoClass(estado: string): string {
    switch(estado) {
      case 'Completada': return 'estado-completada';
      case 'Pendiente': return 'estado-pendiente';
      default: return '';
    }
  }

  getEstadoIcon(estado: string): string {
    switch(estado) {
      case 'Completada': return 'check_circle';
      case 'Pendiente': return 'hourglass_empty';
      default: return 'help';
    }
  }
}
