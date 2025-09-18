# Proyecto: Microservicios Cliente-Persona y Cuenta-Movimiento

## 📖 Descripción
Este proyecto implementa una arquitectura de **microservicios en Java Spring Boot**, dividida en dos servicios principales:

- **cliente-persona** → gestión de clientes y personas.  
- **cuenta-movimiento** → gestión de cuentas bancarias y movimientos.  

La comunicación entre microservicios se realiza de manera **asíncrona con Apache Kafka**, garantizando la integración de eventos entre entidades.  
Cada servicio expone APIs REST documentadas con **Swagger**.

---

##  Arquitectura
- **Microservicios**:  
  - `cliente-persona` (puerto 8081)  
  - `cuenta-movimiento` (puerto 8082)  
- **Mensajería**: Apache Kafka  
- **Bases de datos**:  
  - `clientepersona`  
  - `cuentamovimiento`  
- **Orquestación**: Docker y Docker Compose  
- **Documentación de API**: Swagger UI  

---

##  Tecnologías utilizadas
- Java 17  
- Spring Boot 3  
- Spring Data JPA (Hibernate)  
- Spring Kafka  
- Swagger / OpenAPI  
- Docker & Docker Compose  
- PostgreSQL  

---

##  Funcionalidades implementadas
- **F1**: CRUD de Cliente, Cuenta y Movimiento  
- **F2**: Registro de movimientos con actualización automática de saldo  
- **F3**: Validación de saldo insuficiente con error `"Saldo no disponible"`  
- **F4**: Reporte de estado de cuenta (rango de fechas y cliente)  
- **F5**: Pruebas unitarias de Cliente  
- **F6**: Prueba de integración en Cuenta-Movimiento  
- **F7**: Despliegue con Docker y Docker Compose  

---

##  Estructura con arquitectura hexagonal
### Microservicio `cliente-persona`
```
src/main/java/com/banco/clientepersona
 ├── application
 │   ├── dto (in/out)
 │   └── service
 ├── domain (Cliente, Persona)
 ├── infrastructure
 │   ├── config
 │   ├── controller
 │   ├── messaging (Kafka producer)
 │   └── repository
```

### Microservicio `cuenta-movimiento`
```
src/main/java/com/banco/cuentamovimiento
 ├── application
 │   ├── dto (in/out)
 │   └── service
 ├── domain (Cuenta, Movimiento)
 ├── infrastructure
 │   ├── config
 │   ├── controller
 │   ├── messaging (Kafka producer/consumer)
 │   └── repository
```

---

##  Base de Datos
El script de base de datos se encuentra en: **`BaseDatos.sql`**  

Incluye:
- `clientes`  
- `cuentas`  
- `movimientos`  

> ⚠️ Nota: la relación entre `clientes` y `cuentas` es lógica (por eventos Kafka) ya que pertenecen a bases distintas.

---

##  Despliegue con Docker
### 1. Construir los JAR
```bash
mvn clean package -DskipTests
```

### 2. Levantar con Docker Compose
```bash
docker-compose up --build
```

### 3. Servicios disponibles
- **Swagger Cliente-Persona** → [http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/)  
- **Swagger Cuenta-Movimiento** → [http://localhost:8082/swagger-ui/index.html#/](http://localhost:8082/swagger-ui/index.html#/)  
- **Kafka** → localhost:9092  
- **PostgreSQL** → localhost:5432  

---

##  Endpoints principales
### Cliente-Persona (`localhost:8081`)
- `POST /clientes` → Crear cliente  
- `GET /clientes/{id}` → Consultar cliente  
- `PUT /clientes/{id}` → Actualizar cliente  
- `DELETE /clientes/{id}` → Eliminar cliente  

### Cuenta-Movimiento (`localhost:8082`)
- `POST /cuentas` → Crear cuenta  
- `GET /cuentas/{id}` → Consultar cuenta  
- `POST /movimientos` → Registrar movimiento  
- `GET /reportes?fechaInicio=...&fechaFin=...&clienteId=...` → Reporte de estado de cuenta  

---

##  Mensajería Kafka
- **Topic `cliente-creado`** → envío de eventos al crear clientes.  
- **Topic `movimiento-registrado`** → registro de movimientos asociados a cuentas.  

Pruebas con consumidor Kafka:
```bash
docker exec -it kafka bash
/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic cliente-creado --from-beginning
/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic movimiento-registrado --from-beginning
```
##  Postman
La colección de pruebas se encuentra en la carpeta [/docs/postman](./postman).

---

##  Pruebas
- **Unitarias**: Cliente y Movimiento  
- **Integración**: Reporte de cuentas  

---

##  Entregables
- Repositorio Git público con el código fuente  
- Archivo `BaseDatos.sql` con el script de creación de tablas  
- Colección Postman para pruebas de endpoints  
- README.md (este documento)  
