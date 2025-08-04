# 🔐 Sistema de Login con Spring Boot

Un sistema completo de autenticación desarrollado con Spring Boot que incluye registro de usuarios, login seguro con hashing de contraseñas usando BCrypt, y una interfaz web moderna.

## 🚀 Características

### **Backend (Spring Boot)**
- ✅ **Registro de usuarios** con validaciones
- ✅ **Login seguro** con hashing BCrypt
- ✅ **Validación de contraseñas** (mínimo 6 caracteres)
- ✅ **Prevención de usuarios duplicados**
- ✅ **Respuestas JSON estructuradas**
- ✅ **Manejo de errores amigable**
- ✅ **CORS configurado** para desarrollo

### **Frontend (HTML/CSS/JavaScript)**
- ✅ **Interfaz moderna** y responsive
- ✅ **Validaciones del lado cliente**
- ✅ **Mensajes de error dinámicos**
- ✅ **Navegación entre páginas**
- ✅ **UX optimizada**

### **Seguridad**
- ✅ **Hashing BCrypt** para contraseñas
- ✅ **Validación de entrada** en backend
- ✅ **Sanitización de datos**
- ✅ **Mensajes de error seguros**

## 📁 Estructura del Proyecto

```
Sistema_Login/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/sistemalogin/
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   └── WebController.java
│   │   │   │   ├── service/
│   │   │   │   │   └── AuthService.java
│   │   │   │   ├── model/
│   │   │   │   │   └── User.java
│   │   │   │   ├── dto/
│   │   │   │   │   └── AuthResponse.java
│   │   │   │   └── BackendApplication.java
│   │   │   └── resources/
│   │   │       ├── static/
│   │   │       │   ├── login.html
│   │   │       │   └── register.html
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/example/sistemalogin/
│   │           ├── service/
│   │           │   └── AuthServiceTest.java
│   │           ├── controller/
│   │           │   ├── AuthControllerTest.java
│   │           │   └── AuthControllerIntegrationTest.java
│   │           ├── model/
│   │           │   └── UserTest.java
│   │           ├── dto/
│   │           │   └── AuthResponseTest.java
│   │           ├── EndToEndTest.java
│   │           └── TestConfig.java
│   ├── pom.xml
│   └── TEST_README.md
└── README.md
```

## 🛠️ Tecnologías Utilizadas

### **Backend**
- **Spring Boot 2.7.18** - Framework principal
- **Spring Security Crypto** - Hashing BCrypt
- **Jackson** - Serialización JSON
- **JUnit 5** - Testing framework
- **Mockito** - Mocks para tests
- **Maven** - Gestión de dependencias

### **Frontend**
- **HTML5** - Estructura semántica
- **CSS3** - Estilos modernos y responsive
- **JavaScript ES6+** - Interactividad
- **Fetch API** - Comunicación con backend

## ⚙️ Instalación y Configuración

### **Prerrequisitos**
- Java 8 o superior
- Maven 3.6+
- Navegador web moderno

### **1. Clonar el Proyecto**
```bash
git clone <repository-url>
cd Sistema_Login/backend
```

### **2. Compilar el Proyecto**
```bash
mvn clean compile
```

### **3. Ejecutar la Aplicación**
```bash
mvn spring-boot:run
```

### **4. Acceder a la Aplicación**
- **Login:** http://localhost:8080/login.html
- **Registro:** http://localhost:8080/register.html
- **API Base:** http://localhost:8080/api

## 🧪 Ejecutar Tests

### **Todos los Tests**
```bash
mvn test
```

### **Tests Específicos**
```bash
# Tests unitarios
mvn test -Dtest=AuthServiceTest
mvn test -Dtest=AuthControllerTest

# Tests de integración
mvn test -Dtest=AuthControllerIntegrationTest
mvn test -Dtest=EndToEndTest
```

### **Cobertura de Tests**
- ✅ **37 tests** implementados
- ✅ **100% cobertura** de endpoints
- ✅ **Tests unitarios** y de integración
- ✅ **Casos de borde** cubiertos

## 📡 API Endpoints

### **POST /api/register**
Registra un nuevo usuario.

