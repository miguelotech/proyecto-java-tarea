# proyecto-java
### By: Alexander Chang Cruz.

# 🚀 Guía de Uso Rápido

## 📋 Requisitos mínimos
- **Opción 1**: Solo Java 21 (para desarrollo)
- **Opción 2**: Solo Docker (para entorno completo)

## ⚡ Inicio Rápido

### 🔥 **Método 1: Ultra rápido (H2 en memoria)**
```bash
git clone <tu-repositorio>
cd proyecto-java

# Opción A: Con Maven instalado
mvn spring-boot:run

# Opción B: Sin Maven (usa wrapper incluido)
./mvnw spring-boot:run        # Linux/Mac
mvnw.cmd spring-boot:run      # Windows
```

**Listo!** API corriendo en: http://localhost:8081/api/clients
**Base de datos H2**: http://localhost:8081/h2-console
- URL: `jdbc:h2:mem:devdb`
- User: `sa`
- Password: (vacío)

---

### 🐳 **Método 2: Entorno completo (PostgreSQL)**
```bash
git clone <tu-repositorio>
cd proyecto-java
docker-compose up --build
```

**Listo!** API corriendo en: http://localhost:8081/api/clients
**pgAdmin**: http://localhost:8082
- Email: `admin@admin.com`
- Password: `admin123`

---

## 🔄 Cambiar entre entornos

### Durante desarrollo:
```bash
# H2 (rápido, en memoria)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# PostgreSQL (con Docker corriendo)
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Variables de entorno:
```bash
export SPRING_PROFILES_ACTIVE=dev    # H2
export SPRING_PROFILES_ACTIVE=prod   # PostgreSQL
mvn spring-boot:run
```

---

## 🧪 Probar la API

```bash
# Listar usuarios
curl http://localhost:8081/api/users

# Crear usuario  
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez","email":"juan@example.com"}'

# Ver usuario creado
curl http://localhost:8081/api/users
```

---

## 🛠️ Perfiles disponibles

| Perfil | Base de Datos | Uso | Comando |
|--------|---------------|-----|---------|
| `dev` (default) | H2 en memoria | Desarrollo rápido | `mvn spring-boot:run` |
| `prod` | PostgreSQL | Entorno realista | `docker-compose up` |

**💡 Tip**: Usa `dev` para desarrollo rápido, `prod` para simular producción.

