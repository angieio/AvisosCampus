# AvisosCampus

## Nombre: Angieluz Vasquez Macalupu

## Descripción

AvisosCampus es una aplicación Android desarrollada con Kotlin, Jetpack Compose y Material 3 que simula avisos académicos. Permite mostrar una notificación inmediata, programar o cancelar un recordatorio de 10 segundos y recibir avisos cuando se activa o desactiva el modo avión. Al tocar una notificación, se abre la aplicación y el aviso desaparece del panel. En Android 13 o superior, la app solicita el permiso de notificaciones. Si el usuario lo deniega, el aviso se guarda en el historial y la pantalla ofrece acceso a los ajustes de notificaciones. El historial muestra los 20 avisos más recientes con su origen, título, texto y hora. El proyecto usa la arquitectura MVVM con un repositorio en memoria, corrutinas para la cuenta regresiva y un `BroadcastReceiver` para detectar los cambios del modo avión.

## Estructura del proyecto

```text
com.tecsup.avisoscampus/
├── AvisosApp.kt
├── MainActivity.kt
├── data/
│   └── AvisosRepository.kt
├── model/
│   └── Modelos.kt
├── notificacion/
│   └── Notificador.kt
├── receiver/
│   └── ModoAvionReceiver.kt
├── ui/
│   ├── HistorialAvisos.kt
│   ├── PantallaAvisos.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── viewmodel/
    ├── AvisosViewModel.kt
    └── AvisosViewModelFactory.kt
```

## Arquitectura MVVM

```text
Usuario
  │ Pulsa un botón o cambia el switch
  ▼
PantallaAvisos ── envía la acción ──► AvisosViewModel
     ▲                                     │
     │                                     │ Registra el aviso
     │                                     ▼
     │                           AvisosRepositoryMemoria
     │                                     │
     │                                     │ Devuelve el historial
     │                                     ▼
     └──── estado, historial y eventos ── AvisosViewModel
```

```text
Publicación:
PantallaAvisos → Notificador → Canal «Avisos» de Android

Modo avión:
Android → ModoAvionReceiver → AvisosViewModel
```

## Capturas

| Notificación en el panel | Recordatorio | Modo avión | Historial | Permiso denegado |
| :---: | :---: | :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/b3496aba-ff9f-4265-8d90-c84a4b120417" width="160" alt="Notificación en el panel"> | <img src="https://github.com/user-attachments/assets/839dcd9e-eaf1-4695-805b-bf360ec19e85" width="160" alt="Recordatorio"> | <img src="https://github.com/user-attachments/assets/8b5845ca-79ac-474f-acc1-633382aeccd9" width="160" alt="Modo avión"> | <img src="https://github.com/user-attachments/assets/6287df33-5893-4bcb-a995-85635f892d37" width="160" alt="Historial de avisos"> | <img src="https://github.com/user-attachments/assets/1d64f1e0-a682-49ee-a1ed-733acffc6a29" width="160" alt="Permiso denegado"> |

## Para pensar

### 1. ¿Por qué una notificación necesita un canal y un permiso? ¿Qué pasa si falta cada uno?

El canal agrupa las notificaciones por categoría y permite que el usuario ajuste su sonido e importancia. Desde Android 8, si AvisosCampus intenta publicar en un canal que no está registrado, la notificación no se muestra. Desde Android 13, si la app no tiene el permiso `POST_NOTIFICATIONS`, Android bloquea sus avisos aunque el canal ya exista.

### 2. ¿Por qué el ViewModel emite un evento y no muestra la notificación él mismo?

El ViewModel decide qué aviso generar, lo registra en el repositorio y emite un evento. La pantalla recibe el evento y llama a `Notificador` con el contexto de Android. Así, la lógica del ViewModel se puede probar de forma independiente.

### 3. ¿Qué pasa si usas el mismo ID en dos notificaciones?

Si la app publica otra notificación con el mismo ID y la misma etiqueta, Android actualiza la que ya estaba en el panel. AvisosCampus usa el ID de cada aviso para que dos pulsaciones de **Mostrar aviso** generen dos notificaciones distintas.

### 4. ¿Por qué `onReceive` debe ser corto y por qué hay que quitar el receptor al salir?

`onReceive` normalmente se ejecuta en el hilo principal, así que un trabajo largo puede bloquear la interfaz. En AvisosCampus, el receptor solo lee el estado del modo avión y lo comunica al ViewModel. La pantalla lo desregistra en `onDispose` para evitar que siga recibiendo eventos o retenga referencias cuando ya no se necesita.

### 5. ¿En qué casos usarías una notificación local y en cuáles una push de Firebase?

Usaría una notificación local para un evento que se programa o detecta en el celular, como un recordatorio o un cambio del modo avión. Usaría Firebase Cloud Messaging para un aviso que viene de un servidor, como un cambio de horario o la publicación de una nota, que deba llegar aunque el usuario no tenga la app abierta.


## Pruebas de aceptación

| Prueba | Resultado esperado | OK |
| --- | --- | :---: |
| Abrir **Ajustes → Apps → AvisosCampus → Notificaciones** | Existe el canal **Avisos**. | ✅ |
| Pulsar **Permitir notificaciones** | Aparece el diálogo de Android y la etiqueta cambia según la respuesta. | ✅ |
| Pulsar **Mostrar aviso** | Aparece la notificación **Tarea lista**. | ✅ |
| Pulsar **Mostrar aviso** dos veces | Aparecen dos notificaciones distintas. | ✅ |
| Tocar la notificación | Se abre AvisosCampus y la notificación desaparece del panel. | ✅ |
| Pulsar **Recordar en 10 s** | Se muestra la cuenta regresiva y se publica el aviso al terminar. | ✅ |
| Iniciar un recordatorio y minimizar la app | El aviso llega al terminar la cuenta. | ✅ |
| Iniciar un recordatorio y cancelarlo | No se publica ningún aviso. | ✅ |
| Activar el switch y cambiar el modo avión | Aparece **Activado** al encender el modo avión y **Desactivado** al apagarlo. | ✅ |
| Desactivar el switch y cambiar el modo avión | No se publica ningún aviso. | ✅ |
| Denegar el permiso y pulsar **Mostrar aviso** | Aparece el mensaje de error, el aviso queda en el historial y la app sigue abierta. | ✅ |
| Rotar la pantalla durante el recordatorio | La cuenta regresiva continúa. | ✅ |
| Revisar el código | El ViewModel no usa `Context` y el receptor se desregistra en `onDispose`. | ✅ |
