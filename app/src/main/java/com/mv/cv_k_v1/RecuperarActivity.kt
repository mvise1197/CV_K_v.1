package com.mv.cv_k_v1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecuperarActivity : AppCompatActivity() {

    private lateinit var etCorreo: EditText
    private lateinit var btnEnviar: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)
        initialise()

        // Navegar a la actividad de registro
        val tvRegistrarse = findViewById<TextView>(R.id.tv_inicio)
        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialise() {
        etCorreo = findViewById(R.id.et_correo)
        btnEnviar = findViewById(R.id.btn_enviar)
        mAuth = FirebaseAuth.getInstance()

        // Configurar el evento para el botón de enviar correo
        btnEnviar.setOnClickListener {
            sendPasswordResetEmail()
        }
    }

    private fun sendPasswordResetEmail() {
        val email = etCorreo.text.toString().trim()

        if (email.isNotEmpty()) {
            mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email de recuperación enviado", Toast.LENGTH_SHORT).show()
                        goInicio()
                    } else {
                        Toast.makeText(this, "No se encontró el usuario con este correo", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Por favor, ingresa un correo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goInicio() {
        val intent = Intent(this, IniciarSesionActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
