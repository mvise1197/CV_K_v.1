package com.mv.cv_k_v1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class RegistrarseActivity : AppCompatActivity() {
    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var progressBar: ProgressDialog
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)
        initialise()

        // Navegar a la actividad de inicio de sesión
        val tvInicio = findViewById<TextView>(R.id.tv_inicio)
        tvInicio.setOnClickListener {
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialise() {
        txtName = findViewById(R.id.et_nombre)
        txtEmail = findViewById(R.id.et_correo)
        txtPhone = findViewById(R.id.et_cel)
        txtPassword = findViewById(R.id.et_contrasena)
        btnRegister = findViewById(R.id.btn_login)

        progressBar = ProgressDialog(this)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Configurar evento para el botón
        btnRegister.setOnClickListener {
            createNewAccount()
        }
    }

    private fun createNewAccount() {
        val name = txtName.text.toString().trim()
        val email = txtEmail.text.toString().trim()
        val phone = txtPhone.text.toString().trim()
        val password = txtPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.setMessage("Registrando usuario...")
        progressBar.show()

        // Crear usuario en Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser = auth.currentUser!!
                    saveUserDataToFirestore(user.uid, name, email, phone, password)
                } else {
                    progressBar.hide()
                    Toast.makeText(this, "Error en la autenticación: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserDataToFirestore(userId: String, name: String, email: String, phone: String, password: String) {
        // Crear objeto de usuario
        val userData = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "password" to password
        )

        // Guardar datos en Firestore
        firestore.collection("CV_Users").document(userId)
            .set(userData)
            .addOnSuccessListener {
                progressBar.hide()
                Toast.makeText(this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show()
                updateUserInfoAndGoHome()
            }
            .addOnFailureListener { e ->
                progressBar.hide()
                Toast.makeText(this, "Error al guardar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfoAndGoHome() {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
