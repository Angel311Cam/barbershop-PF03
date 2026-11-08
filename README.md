# Sistema de Gestión de Citas - Barbería

**Estudiante:** Angel Estuardo Campos Santay  
**Carné:** 9941-25-4809

## Descripción del proyecto

Este proyecto consiste en un sistema web para administrar los servicios y las citas de una barbería. Permite que los clientes consulten los servicios disponibles y soliciten citas, mientras que el administrador puede gestionar las reservas desde un panel de administración.

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Maven
- PostgreSQL
- HTML
- CSS
- JavaScript
- IntelliJ IDEA
- Postman

## Funcionalidades principales

- Visualización de servicios y precios.
- Registro de citas de clientes.
- Almacenamiento de información en PostgreSQL.
- Consulta de citas registradas.
- Administración de citas.
- Cambio de estado de las citas.
- Eliminación de citas.
- Filtro de citas por fecha y estado.
- Validación para evitar horarios ocupados.
- Cálculo de precios según el servicio seleccionado.

## Servicios disponibles

| Servicio | Precio |
|---|---:|
| Corte clásico | Q50 |
| Corte y barba | Q80 |
| Arreglo de barba | Q35 |
| Corte premium | Q65 |

## Estructura del proyecto

```text
src/
 └── main/
     ├── java/com/barbershop/
     │   ├── controller/
     │   ├── entity/
     │   ├── repository/
     │   ├── service/
     │   ├── BarbershopApplication.java
     │   └── DataInitializer.java
     │
     └── resources/
         ├── static/
         │   ├── css/
         │   ├── js/
         │   ├── index.html
         │   └── admin.html
         └── application.properties
```

## Base de datos

El proyecto utiliza PostgreSQL con una base de datos llamada `barbershop_db`.

Antes de ejecutar el proyecto, se debe crear la base de datos y configurar las credenciales en `application.properties`.

## Ejecución del proyecto

1. Abrir el proyecto en IntelliJ IDEA.
2. Configurar Java 21.
3. Tener PostgreSQL instalado y funcionando.
4. Crear la base de datos `barbershop_db`.
5. Configurar el usuario y contraseña en `application.properties`.
6. Ejecutar `BarbershopApplication.java`.
7. Abrir el navegador en:

```text
http://localhost:8082/
```

Panel de administración:

```text
http://localhost:8082/admin.html
```

## Pruebas

Se utilizó Postman para probar las operaciones de la API:

- Consultar servicios.
- Registrar citas.
- Consultar citas.
- Buscar citas por fecha.
- Cambiar el estado de una cita.
- Eliminar citas.
- Verificar que no se permitan horarios ocupados.

## Autor

Angel Estuardo Campos Santay  
Ingeniería en Sistemas