package br.edu.fatecpg.frontandroid.model

import java.io.Serializable

data class User(
    val login: String,
    var password: String,
    var tentativas: Int = 0,
    var bloqueado: Boolean = false
) : Serializable