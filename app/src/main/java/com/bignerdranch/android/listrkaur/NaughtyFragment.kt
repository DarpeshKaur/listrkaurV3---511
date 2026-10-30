package com.bignerdranch.android.listrkaur

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.listrkaur.databinding.FragmentNaughtyBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

private const val TAG = "NaughtyFragment"

class NaughtyFragment : Fragment() {

    private var _binding: FragmentNaughtyBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Unable to access binding. Is the view created?"
        }

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNaughtyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            addPerson()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addPerson() {
        val name = binding.nameText.text.toString().trim()
        if (name.isEmpty()) return

        val person = hashMapOf("name" to name)

        db.collection("naughty").document()
            .set(person)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                _binding?.nameText?.text?.clear()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
            }
    }
}
