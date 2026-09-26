# Biblioteca — Sistema de Gestión de Préstamos de Libros

Proyecto académico: diseño de una aplicación web (Backend + Frontend) aplicando
**Arquitectura Hexagonal**, **Código Limpio**, **Refactoring** y **principios SOLID**.

## Problema resuelto

Una biblioteca necesita controlar el préstamo y la devolución de libros:
- Un libro no puede prestarse si ya está prestado.
- Un usuario no puede tener más de 3 préstamos activos a la vez.
- Cada préstamo tiene una fecha límite de devolución (14 días) y cambia a estado
  `VENCIDO` automáticamente si se pasa la fecha.

## Stack utilizado

| Capa       | Tecnología                          |
|------------|--------------------------------------|
| Backend    | Java 17 + Spring Boot 3 (Maven)      |
| Frontend   | React 18 + Vite                      |
| Base datos | H2 (en memoria, no requiere instalación) |

## Cómo está aplicada la Arquitectura Hexagonal

```
backend/src/main/java/com/biblioteca/
├── domain/                    → NÚCLEO: reglas de negocio puras (sin Spring, sin JPA)
│   ├── model/                 → Libro, Usuario, Prestamo (entidades de dominio)
│   ├── ports/in/               → Casos de uso (qué hace la app)
│   ├── ports/out/              → Contratos de persistencia (qué necesita la app)
│   ├── service/                → Implementación de los casos de uso
│   └── exception/              → Excepciones de negocio
│
└── infrastructure/             → DETALLES: todo lo reemplazable
    ├── adapters/in/web/        → Controllers REST (entrada)
    ├── adapters/out/persistence/ → JPA (salida)
    └── config/                  → Conecta el dominio con Spring (BeanConfig)
```

**Principios SOLID evidenciados:**
- **SRP**: cada clase tiene una responsabilidad (Controller ≠ Service ≠ Adapter).
- **DIP**: `PrestamoService` depende de `PrestamoRepositoryPort` (interfaz), no de
  Spring Data JPA directamente. La implementación (`PrestamoRepositoryAdapter`)
  se inyecta desde `infrastructure`.
- **OCP**: se podría cambiar H2 por MongoDB creando un nuevo adapter, sin tocar
  ni una línea del dominio.
- **ISP**: los puertos están separados por caso de uso (`PrestarLibroUseCase`,
  `DevolverLibroUseCase`, etc.) en vez de una única interfaz gigante.

**Código limpio y refactoring:** nombres descriptivos en español consistente,
métodos cortos con una sola responsabilidad, sin lógica de negocio en
controllers ni en adapters (viven solo en `domain/service`), DTOs separados
del modelo de dominio para no acoplar la API REST a las reglas internas.

---

## Requisitos previos

- **Java 17 o superior** → `java -version`
- **Maven** (o usar el wrapper `mvnw` si lo agregas; aquí se asume Maven instalado) → `mvn -version`
- **Node.js 18+** y npm → `node -version`

## Paso a paso para correr el proyecto

### 1. Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

Espera a ver el mensaje `Started BibliotecaHexagonalApplication`. Esto levanta:
- La API REST en **http://localhost:8080**
- La consola de H2 en **http://localhost:8080/h2-console** (JDBC URL: `jdbc:h2:mem:bibliotecadb`, usuario `sa`, sin contraseña)
- Datos de ejemplo (4 libros y 2 usuarios) cargados automáticamente al iniciar

Si no tienes Maven instalado globalmente, instálalo o genera el `mvnw` con
`mvn -N io.takari:maven:wrapper` antes del primer paso.

### 2. Frontend (React)

En **otra terminal**, sin cerrar el backend:

```bash
cd frontend
npm install
npm run dev
```

Abre **http://localhost:5173** en el navegador.

### 3. Probar la aplicación

1. En la pestaña **Catálogo** verás los libros y si están disponibles o no.
2. En la pestaña **Préstamos**, selecciona un libro disponible y un usuario, y
   presiona **Prestar**.
3. El libro pasará a "Prestado" en el catálogo, y aparecerá en la lista de
   préstamos activos con su fecha de vencimiento.
4. Presiona **Devolver** en un préstamo para liberar el libro nuevamente.

### Endpoints principales de la API (para probar con Postman/curl si quieres)

| Método | Ruta                          | Descripción                    |
|--------|-------------------------------|---------------------------------|
| GET    | `/api/libros`                 | Lista todos los libros          |
| POST   | `/api/libros`                 | Registra un libro nuevo         |
| GET    | `/api/usuarios`                | Lista todos los usuarios        |
| POST   | `/api/usuarios`                | Registra un usuario nuevo       |
| GET    | `/api/prestamos`               | Lista todos los préstamos       |
| POST   | `/api/prestamos`               | Registra un préstamo (`{libroId, usuarioId}`) |
| PUT    | `/api/prestamos/{id}/devolver` | Marca un préstamo como devuelto |

## Subir el proyecto a GitHub/GitLab

```bash
cd biblioteca-hexagonal
git init
git add .
git commit -m "Proyecto: Biblioteca con Arquitectura Hexagonal"
git branch -M main
git remote add origin <URL-de-tu-repositorio>
git push -u origin main
```

## Notas

- La base de datos es en memoria (H2): cada vez que reinicies el backend, los
  datos vuelven a los 4 libros y 2 usuarios de ejemplo. Es intencional, para
  que sea fácil de correr y evaluar sin configurar nada externo. Si se
  necesita persistencia real, basta con cambiar `application.properties` a
  PostgreSQL/MySQL — el dominio no cambia en absoluto, solo la configuración
  de infraestructura (esto demuestra el OCP en la práctica).
