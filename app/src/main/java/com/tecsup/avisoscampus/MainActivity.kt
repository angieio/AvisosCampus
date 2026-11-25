package com.tecsup.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tecsup.avisoscampus.ui.PantallaAvisos
import com.tecsup.avisoscampus.ui.theme.AvisosCampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvisosCampusTheme {
                PantallaAvisos()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaAvisosPreview() {
    AvisosCampusTheme {
        PantallaAvisos()
    }
}
