package com.example.checklist.view

// Importando classes necessárias do Jetpack Compose
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

// Função composable para exibir a lista de tarefas
@Composable
fun Checklist(
    tarefas: List<Tarefa>,
    onTarefaConcluidaChange: (Int, Boolean) -> Unit,
    onTarefaExcluir: (Int) -> Unit,
    onTarefaEditar: (Int, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Preenche toda a altura disponível
        contentPadding = PaddingValues(vertical = 8.dp) // Define espaçamento interno
    ) {
        items(tarefas.size) { index ->
            val tarefa = tarefas[index]
            var editandoTarefaIndex by remember { mutableStateOf(-1) } // Estado para controlar a tarefa em edição

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp) // Define padding interno
                    .fillMaxWidth() // Preenche a largura disponível
            ) {
                Checkbox(
                    checked = tarefa.concluida,
                    onCheckedChange = { isChecked -> onTarefaConcluidaChange(index, isChecked) }
                )
                Spacer(modifier = Modifier.width(8.dp)) // Define um espaço entre o Checkbox e o Texto

                if (editandoTarefaIndex == index) { // Verifica se esta tarefa está sendo editada
                    var texto by remember { mutableStateOf(tarefa.texto) }
                    TextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = { Text("Editar Tarefa") },
                        modifier = Modifier.weight(1f) // Preenche o espaço restante
                    )
                    Button(
                        onClick = {
                            onTarefaEditar(index, texto)
                            editandoTarefaIndex = -1 // Termina a edição
                        }
                    ) {
                        Text("Salvar")
                    }
                } else { // Se esta tarefa não está sendo editada
                    Text(text = tarefa.texto, modifier = Modifier.weight(1f)) // Exibe o texto da tarefa
                    // Botão de edição substituído por IconButton com ícone de edição
                    IconButton(
                        onClick = { editandoTarefaIndex = index },
                        enabled = editandoTarefaIndex == -1 // Desabilita se outra tarefa estiver em edição
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit, // Usa ícone de edição
                            contentDescription = "Editar"
                        )
                    }
                    // Botão de exclusão substituído por IconButton com ícone de lixeira
                    IconButton(
                        onClick = { onTarefaExcluir(index) },
                        enabled = editandoTarefaIndex == -1 // Desabilita se outra tarefa estiver em edição
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete, // Usa ícone de exclusão
                            contentDescription = "Excluir"
                        )
                    }
                }
            }
        }
    }
}
