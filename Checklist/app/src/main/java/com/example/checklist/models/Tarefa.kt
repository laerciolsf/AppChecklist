package com.example.checklist.models



// Classe de dados que representa uma tarefa na lista de verificação
data class Tarefa(val texto: String, var concluida: Boolean = false)

// Função que retorna uma lista inicial de tarefas
fun listaDeTarefas(): List<Tarefa> {
    return listOf(
        Tarefa("Comprar leite"),
        Tarefa("Ligar para o médico"),
        Tarefa("Estudar para a prova")
    )
}