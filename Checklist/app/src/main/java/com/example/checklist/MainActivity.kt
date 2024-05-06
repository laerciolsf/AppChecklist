package com.example.checklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChecklistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelaChecklist()
                }
            }
        }
    }
}

data class Tarefa(val texto: String, var concluida: Boolean = false)

@Composable
fun Checklist(
    tarefas: List<Tarefa>,
    onTarefaConcluidaChange: (Int, Boolean) -> Unit,
    onTarefaExcluir: (Int) -> Unit,
    onTarefaEditar: (Int, String) -> Unit
) {
    // Substituído Column por LazyColumn para renderização eficiente
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        // Usando items para iterar sobre a lista de tarefas
        items(tarefas.size) { index ->
            val tarefa = tarefas[index]
            var editandoTarefaIndex by remember { mutableStateOf(-1) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
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
                        enabled = editandoTarefaIndex == -1
                    ) {
                        Text("Excluir")
                    }
                }
            }
        }
    }
}

@Composable
fun TelaChecklist() {
    var tarefas by remember { mutableStateOf(listaDeTarefas()) }
    var novaTarefaTexto by remember { mutableStateOf("") }
    var editando by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = novaTarefaTexto,
            onValueChange = { novaTarefaTexto = it },
            label = { Text("Nova Tarefa") },
            modifier = Modifier.padding(16.dp),
            enabled = !editando
        )

        Button(
            onClick = {
                if (!editando && novaTarefaTexto.isNotBlank()) {
                    tarefas = tarefas.toMutableList().apply {
                        add(Tarefa(novaTarefaTexto))
                    }
                    novaTarefaTexto = ""
                }
            },
            modifier = Modifier.padding(16.dp),
            enabled = !editando
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

fun listaDeTarefas(): List<Tarefa> {
    return listOf(
        Tarefa("Comprar leite"),
        Tarefa("Ligar para o médico"),
        Tarefa("Estudar para a prova")
    )
}

@Preview(showBackground = true)
@Composable
fun PreviaTelaChecklist() {
    TelaChecklist()
}
