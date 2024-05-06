package com.example.checklist.models



data class Tarefa(val texto: String, var concluida: Boolean = false)

fun listaDeTarefas(): List<Tarefa> {
    return listOf(
        Tarefa("Comprar leite"),
        Tarefa("Ligar para o médico"),
        Tarefa("Estudar para a prova")
    )
}