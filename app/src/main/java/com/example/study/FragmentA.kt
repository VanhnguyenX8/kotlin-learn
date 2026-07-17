package com.example.study

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
class FragmentA  : Fragment (R.layout.fragment_a){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnNext).setOnClickListener {
            /// dung voi bundle thi bat nhu key
            val bundle = bundleOf("userId" to "123")
            findNavController().navigate(R.id.action_A_to_B, bundle)
        }
    }
}