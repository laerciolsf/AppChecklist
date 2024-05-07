package com.example.checklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.checklist.ui.theme.ChecklistTheme
import com.example.checklist.viewModels.TelaChecklist

// Classe principal da atividade, ponto de entrada do aplicativo Android
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChecklistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chama o composable TelaChecklist para exibir a lista de tarefas
                    TelaChecklist()
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviaTelaChecklist() {
    TelaChecklist()
}
