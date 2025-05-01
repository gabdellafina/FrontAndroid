package br.edu.fatecpg.frontandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.fatecpg.frontandroid.model.User

class LoginViewModel : ViewModel() {

    private val _users = mutableListOf<User>()
    val users: List<User> get() = _users

    fun register(login: String, password: String): Boolean {
        if (_users.any { it.login == login }) {
            return false
        }
        _users.add(User(login, password))
        return true
    }

    fun login(login: String, password: String): Boolean {
        val user = _users.find { it.login == login } ?: return false

        if (user.bloqueado) return false

        return if (user.password == password) {
            user.tentativas = 0
            true
        } else {
            user.tentativas++
            if (user.tentativas >= 3) {
                user.bloqueado = true
            }
            false
        }
    }

    fun getAllUsers(): List<User> {
        return _users
    }
}