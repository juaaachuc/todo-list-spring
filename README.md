# To-Do List API con Spring Boot

Este proyecto es una API REST para gestionar una lista de tareas, desarrollada con **Spring Boot**, **MySQL**, y ejecutándose dentro de **WSL**.

## 📌 Requisitos Previos
Antes de instalar y ejecutar el proyecto, asegúrate de tener lo siguiente:

- **Java 17** o superior.
- **Maven** instalado.
- **MySQL Server** en WSL.
- **IntelliJ IDEA o VSCode** (opcional para desarrollo).
- **WSL** instalado y configurado (opcional).
- **Sistema operativo basado en Linux** (en caso de no usar WSL).

## 🚀 Instalación
### 1️⃣ Clonar el Repositorio
Ejecuta el siguiente comando en la terminal:
```sh
cd ~
git clone https://github.com/juaaachuc/todo-list-spring.git
cd todo-list-spring
```

### 2️⃣ Configurar la Base de Datos MySQL
Inicia MySQL:
```sh
sudo service mysql start
```
Accede al cliente MySQL:
```sh
mysql -u root -p
```
Ejecuta los siguientes comandos en MySQL:
```sql
CREATE DATABASE todo_db;
```

### 3️⃣ Configurar el Archivo `application.properties`
Edita `src/main/resources/application.properties` y verifica que tenga la siguiente configuración (Ajusta con las credenciales de tu gestor de base de datos):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=todo_user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4️⃣ Construir y Ejecutar la Aplicación
Ejecuta los siguientes comandos:
```sh
./mvnw clean package
./mvnw spring-boot:run
```
La API estará disponible en:
```
http://localhost:8080/api/tareas
```

## 📡 Endpoints Disponibles

### 🔹 Obtener todas las tareas
```
GET /api/tareas
```

### 🔹 Obtener una tarea por ID
```
GET /api/tareas/{id}
```

### 🔹 Crear una nueva tarea
```
POST /api/tareas
Content-Type: application/json
{
  "titulo": "Mi nueva tarea",
  "descripcion": "Descripción de la tarea",
  "completada": false
}
```

### 🔹 Actualizar completamente una tarea
```
PUT /api/tareas/{id}
```

### 🔹 Actualizar parcialmente una tarea
```
PATCH /api/tareas/{id}
```

### 🔹 Eliminar una tarea
```
DELETE /api/tareas/{id}
```

## 🛠 Herramientas Utilizadas
- **Spring Boot 3.x**
- **MySQL**
- **Spring Data JPA**
- **Maven**
- **Lombok**

## 📝 Notas
Si tienes problemas con las dependencias, intenta limpiar y reconstruir el proyecto:
```sh
./mvnw clean install
```

¡Listo! Ya puedes usar y contribuir a este proyecto. 🚀

