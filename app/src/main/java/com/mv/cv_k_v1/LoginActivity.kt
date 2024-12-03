package com.mv.cv_k_v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Obtener referencias a los elementos
        val etUsername = findViewById<EditText>(R.id.et_correo)
        val etPassword = findViewById<EditText>(R.id.et_contrasena)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvRecuperar = findViewById<TextView>(R.id.tv_recuperar)
        val tvRegistrarse = findViewById<TextView>(R.id.tv_registrarse)

        // Acción de login
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Lógica de validación
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            }
        }

        // ENVIAR a "REGISTRARSE - NUEVO USUARIO"
        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // ENVIAR a "RECUPERAR CONTRASEÑAd"
        tvRecuperar.setOnClickListener {
            // Agrega tu lógica aquí
            Toast.makeText(this, "Recuperar Contraseña", Toast.LENGTH_SHORT).show()
        }
    }
}
