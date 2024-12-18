package com.mv.cv_k_v1

import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearPetsFragment : Fragment(R.layout.fragment_crearpets) {

    private lateinit var nomPet: EditText
    private lateinit var espPet: Spinner
    private lateinit var edadPet: EditText
    private lateinit var kgPet: EditText
    private lateinit var rzPet: EditText
    private lateinit var sexPet: Spinner
    private lateinit var vacPet: EditText
    private lateinit var alergPet: EditText

    private lateinit var btnPet: Button

    private val db = Firebase.firestore

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nomPet = view.findViewById(R.id.nomPet)
        espPet = view.findViewById(R.id.espPet)
        edadPet = view.findViewById(R.id.edadPet)
        kgPet  = view.findViewById(R.id.kgPet)
        rzPet = view.findViewById(R.id.rzPet)
        sexPet = view.findViewById(R.id.sexPet)
        vacPet = view.findViewById(R.id.vacPet)
        alergPet = view.findViewById(R.id.alergPet)

        btnPet = view.findViewById(R.id.btnPet)

        val espOp = arrayOf("Seleccionar", "Perro", "Gato", "Chivo", "Conejo")
        val sexOp = arrayOf("Seleccionar", "Hembra", "Macho")

        val espAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, espOp)
        espAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        espPet.adapter = espAdapter

        val sexAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexOp)
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexPet.adapter = sexAdapter

        btnPet.setOnClickListener{
            val nom = nomPet.text.toString()
            val esp = espPet.selectedItem.toString()
            val edad = edadPet.text.toString()
            val kg = kgPet.text.toString()
            val rz = rzPet.text.toString()
            val sex = sexPet.selectedItem.toString()
            val vac = vacPet.text.toString()
            val alerg = alergPet.text.toString()

            val pet = hashMapOf(
                "nom" to nom,
                "esp" to esp,
                "edad" to edad,
                "kg" to kg,
                "rz" to rz,
                "sex" to sex,
                "vac" to vac,
                "alerg" to alerg
            )

            db.collection("pets")
                .add(pet)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Mascota registrada con éxito", Toast.LENGTH_SHORT).show()
                    nomPet.text.clear()
                    espPet.setSelection(0)
                    edadPet.text.clear()
                    kgPet.text.clear()
                    rzPet.text.clear()
                    sexPet.setSelection(0)
                    vacPet.text.clear()
                    alergPet.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), "Error al registrar mascota", Toast.LENGTH_SHORT).show()
                }
        }
    }
}