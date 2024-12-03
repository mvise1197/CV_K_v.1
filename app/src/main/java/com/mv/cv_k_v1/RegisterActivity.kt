package com.mv.cv_k_v1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.core.View

/*class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Obtener referencias a los elementos
        val tvInicio = findViewById<TextView>(R.id.tv_inicio)

        // ENVIAR a "RECUPERAR CONTRASEÑA"
        tvInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}*/

class RegisterActivity : AppCompatActivity() {
    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressDialog
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    /*private var firstName: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var password: String = ""*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialise()

        // Obtener referencias a los elementos
        val tvInicio = findViewById<TextView>(R.id.tv_inicio)

        // ENVIAR a "RECUPERAR CONTRASEÑA"
        tvInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialise() {
        txtName = findViewById(R.id.et_nombre)
        txtEmail = findViewById(R.id.et_correo)
        txtPhone = findViewById(R.id.et_cel)
        txtPassword = findViewById(R.id.et_contrasena)
        btnLogin = findViewById(R.id.btn_login)

        progressBar = ProgressDialog(this)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        databaseReference = database.reference.child("CV_Users")

        // Configurar evento para el botón
        btnLogin.setOnClickListener {
            createNewAccount()
        }
    }

    private fun createNewAccount() {
        val firstName = txtName.text.toString()
        val email = txtEmail.text.toString()
        val phone = txtPhone.text.toString()
        val password = txtPassword.text.toString()

        if (firstName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
            progressBar.setMessage("Registrando usuario...")
            progressBar.show()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser = auth.currentUser!!
                        /*verifyEmail(user)*/
                        val currentUserDb = databaseReference.child(user.uid)
                        currentUserDb.child("name").setValue(firstName)
                        currentUserDb.child("email").setValue(email)
                        currentUserDb.child("phone").setValue(phone)
                        currentUserDb.child("password").setValue(password)
                        updateUserInfoAndGoHome()
                    } else {
                        Toast.makeText(this, "Error en la autenticación.", Toast.LENGTH_SHORT).show()
                    }
                    progressBar.hide()
                }
        } else {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    /*fun register(view: View) {
        createNewAccount()
    }*/

    private fun updateUserInfoAndGoHome() {
        val intent = Intent(this, InicioActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    /*private fun verifyEmail(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verificación enviada al correo: ${user.email}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al enviar la verificación", Toast.LENGTH_SHORT).show()
                }
            }
    }*/
}
