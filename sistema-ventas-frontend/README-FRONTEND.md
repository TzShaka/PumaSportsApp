# Sistema de Ventas - Frontend Angular

## 🚀 Características Implementadas

### ✅ Gestión de Categorías
- ✅ Listado de categorías con tabla Material Design
- ✅ Crear nueva categoría
- ✅ Editar categoría existente
- ✅ Activar/Desactivar categoría
- ✅ Eliminar categoría
- ✅ Filtro de búsqueda
- ✅ Ordenamiento por columnas
- ✅ Validaciones de formulario
- ✅ Manejo de errores

### ✅ Gestión de Clientes
- ✅ Listado de clientes con tabla Material Design
- ✅ Crear nuevo cliente
- ✅ Editar cliente existente
- ✅ Activar/Desactivar cliente
- ✅ Eliminar cliente
- ✅ Filtro de búsqueda avanzado
- ✅ Validaciones de formulario (DNI, email, teléfono)
- ✅ Manejo de errores y duplicados
- ✅ Diseño responsivo

### 🎨 Interfaz de Usuario
- ✅ Diseño moderno con Material Design y Tailwind CSS
- ✅ Layout responsivo para móvil y desktop
- ✅ Navegación lateral con iconos
- ✅ Dialogs modales para formularios
- ✅ Tooltips informativos
- ✅ Animaciones suaves
- ✅ Estados de carga
- ✅ Mensajes de confirmación y error

## 🛠️ Configuración y Ejecución

### Prerrequisitos
- Node.js 18+ 
- Angular CLI 18+
- Backend de microservicios corriendo

### Instalación

1. **Navegar al directorio del frontend:**
   ```bash
   cd sistema-ventas-frontend
   ```

2. **Instalar dependencias:**
   ```bash
   npm install
   ```

3. **Verificar configuración del backend:**
   Asegúrate de que los servicios estén corriendo en:
   - Gateway: `http://localhost:8085`
   - Auth Service: Puerto configurado
   - Catalogo Service: Puerto configurado
   - Cliente Service: Puerto configurado

### Ejecutar en Desarrollo

```bash
ng serve
```

El frontend estará disponible en: `http://localhost:4200`

### Construir para Producción

```bash
ng build --prod
```

## 🔧 Estructura del Proyecto

```
src/app/
├── guards/
│   └── auth.guard.ts              # Guard de autenticación
├── material/
│   └── material.module.ts         # Módulos de Material Design
├── modelo/
│   ├── Categoria.ts               # Interface de Categoría
│   ├── Cliente.ts                 # Interface de Cliente
│   ├── LoginRequest.ts            # Interface de Login
│   └── LoginResponse.ts           # Interface de Respuesta Login
├── pages/
│   ├── categorias/
│   │   ├── categorias.component.ts
│   │   ├── categorias.component.html
│   │   ├── categorias.component.css
│   │   └── categoria-dialog.component.ts
│   ├── clientes/
│   │   ├── clientes.component.ts
│   │   ├── clientes.component.html
│   │   ├── clientes.component.css
│   │   └── cliente-dialog.component.ts
│   ├── login-component/
│   ├── home-layout.component.*    # Layout principal
│   └── productos/
├── services/
│   ├── auth-service.service.ts    # Servicio de autenticación
│   ├── categoria.service.ts       # Servicio de categorías
│   └── cliente.service.ts         # Servicio de clientes
└── app.routes.ts                  # Configuración de rutas
```

## 🔗 Integración con Backend

### Endpoints Utilizados

**Categorías:**
- `GET /categoria` - Listar categorías
- `POST /categoria` - Crear categoría
- `PUT /categoria/{id}` - Actualizar categoría
- `DELETE /categoria/{id}` - Eliminar categoría
- `PUT /categoria/desactivar/{id}` - Desactivar categoría

**Clientes:**
- `GET /cliente` - Listar clientes
- `POST /cliente` - Crear cliente
- `GET /cliente/{id}` - Obtener cliente por ID
- `GET /cliente/dni/{dni}` - Obtener cliente por DNI
- `PUT /cliente/{id}` - Actualizar cliente
- `DELETE /cliente/{id}` - Eliminar cliente

**Autenticación:**
- `POST /auth/login` - Iniciar sesión
- `POST /auth/validate` - Validar token

### Headers de Autenticación

