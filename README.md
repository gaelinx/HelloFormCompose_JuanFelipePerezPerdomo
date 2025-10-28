# Hello From Compose

Una aplicación Android simple construida con **Jetpack Compose** que demuestra el uso de componentes básicos de Material Design 3, manejo de estado, validación de entrada y feedback visual.

Autor: Juan Felipe Perez Perdomo

Ciclo: 2B DAM

Modulo: PGL

## Características

- **TopAppBar personalizada** con colores personalizados
- **Campo de texto con validación** que limita a 20 caracteres
- **Contador de caracteres en tiempo real** (0/20)
- **Feedback visual de error**: El borde del campo se pone rojo por 2 segundos cuando intentas exceder el límite
- **Botón de saludo** que se habilita solo cuando hay texto válido
- **Mensaje de bienvenida personalizado** que aparece al presionar el botón
- **Ocultamiento automático del teclado** al presionar el botón
- **Soporte para scroll vertical** para adaptarse a diferentes tamaños de pantalla
- **Persistencia de estado** al rotar la pantalla

## Tecnologías Utilizadas

- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Framework UI moderno de Android
- **Material Design 3** - Componentes de diseño
- **Coroutines** - Para el manejo del temporizador de error

## Estructura del Proyecto

```
com.example.hellofromcompose/
├── MainActivity.kt          # Activity principal
├── ViewConteiner()         # Scaffold con TopBar
├── HelloFrom()             # Contenido principal con formulario
└── Toolbar()               # Barra superior personalizada
```

## Componentes Principales

### 1. TopAppBar (Toolbar)
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar()
```
- Título: "Hello From Compose"
- Color de fondo personalizado (`R.color.background`)
- Texto en color blanco

### 2. OutlinedTextField
```kotlin
OutlinedTextField(
    value = name,
    onValueChange = {...},
    label = { Text(text = "Introduce Tu Nombre") }
)
```
**Características:**
- Límite máximo: 20 caracteres
- Contador de caracteres en tiempo real
- Validación visual con borde rojo (2 segundos) al exceder límite
- Label personalizado

### 3. Button (Saludar)
```kotlin
Button(
    onClick = {...},
    enabled = name.isNotBlank()
)
```
**Comportamiento:**
- Se habilita solo cuando hay texto válido (no vacío ni solo espacios)
- Oculta el teclado al presionar
- Muestra el mensaje de saludo

### 4. Mensaje de Saludo
```kotlin
Text(
    text = "Hola $name, ¿como estas?",
    style = MaterialTheme.typography.headlineSmall
)
```
- Aparece dinámicamente al presionar el botón
- Personalizado con el nombre ingresado
- Estilo de tipografía Material Design

## Flujo de la Aplicación

1. **Usuario abre la app** → Ve el formulario con campo de texto vacío
2. **Usuario escribe su nombre** → El contador aumenta (ej: 3/20)
3. **Usuario intenta exceder 20 caracteres** → Borde se pone rojo por 2 segundos
4. **Usuario presiona "Saludar"** → 
   - Teclado se oculta
   - Aparece mensaje: "Hola [nombre], ¿como estas?"
5. **Usuario empieza a escribir de nuevo** → El mensaje de saludo desaparece
<img width="451" height="893" alt="image" src="https://github.com/user-attachments/assets/46eb1f8b-b962-46ad-967c-2e9b5e829cc8" />
<img width="483" height="880" alt="image" src="https://github.com/user-attachments/assets/ba343d6a-2113-4695-94ff-c98da3500a5d" />
<img width="834" height="533" alt="image" src="https://github.com/user-attachments/assets/5510c360-6cee-4607-a23e-6fca1e1efbc2" />


Los datos Persisten aunque la aplicacion cambie de rotacion (rememberSaveable)
<img width="510" height="285" alt="image" src="https://github.com/user-attachments/assets/978fd153-27e0-4704-aa1e-f2ae575b5d2a" />


## Conceptos de Compose Demostrados

### Manejo de Estado
```kotlin
var name by rememberSaveable { mutableStateOf("") }
var showGreeting by rememberSaveable { mutableStateOf(false) }
var showError by rememberSaveable { mutableStateOf(false) }
```
- `rememberSaveable` preserva el estado durante cambios de configuración (rotación)
- `mutableStateOf` crea variables observables que recomponen la UI

### LaunchedEffect (Coroutines)
```kotlin
LaunchedEffect(showError) {
    if (showError) {
        kotlinx.coroutines.delay(2000)
        showError = false
    }
}
```
- Ejecuta código suspendido cuando cambia `showError`
- Implementa el temporizador de 2 segundos para el error

### Composición Condicional
```kotlin
if (showGreeting && name.isNotEmpty()) {
    Text("Hola $name, ¿como estas?")
}
```
- Renderizado condicional basado en el estado

### Modificadores (Modifiers)
```kotlin
Modifier
    .fillMaxSize()
    .padding(paddingValues)
    .padding(16.dp)
    .verticalScroll(rememberScrollState())
```
- Cadena de modificadores para diseño y comportamiento

## Personalización de Colores

### Colores Personalizados (colors.xml)
```xml
<color name="background">#COLOR_AZUL</color>
<color name="white">#FFFFFF</color>
<color name="teal_700">#TEAL_COLOR</color>
```

### Aplicación de Colores
- **TopAppBar**: `R.color.background` (fondo), `R.color.white` (texto)
- **Button**: `R.color.teal_700`
- **TextField border**: `R.color.background` (normal), rojo (error)
- **Saludo**: `R.color.teal_700`

## Validaciones Implementadas

1. **Límite de caracteres**: Máximo 20 caracteres
2. **Validación de espacios**: `isNotBlank()` evita nombres con solo espacios
3. **Feedback visual**: Borde rojo temporal al intentar exceder límite
4. **Botón deshabilitado**: No se puede presionar con campo vacío

## Cómo Ejecutar

1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza las dependencias de Gradle
4. Ejecuta en un emulador o dispositivo físico (API 21+)

## Requisitos

- **Android Studio**: Hedgehog o superior
- **Min SDK**: 23 (Android 6.0)
- **Target SDK**: 34 o superior
- **Kotlin**: 1.9.0+
- **Compose**: Material3

## 🔧 Dependencias Principales

```gradle
implementation "androidx.compose.material3:material3"
implementation "androidx.compose.ui:ui"
implementation "androidx.activity:activity-compose"
```

## Notas Técnicas

- **Edge-to-Edge**: La app usa `enableEdgeToEdge()` para aprovechar toda la pantalla
- **Scaffold**: Proporciona estructura Material con TopBar y manejo automático de padding
- **Keyboard Controller**: Usa `LocalSoftwareKeyboardController` para ocultar el teclado
- **Scroll**: Implementa `verticalScroll()` para contenido que excede la pantalla

## 🎓 Aprendizajes Clave

Este proyecto demuestra:
- Composición básica de UI con Compose
- Manejo de estado mutable
- Validación de entrada de usuario
- Feedback visual con animaciones temporales
- Personalización de colores en Material3
- Uso de Coroutines en Compose
- Layouts responsivos con Scaffold y Column
