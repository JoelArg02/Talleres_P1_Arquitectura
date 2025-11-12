# Cliente Móvil Android - EurekaBank REST

Aplicación móvil Android que consume la API REST de EurekaBank.

## Características

- Login de usuarios
- Consulta de movimientos por cuenta
- Visualización de balances de todas las cuentas
- Registro de depósitos
- Registro de retiros
- Registro de transferencias entre cuentas

## Arquitectura

- **MVVM (Model-View-ViewModel)**
- **Repository Pattern**
- **Retrofit** para consumo de API REST
- **Coroutines** para operaciones asíncronas
- **LiveData** para observación de datos

## Tecnologías

- Kotlin
- Android SDK 33+
- Retrofit 2.11.0
- OkHttp 4.12.0
- Coroutines 1.9.0
- Material Design Components

## Configuración

### Base URL
Por defecto la aplicación apunta a: `http://10.0.2.2:8080/eurekabank/api/eureka/`

Para cambiar la URL, editar el archivo:
`app/src/main/java/ec/edu/espe/rest_java_bank/data/remote/RetrofitClient.kt`

### Credenciales de prueba
- Usuario: `admin`
- Contraseña: `123456`

## Estructura del Proyecto

```
app/src/main/java/ec/edu/espe/rest_java_bank/
├── data/
│   ├── models/           # Modelos de datos
│   ├── remote/           # Configuración Retrofit y API Service
│   └── repository/       # Repositorio de datos
├── ui/
│   ├── activity/         # Activities
│   ├── adapter/          # Adapters para RecyclerView
│   ├── fragments/        # Fragments
│   └── viewmodel/        # ViewModels
└── res/                  # Recursos (layouts, drawables, etc)
```

## API REST

Ver documentación completa en `API_DOCUMENTATION.md`

### Endpoints principales:

- `POST /login` - Autenticación
- `GET /movimientos/{cuenta}` - Consultar movimientos
- `GET /balances` - Obtener balances
- `POST /deposito` - Registrar depósito
- `POST /retiro` - Registrar retiro
- `POST /transferencia` - Registrar transferencia

## Compilación

```bash
./gradlew assembleDebug
```

## Instalación

```bash
./gradlew installDebug
```

## Notas Importantes

1. El servidor REST debe estar ejecutándose en `localhost:8080`
2. La aplicación requiere permisos de Internet
3. Se usa `usesCleartextTraffic="true"` para desarrollo (remover en producción)
4. El emulador Android usa `10.0.2.2` para acceder a `localhost` del host
