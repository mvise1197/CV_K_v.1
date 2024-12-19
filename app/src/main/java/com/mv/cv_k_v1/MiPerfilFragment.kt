package com.mv.cv_k_v1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MiPerfilFragment : Fragment() {
    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var et_cel: TextView

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_miperfil, container, false)

        txtName = view.findViewById(R.id.txtName)
        txtEmail = view.findViewById(R.id.txtEmail)
        et_cel = view.findViewById(R.id.et_cel)

        loadUserProfile()

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserProfile() {
        val uid = auth.currentUser?.uid ?: return

        firestore.collection("CV_Users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.getString("name") ?: "Sin nombre"
                    val email = document.getString("email") ?: "Sin correo"
                    val phone = document.getString("phone") ?: "Sin teléfono"

                    // Mostrar datos en las vistas
                    txtName.text = "Usuario: $name"
                    txtEmail.text = "Correo: $email"
                    et_cel.text = "Celular: $phone"
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}