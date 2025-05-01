package br.edu.fatecpg.frontandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.fatecpg.frontandroid.databinding.ActivityAdminBinding
import br.edu.fatecpg.frontandroid.viewmodel.LoginViewModel
import br.edu.fatecpg.frontandroid.databinding.ActivityMainBinding
import br.edu.fatecpg.frontandroid.model.User


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val lvm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAdmin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("users", ArrayList(lvm.getAllUsers()))
            startActivity(intent)
        }

        binding.btnCad.setOnClickListener {
            val login = binding.edtLogin.text.toString()
            val pass = binding.edtPass.text.toString()
            val result = lvm.register(login, pass)
            Log.d("Cadastro", result.toString())
            if (result) {
                Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Usuário já existente", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val login = binding.edtLogin.text.toString()
            val pass = binding.edtPass.text.toString()
            val result = lvm.login(login, pass)
            val user = lvm.getAllUsers().find { it.login == login }
            Log.d("Logado", result.toString())
            if (result) {
                Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
            } else {
                if (user != null && user.bloqueado) {
                    Toast.makeText(
                        this,
                        "Usuário bloqueado por tentativas excessivas",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}