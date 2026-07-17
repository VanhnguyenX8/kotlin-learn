package com.example.study

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FragmentB : Fragment(R.layout.fragment_b) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /// value
        var userId = arguments?.getString("userId").orEmpty()
        print("Anhnv debug: $userId")
        view.findViewById<Button>(R.id.btnNext).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}