package com.example.checklist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.checklist.models.Tarefa
import com.example.checklist.models.listaDeTarefas
import com.example.checklist.viewModels.Checklist

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
