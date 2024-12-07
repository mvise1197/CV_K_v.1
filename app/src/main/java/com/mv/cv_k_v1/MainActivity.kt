package com.mv.cv_k_v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //LLAMAR AL BOTÓN PARA INGRESAR A LA VISTA DE INICIAR SESIÓN O REGISTRARSE
        val isButton: Button = findViewById(R.id.btn_if)

        //BOTÓN PARA INGRESAR A LA VISTA DE INICIAR SESIÓN O REGISTRARSE
        isButton.setOnClickListener {
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
    }
}