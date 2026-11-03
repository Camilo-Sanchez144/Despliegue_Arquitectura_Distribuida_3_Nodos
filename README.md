# 📚 Gestión de Biblioteca

Aplicación para la gestión de libros de una biblioteca, construida con una **arquitectura distribuida en 3 capas** desplegada en máquinas virtuales. El proyecto nació como ejercicio práctico para aprender a crear y conectar VMs, trabajar con bases de datos usando el ORM Hibernate, y desarrollar un backend con Spring Boot y un frontend en JavaScript nativo.

## 🧱 Arquitectura

| Capa | Tecnología | Máquina virtual |
|------|-----------|-----------------|
| Base de datos | MySQL + Hibernate (JPA) | Debian 13 |
| Backend (API REST) | Java 21 + Spring Boot | CentOS |
| Frontend | HTML, CSS y JavaScript nativo | Ubuntu Server |

La comunicación entre las tres máquinas se realiza mediante **Tailscale** (red privada VPN), sin exponer los servicios a internet.

```
[ Navegador ] → [ Frontend · Ubuntu Server ] → [ Backend · CentOS ] → [ BD · Debian 13 ]
                                  └──────────── red privada Tailscale ────────────┘
```

## 🛠️ Tecnologías

- **Backend:** Java 21, Spring Boot, Spring Web MVC, Spring Data JPA, Hibernate, Gradle
- **Base de datos:** MySQL
- **Frontend:** JavaScript nativo (sin frameworks), HTML5, CSS3
- **Infraestructura:** VirtualBox [confirma el hipervisor], Debian 13, CentOS, Ubuntu Server, Tailscale

## ✨ Funcionalidades

- Registrar libros
- Listar libros activos e inactivos
- Consultar un libro por ID
- Actualizar la información de un libro
- Eliminar (desactivar) un libro
- Reactivar libros inactivos

## 🔌 Endpoints de la API

Base: `/api/libros`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/libros` | Crear un libro |
| `GET` | `/api/libros` | Listar libros activos |
| `GET` | `/api/libros/inactivos` | Listar libros inactivos |
| `GET` | `/api/libros/{id}` | Obtener un libro por ID |
| `PUT` | `/api/libros/{id}` | Actualizar un libro |
| `PUT` | `/api/libros/{id}/activar` | Reactivar un libro |
| `DELETE` | `/api/libros/{id}` | Desactivar un libro |

### Ejemplo de body (JSON)

```json
{
  "titulo_libro": "Cien años de soledad",
  "autor_libro": "Gabriel García Márquez",
  "categoria_libro": "Novela",
  "anio_publicacion_libro": "1967-05-30",
  "cantidad_libro": 5,
  "status_libro": true
}
```

## 🚀 Instalación y ejecución

### Requisitos
- Java 21
- MySQL
- Tailscale instalado y configurado en cada VM
- Un servidor web para el frontend (ej. Nginx o Apache)

### 1. Base de datos (Debian 13)
```bash
# Crear la base de datos y el usuario
CREATE DATABASE biblioteca;
CREATE USER 'usuario'@'%' IDENTIFIED BY 'contraseña';
GRANT ALL PRIVILEGES ON biblioteca.* TO 'usuario'@'%';
```

### 2. Backend (CentOS)
```bash
git clone [URL-del-repositorio]
cd gestionbiblioteca
```

Configura `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://[IP-Tailscale-BD]:3306/biblioteca
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update
```

Ejecuta:

```bash
./gradlew bootRun
# o generar el jar
./gradlew build && java -jar build/libs/[nombre].jar
```

### 3. Frontend (Ubuntu Server)
Copia la carpeta del frontend al directorio del servidor web y en el JS apunta la URL de la API a la IP Tailscale del backend:

```js
const API_URL = "http://[IP-Tailscale-Backend]:8080/api/libros";
```

## 📄 Documentación

Presentación completa del proyecto y del despliegue: [Ver presentación](https://docs.google.com/presentation/d/1xnhljuOutBo0y1RkjZKZvaQUsLubPwlB/edit?usp=sharing&ouid=106488993600416122907&rtpof=true&sd=true)

## 🎯 Aprendizajes

- Creación y configuración de máquinas virtuales con distintos sistemas operativos
- Conexión segura entre servidores con Tailscale
- Mapeo objeto-relacional con Hibernate/JPA
- Desarrollo de una API REST con Spring Boot
- Consumo de una API desde JavaScript nativo (separación frontend/backend)