Todos los requests (excepto login) incluyen:
```javascript
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

## 📱 Funcionalidades de la UI

### Categorías
- **Tabla responsive** con ordenamiento
- **Filtro en tiempo real** por nombre
- **Estados visuales** (Activo/Inactivo) con badges coloridos
- **Acciones rápidas**: Editar, Activar/Desactivar, Eliminar
- **Dialog modal** para crear/editar con validaciones
- **Confirmaciones** antes de eliminar

### Clientes
- **Tabla avanzada** con múltiples columnas
- **Filtro global** que busca en todos los campos
- **Validaciones robustas**: DNI único, email válido, formato de teléfono
- **Links funcionales** en emails
- **Formato de fecha** legible
- **Manejo de campos opcionales**
- **Estados de carga** y mensajes informativos

### Navegación
- **Sidebar responsivo** con navegación por secciones
- **Breadcrumbs visuales** con iconos Material
- **Rutas protegidas** con guard de autenticación
- **Cierre de sesión** con limpieza de localStorage

## 🚨 Manejo de Errores

- **Validaciones de formulario** en tiempo real
- **Mensajes de error específicos** según el tipo de error
- **Manejo de conflictos** (DNI/email duplicados)
- **Timeouts y errores de red**
- **Fallbacks y estados de carga**

## 🎨 Personalización de Estilos

El proyecto utiliza:
- **Material Design** para componentes base
- **Tailwind CSS** para utilidades y layout
- **CSS Grid/Flexbox** para layouts responsivos
- **Animaciones CSS** para transiciones suaves
- **Variables CSS** para colores y espaciado consistente

## 🔜 Próximas Mejoras

1. **Paginación** en tablas grandes
2. **Filtros avanzados** con múltiples criterios
3. **Exportación** de datos (PDF, Excel)
4. **Modo oscuro**
5. **Notificaciones push**
6. **Cache de datos** para mejor performance
7. **Tests unitarios e integración**

## 🐛 Resolución de Problemas

### Error de CORS
Verificar configuración en el Gateway:
```yaml
allowedOrigins: "http://localhost:4200"
```

### Token expirado
El sistema redirige automáticamente al login. Si persiste, limpiar localStorage:
```javascript
localStorage.clear()
```

### Estilos no cargan
Verificar que Tailwind CSS esté configurado en `styles.css`

### Servicios no conectan
1. Verificar que todos los microservicios estén ejecutándose
2. Comprobar que Eureka esté funcionando
3. Revisar logs del Gateway para errores de ruteo

## 📋 Lista de Verificación Pre-Ejecución

### Backend
- [ ] Eureka Server corriendo en puerto 8090
- [ ] Config Server corriendo
- [ ] Gateway corriendo en puerto 8085
- [ ] Auth Service corriendo
- [ ] Catalogo Service corriendo
- [ ] Cliente Service corriendo
- [ ] Base de datos MySQL configurada y accesible

### Frontend
- [ ] Node.js 18+ instalado
- [ ] Angular CLI instalado globalmente
- [ ] Dependencias instaladas (`npm install`)
- [ ] Configuración de endpoints correcta

## 🚀 Comandos Útiles

### Desarrollo
```bash
# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
ng serve

# Ejecutar con hot reload en puerto específico
ng serve --port 4200 --open

# Generar nuevo componente
ng generate component pages/nuevo-componente

# Generar nuevo servicio
ng generate service services/nuevo-servicio
```

### Producción
```bash
# Build para producción
ng build --prod

# Servir build de producción (requiere servidor web)
ng serve --prod
```

### Testing
```bash
# Ejecutar tests unitarios
ng test

# Ejecutar tests end-to-end
ng e2e

# Ejecutar tests con coverage
ng test --code-coverage
```

## 📊 Métricas y Performance

### Optimizaciones Implementadas
- **Lazy Loading** de módulos
- **OnPush** change detection strategy donde sea posible
- **TrackBy** functions en listas grandes
- **Debounce** en filtros de búsqueda
- **Minificación** de assets en producción

### Métricas Esperadas
- **First Contentful Paint**: < 2s
- **Time to Interactive**: < 3s
- **Bundle Size**: < 2MB
- **Lighthouse Score**: > 90

## 🔒 Seguridad

### Implementado
- **JWT Token** con expiración
- **Route Guards** para proteger rutas
- **HTTPS** ready (configurar en producción)
- **Input Sanitization** en formularios
- **CORS** configurado en backend

### Recomendaciones Adicionales
- Implementar **CSP** (Content Security Policy)
- Usar **HttpOnly** cookies en lugar de localStorage
- Configurar **Rate Limiting** en el backend
- Implementar **2FA** para usuarios admin

## 📞 Soporte y Contribución

### Reportar Bugs
1. Crear issue en el repositorio
2. Incluir pasos para reproducir
3. Adjuntar logs relevantes
4. Especificar navegador y versión

### Contribuir
1. Fork del repositorio
2. Crear branch feature/bug-fix
3. Seguir convenciones de código
4. Añadir tests si aplica
5. Crear Pull Request

---

**Desarrollado con ❤️ usando Angular 18 + Material Design + Tailwind CSS**
