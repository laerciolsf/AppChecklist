package com.example.checklist.viewModels

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
import com.example.checklist.view.Checklist

// ViewModel que gerencia o estado e as operações na tela da lista de verificação
@Composable
fun TelaChecklist() {
    // Define o estado das tarefas, inicializando com uma lista de tarefas padrão
    var tarefas by remember { mutableStateOf(listaDeTarefas()) }
    // Define o estado do texto da nova tarefa a ser adicionada
    var novaTarefaTexto by remember { mutableStateOf("") }
    // Define o estado do indicador de edição de tarefa
    var editando by remember { mutableStateOf(false) }

    // Define a estrutura da tela da lista de verificação
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto para adicionar nova tarefa
        TextField(
            value = novaTarefaTexto,
            onValueChange = { novaTarefaTexto = it },
            label = { Text("Nova Tarefa") },
            modifier = Modifier.padding(16.dp),
            enabled = !editando // Habilita apenas se não estiver editando
        )

        // Botão para adicionar uma nova tarefa
        Button(
            onClick = {
                if (!editando && novaTarefaTexto.isNotBlank()) { // Verifica se não está editando e o campo de texto não está em branco
                    // Adiciona a nova tarefa à lista de tarefas e limpa o campo de texto
                    tarefas = tarefas.toMutableList().apply {
                        add(Tarefa(novaTarefaTexto))
                    }
                    novaTarefaTexto = ""
                }
            },
            modifier = Modifier.padding(16.dp),
            enabled = !editando // Habilita apenas se não estiver editando
        ) {
            Text("Adicionar Tarefa")
        }

        // Componente de lista de verificação para exibir e gerenciar as tarefas existentes
        Checklist(
            tarefas = tarefas,
            // Callback para atualizar o estado da conclusão de uma tarefa
            onTarefaConcluidaChange = { index, concluida ->
                tarefas = tarefas.mapIndexed { idx, tarefa ->
                    if (idx == index) {
                        tarefa.copy(concluida = concluida)
                    } else {
                        tarefa
                    }
                }
            },
            // Callback para excluir uma tarefa
            onTarefaExcluir = { index ->
                tarefas = tarefas.toMutableList().apply {
                    removeAt(index)
                }
            },
            // Callback para editar uma tarefa
            onTarefaEditar = { index, novoTexto ->
                tarefas = tarefas.toMutableList().apply {
                    this[index] = tarefas[index].copy(texto = novoTexto)
                }
            }
        )
    }
}
