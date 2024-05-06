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


// Composable que exibe a lista de tarefas
@Composable
fun Checklist(
    tarefas: List<Tarefa>,
    onTarefaConcluidaChange: (Int, Boolean) -> Unit,
    onTarefaExcluir: (Int) -> Unit,
    onTarefaEditar: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        tarefas.forEachIndexed { index, tarefa ->
            var editandoTarefaIndex by remember { mutableStateOf(-1) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Checkbox(
                    checked = tarefa.concluida,
                    onCheckedChange = { isChecked -> onTarefaConcluidaChange(index, isChecked) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (editandoTarefaIndex == index) {
                    var texto by remember { mutableStateOf(tarefa.texto) }
                    TextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = { Text("Editar Tarefa") },
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = {
                            onTarefaEditar(index, texto)
                            editandoTarefaIndex = -1
                        }
                    ) {
                        Text("Salvar")
                    }
                } else {
                    Text(text = tarefa.texto, modifier = Modifier.weight(1f))
                    Button(
                        onClick = { editandoTarefaIndex = index }
                    ) {
                        Text("Editar")
                    }
                    Button(
                        onClick = { onTarefaExcluir(index) },
                        enabled = editandoTarefaIndex == -1 // Desabilita o botão "Remover" quando estiver editando
                    ) {
                        Text("Excluir")
                    }
                }
            }
        }
    }
}

// Composable que representa a tela principal da lista de verificação
@Composable
fun TelaChecklist() {
    var tarefas by remember { mutableStateOf(listaDeTarefas()) }
    var novaTarefaTexto by remember { mutableStateOf("") }
    var editando by remember { mutableStateOf(false) } // Variável de estado para controlar se está editando

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = novaTarefaTexto,
            onValueChange = { novaTarefaTexto = it },
            label = { Text("Nova Tarefa") },
            modifier = Modifier.padding(16.dp),
            enabled = !editando // Desabilita o TextField quando estiver editando
        )

        Button(
            onClick = {
                if (!editando && novaTarefaTexto.isNotBlank()) { // Verifica se não está editando e se há texto na nova tarefa
                    tarefas = tarefas.toMutableList().apply {
                        add(Tarefa(novaTarefaTexto))
                    }
                    novaTarefaTexto = ""
                }
            },
            modifier = Modifier.padding(16.dp),
            enabled = !editando // Desabilita o botão "Nova Tarefa" quando estiver editando
        ) {
            Text("Adicionar Tarefa")
        }

        Checklist(
            tarefas = tarefas,
            onTarefaConcluidaChange = { index, concluida ->
                tarefas = tarefas.mapIndexed { idx, tarefa ->
                    if (idx == index) {
                        tarefa.copy(concluida = concluida)
                    } else {
                        tarefa
                    }
                }
            },
            onTarefaExcluir = { index ->
                tarefas = tarefas.toMutableList().apply {
                    removeAt(index)
                }
            },
            onTarefaEditar = { index, novoTexto ->
                tarefas = tarefas.toMutableList().apply {
                    this[index] = tarefas[index].copy(texto = novoTexto)
                }
            }
        )
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
