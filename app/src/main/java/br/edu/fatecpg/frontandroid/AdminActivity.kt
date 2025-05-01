package br.edu.fatecpg.frontandroid

import android.annotation.SuppressLint
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.fatecpg.frontandroid.R
import br.edu.fatecpg.frontandroid.databinding.ActivityAdminBinding
import br.edu.fatecpg.frontandroid.databinding.ActivityMainBinding
import br.edu.fatecpg.frontandroid.model.User
import br.edu.fatecpg.frontandroid.viewmodel.LoginViewModel

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val users = intent.getSerializableExtra("users") as? ArrayList<User> ?: arrayListOf()

        val adapter = object : ArrayAdapter<User>(this, R.layout.item_usuario, users) {
            @SuppressLint("MissingInflatedId")
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = layoutInflater.inflate(R.layout.item_usuario, parent, false)
                val txvLogin = view.findViewById<TextView>(R.id.txvLogin)
                val txvSit = view.findViewById<TextView>(R.id.txvSit)

                val user = users[position]
                txvLogin.text = user.login
                txvSit.text = if (user.bloqueado) "Bloqueado" else "Ativo"
                txvSit.setTextColor(
                    if (user.bloqueado)
                        resources.getColor(R.color.inativo)
                    else
                        resources.getColor(R.color.azul_ativo) // Defina esta cor em colors.xml
                )

                return view
            }
        }

        binding.listUsers.adapter = adapter
    }
}