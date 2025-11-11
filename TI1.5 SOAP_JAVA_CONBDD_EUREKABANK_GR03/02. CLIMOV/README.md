# EurekaBank - Aplicación Móvil Android

Aplicación móvil bancaria que consume servicios SOAP Java para operaciones bancarias.

## Arquitectura

### Capas

1. **Data Layer** (`data/`)
   - `models/`: Modelos de datos (Cuenta, Movimiento, OperacionResult)
   - `remote/`: Cliente SOAP para comunicación con el servicio web
   - `repository/`: Repositorio que abstrae la fuente de datos

2. **Domain Layer** (Implícito en ViewModels)
   - Lógica de negocio y validaciones

3. **UI Layer** (`ui/`)
   - `activity/`: Activities (Login, Main, MovimientosTabla)
   - `fragments/`: Fragments para cada funcionalidad
   - `adapter/`: Adapters para RecyclerView
   - `viewmodel/`: ViewModels con LiveData para manejar estado

## Características

- Login con validación
- Depósitos bancarios
- Retiros bancarios
- Transferencias entre cuentas
- Consulta de movimientos
- Consulta de balances
- Navegación lateral con diseño similar a la aplicación de escritorio

## Tecnologías

- Kotlin
- MVVM Architecture
- LiveData & ViewModel
- Coroutines
- OkHttp para SOAP
- RecyclerView

## Configuración

El servicio SOAP está configurado para usar:
- URL: `http://10.0.2.2:8080/WS_ConUni_SOAPJAVA_GR03/WSEureka`
- (10.0.2.2 es el localhost del emulador de Android)

Para usar en dispositivo físico, cambiar la IP en `SoapClient.kt` a la IP de tu máquina.
