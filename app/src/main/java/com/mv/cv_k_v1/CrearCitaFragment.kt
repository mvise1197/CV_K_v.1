package com.mv.cv_k_v1

import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearCitaFragment : Fragment(R.layout.fragment_crearcita) {

    private lateinit var nomPet: EditText
    private lateinit var mcit: EditText
    private lateinit var fcit: EditText
    private lateinit var hcit: EditText

    private lateinit var btnCita: Button

    private val db = Firebase.firestore

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nomPet = view.findViewById(R.id.nomPet)
        mcit = view.findViewById(R.id.mcit)
        fcit = view.findViewById(R.id.fcit)
        hcit  = view.findViewById(R.id.hcit)

        btnCita = view.findViewById(R.id.btnCita)

        btnCita.setOnClickListener{
            val nom = nomPet.text.toString()
            val mot = mcit.text.toString()
            val fech = fcit.text.toString()
            val hor = hcit.text.toString()

            val pet = hashMapOf(
                "nom" to nom,
                "mot" to mot,
                "fech" to fech,
                "hor" to hor
            )

            db.collection("citas")
                .add(pet)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Cita registrada con éxito", Toast.LENGTH_SHORT).show()
                    nomPet.text.clear()
                    mcit.setSelection(0)
                    fcit.text.clear()
                    hcit.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), "Error al registrar cita", Toast.LENGTH_SHORT).show()
                }
        }

    }
}