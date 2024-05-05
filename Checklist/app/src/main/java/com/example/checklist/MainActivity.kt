package com.example.checklist // Pacote onde a classe MainActivity está localizada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.checklist.ui.theme.ChecklistTheme

// Classe principal da atividade, ponto de entrada do aplicativo Android
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define a IU usando Jetpack Compose
        setContent {
            // Define o tema global do aplicativo
            ChecklistTheme {
                // Superfície que representa a tela onde o conteúdo é exibido
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

// Classe de dados que representa uma tarefa na lista de verificação
data class Tarefa(val texto: String, var concluida: Boolean = false)

// Composable que representa a tela principal da lista de verificação

@Composable
fun TelaChecklist() {
    // Mantém o estado das tarefas usando mutableStateOf
    var tarefas by remember { mutableStateOf(listaDeTarefas()) }

    // Mantém o estado do texto da nova tarefa
    var novaTarefaTexto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TextField para adicionar uma nova tarefa
        TextField(
            value = novaTarefaTexto,
            onValueChange = { novaTarefaTexto = it },
            label = { Text("Nova Tarefa") },
            modifier = Modifier.padding(16.dp)
        )

        // Botão para adicionar a nova tarefa à lista
        Button(
            onClick = {
                if (novaTarefaTexto.isNotBlank()) {
                    tarefas = tarefas.toMutableList().apply {
                        add(Tarefa(novaTarefaTexto))
                    }
                    novaTarefaTexto = ""
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Adicionar Tarefa")
        }

        // Chama o composable Checklist para exibir a lista de tarefas
        Checklist(tarefas = tarefas) { index, concluida ->
            tarefas = tarefas.mapIndexed { idx, tarefa ->
                if (idx == index) {
                    tarefa.copy(concluida = concluida)
                } else {
                    tarefa
                }
            }
        }
    }
}

// Composable que exibe a lista de tarefas
@Composable
fun Checklist(tarefas: List<Tarefa>, onTarefaConcluidaChange: (Int, Boolean) -> Unit) {
    // Organiza as tarefas verticalmente usando Column
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Para cada tarefa na lista
        tarefas.forEachIndexed { index, tarefa ->
            // Cria uma linha que contém um Checkbox e um Text
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Checkbox para marcar a tarefa como concluída
                Checkbox(
                    checked = tarefa.concluida,
                    onCheckedChange = { isChecked -> onTarefaConcluidaChange(index, isChecked) }
                )
                // Espaçamento entre o Checkbox e o Text
                Spacer(modifier = Modifier.width(8.dp))
                // Texto da descrição da tarefa
                Text(text = tarefa.texto)
            }
        }
    }
}

// Função que retorna uma lista de tarefas predefinidas
fun listaDeTarefas(): List<Tarefa> {
    return listOf(
        Tarefa("Comprar leite"),
        Tarefa("Ligar para o médico"),
        Tarefa("Estudar para a prova")
    )
}

// Composable usado para visualização no Android Studio
@Preview(showBackground = true)
@Composable
fun PreviaTelaChecklist() {
    // Mostra como a tela da lista de verificação será renderizada
    TelaChecklist()
}
