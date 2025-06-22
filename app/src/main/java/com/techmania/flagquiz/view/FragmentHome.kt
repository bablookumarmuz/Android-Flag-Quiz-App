package com.techmania.flagquiz.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.techmania.flagquiz.database.DatabaseCopyHelper
import com.techmania.flagquiz.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    // View Binding variable
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        // Create and open the database
        createAndOpenDatabase()

        // Start button click listener to navigate to the quiz fragment
        fragmentHomeBinding.buttonStart.setOnClickListener {
            val direction = FragmentHomeDirections.actionFragmentHomeToFragmentQuiz()
            this.findNavController().navigate(direction)
        }

        return fragmentHomeBinding.root
    }

    // Function to create and open the SQLite database
    private fun createAndOpenDatabase() {
        try {
            val helper = DatabaseCopyHelper(requireActivity())
            helper.createDataBase()
            helper.openDataBase()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