**Request:**
```json
{
  "username": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

**Response (Éxito):**
```json
{
  "success": true,
  "message": "Usuario registrado exitosamente",
  "username": "usuario@ejemplo.com"
}
```

**Response (Error):**
```json
{
  "success": false,
  "message": "El nombre de usuario ya existe"
}
```

### **POST /api/login**
Autentica un usuario existente.

**Request:**
```json
{
  "username": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

**Response (Éxito):**
```json
{
  "success": true,
  "message": "Inicio de sesión exitoso",
  "username": "usuario@ejemplo.com"
}
```

**Response (Error):**
```json
{
  "success": false,
  "message": "Credenciales incorrectas"
}
```

## 🔒 Validaciones de Seguridad

### **Registro**
- ✅ Username obligatorio
- ✅ Password obligatorio
- ✅ Password mínimo 6 caracteres
- ✅ Usuario único (no duplicados)
- ✅ Sanitización de espacios

### **Login**
- ✅ Username obligatorio
- ✅ Password obligatorio
- ✅ Verificación con BCrypt
- ✅ Mensajes de error seguros

## 🎨 Interfaz de Usuario

### **Página de Login**
- Formulario de autenticación
- Validaciones en tiempo real
- Mensajes de error dinámicos
- Enlace al registro

### **Página de Registro**
- Formulario de registro
- Confirmación de contraseña
- Validaciones de seguridad
- Enlace al login

## 🚀 Funcionalidades

### **Flujo de Registro**
1. Usuario accede a `/register.html`
2. Completa formulario con email y contraseña
3. Sistema valida datos y hashea contraseña
4. Usuario es registrado exitosamente
5. Redirección automática al login

### **Flujo de Login**
1. Usuario accede a `/login.html`
2. Ingresa credenciales
3. Sistema verifica con BCrypt
4. Login exitoso o mensaje de error
5. Interfaz muestra resultado

## 🔧 Configuración

### **application.properties**
```properties
# Server configuration
server.port=8080

# Static resources configuration
spring.web.resources.static-locations=classpath:/static/
spring.web.resources.add-mappings=true

# Logging for debugging
logging.level.org.springframework.web=DEBUG
logging.level.com.example.sistemalogin=DEBUG
```

## 📊 Métricas de Calidad

### **Cobertura de Tests**
- **Registro:** 100% de casos cubiertos
- **Login:** 100% de casos cubiertos
- **Validaciones:** 100% de casos cubiertos
- **Errores:** 100% de casos cubiertos

### **Casos de Borde**
- ✅ Valores null
- ✅ Strings vacíos
- ✅ Contraseñas muy cortas
- ✅ Usuarios duplicados
- ✅ Credenciales incorrectas

## 🎯 Próximas Mejoras

### **Funcionalidades Sugeridas**
1. **Base de Datos:** Migrar de HashMap a persistencia real
2. **JWT Tokens:** Implementar autenticación con tokens
3. **Validación de Email:** Verificar formato de email
4. **Rate Limiting:** Protección contra ataques de fuerza bruta
5. **Logs de Auditoría:** Registro de intentos de login
6. **Recuperación de Contraseña:** Sistema de reset por email

### **Mejoras de Seguridad**
1. **CSRF Protection:** Protección contra ataques CSRF
2. **Password Policy:** Políticas de contraseñas más estrictas
3. **Session Management:** Gestión de sesiones
4. **HTTPS:** Configuración SSL/TLS

### **Mejoras de UX**
1. **Loading States:** Indicadores de carga
2. **Toast Notifications:** Notificaciones elegantes
3. **Form Validation:** Validación en tiempo real mejorada
4. **Responsive Design:** Mejor adaptación móvil

## 🐛 Solución de Problemas

### **Error de Compilación**
```bash
# Si hay problemas con Java version
mvn clean compile -X
```

### **Puerto Ocupado**
```bash
# Cambiar puerto en application.properties
server.port=8081
```

### **Tests Fallando**
```bash
# Ejecutar tests con debug
mvn test -X
```

## 📝 Licencia

Este proyecto es parte del curso de Inteligencia Artificial y está destinado únicamente para fines educativos.

## 👥 Contribución

Para contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## 📞 Contacto

Para preguntas o soporte técnico, contacta al equipo de desarrollo.

---

**Desarrollado con ❤️ para el curso de Inteligencia Artificial** 