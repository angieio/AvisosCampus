package com.tecsup.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaAvisos = lightColorScheme(
    primary = AzulAvisos,
    onPrimary = Color.White,
    background = FondoAvisos,
    surface = Color.White
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaAvisos,
        typography = Typography,
        content = content
    )
}
