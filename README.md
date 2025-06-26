# 📘 Ordinaria DIS - Examen Enero

Este proyecto es una aplicación Java con arquitectura frontend-backend usando Spring Boot y Vaadin 24. La app gestiona una lista de usuarios almacenados en un fichero JSON y permite mostrarlos, editarlos y exportarlos a PDF.

---

## ⚙️ Tecnologías

- Java 17 (Amazon Corretto)
- Spring Boot 3.4.1
- Vaadin 24
- Gson 2.11.0
- Docker
- GitFlow

---

## 🐳 Docker: Cómo ejecutar

### 🛠 Backend (Puerto 8080)

```bash
# Desde la raíz del backend
docker build -t backend:latest .
docker run -d -p 8080:8080 backend:latest


Accede a la API en: http://localhost:8080/api/usuarios

# Desde la raíz del frontend
docker build -t frontend:latest .
docker run -d -p 8081:8081 frontend:latest

Interfaz web: http://localhost:8081