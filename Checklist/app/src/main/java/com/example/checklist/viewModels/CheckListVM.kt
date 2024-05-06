package com.example.checklist.viewModels

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun Checklist(
    tarefas: List<Tarefa>,
    onTarefaConcluidaChange: (Int, Boolean) -> Unit,
    onTarefaExcluir: (Int) -> Unit,
    onTarefaEditar: (Int, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
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
                    // Substitua o botão "Editar" pelo IconButton com o ícone de edição
                    IconButton(
                        onClick = { editandoTarefaIndex = index },
                        enabled = editandoTarefaIndex == -1
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit, // Use o ícone de edição
                            contentDescription = "Editar"
                        )
                    }
                    // Substituindo o botão "Excluir" por um IconButton com o ícone de lixeira
                    IconButton(
                        onClick = { onTarefaExcluir(index) },
                        enabled = editandoTarefaIndex == -1
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Excluir"
                        )
                    }
                }
            }
        }
    }
}


