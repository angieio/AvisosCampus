package com.tecsup.avisoscampus.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tecsup.avisoscampus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}

/*
// Dentro de PantallaAvisos (ya viene hecho):
val contexto = LocalContext.current
LaunchedEffect(Unit) {
    vm.eventos.collect { aviso ->
        val mostrado = Notificador.mostrar(contexto, aviso)
// TODO Paso 8: si no se mostró, avisar al ViewModel
    }
}
*/
