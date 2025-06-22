package com.techmania.flagquiz.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.techmania.flagquiz.R
import com.techmania.flagquiz.database.DatabaseCopyHelper
import com.techmania.flagquiz.database.FlagsDao
import com.techmania.flagquiz.databinding.FragmentQuizBinding
import com.techmania.flagquiz.model.FlagsModel

class FragmentQuiz : Fragment() {

    lateinit var fragmentQuizBinding: FragmentQuizBinding
    var flagList = ArrayList<FlagsModel>()

    var correctNumber = 0
    var wrongNumber = 0
    var emptyNumber = 0
    var questionNumber = 0

    lateinit var correctFlag: FlagsModel
    var wrongFlags = ArrayList<FlagsModel>()

    val dao = FlagsDao()

    var optionControl = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentQuizBinding = FragmentQuizBinding.inflate(inflater, container, false)

        // Correct usage of DatabaseCopyHelper
        flagList = dao.getRandomTenRecords(DatabaseCopyHelper(requireActivity()))

        for (flag in flagList) {
            Log.d("flags", flag.id.toString())
            Log.d("flags", flag.countryName)
            Log.d("flags", flag.flagName)
            Log.d("flags", "**************************")
        }

        showData()

        fragmentQuizBinding.buttonA.setOnClickListener { answerControl(fragmentQuizBinding.buttonA) }
        fragmentQuizBinding.buttonB.setOnClickListener { answerControl(fragmentQuizBinding.buttonB) }
        fragmentQuizBinding.buttonC.setOnClickListener { answerControl(fragmentQuizBinding.buttonC) }
        fragmentQuizBinding.buttonD.setOnClickListener { answerControl(fragmentQuizBinding.buttonD) }

        fragmentQuizBinding.buttonNext.setOnClickListener {

            questionNumber++

            if (questionNumber > 9) {

                if (!optionControl) {
                    emptyNumber++
                }

                val direction = FragmentQuizDirections.actionFragmentQuizToFragmentResult().apply {
                    correct = correctNumber
                    wrong = wrongNumber
                    empty = emptyNumber
                }

                this.findNavController().apply {
                    navigate(direction)
                    popBackStack(R.id.fragmentResult, false)
                }

            } else {

                showData()

                if (!optionControl) {
                    emptyNumber++
                    fragmentQuizBinding.textViewEmpty.text = emptyNumber.toString()
                } else {
                    setButtonToInitialProperties()
                }
            }

            optionControl = false
        }

        return fragmentQuizBinding.root
    }

    private fun showData() {
        fragmentQuizBinding.textViewQuestion.text =
            resources.getString(R.string.question_number).plus(" ").plus(questionNumber + 1)

        correctFlag = flagList[questionNumber]

        fragmentQuizBinding.imageViewFlag.setImageResource(
            resources.getIdentifier(correctFlag.flagName, "drawable", requireActivity().packageName)
        )

        wrongFlags = dao.getRandomThreeRecords(DatabaseCopyHelper(requireActivity()), correctFlag.id)

        val mixOptions = HashSet<FlagsModel>()
        mixOptions.add(correctFlag)
        mixOptions.addAll(wrongFlags)

        val options = ArrayList(mixOptions)

        // Shuffle to randomize option order
        options.shuffle()

        fragmentQuizBinding.buttonA.text = options[0].countryName
        fragmentQuizBinding.buttonB.text = options[1].countryName
        fragmentQuizBinding.buttonC.text = options[2].countryName
        fragmentQuizBinding.buttonD.text = options[3].countryName
    }

    private fun answerControl(button: Button) {
        val clickedOption = button.text.toString()
        val correctAnswer = correctFlag.countryName

        if (clickedOption == correctAnswer) {
            correctNumber++
            fragmentQuizBinding.textViewCorrect.text = correctNumber.toString()
            button.setBackgroundColor(Color.GREEN)
        } else {
            wrongNumber++
            fragmentQuizBinding.textViewWrong.text = wrongNumber.toString()
            button.setBackgroundColor(Color.RED)
            button.setTextColor(Color.WHITE)

            when (correctAnswer) {
                fragmentQuizBinding.buttonA.text -> fragmentQuizBinding.buttonA.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonB.text -> fragmentQuizBinding.buttonB.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonC.text -> fragmentQuizBinding.buttonC.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonD.text -> fragmentQuizBinding.buttonD.setBackgroundColor(Color.GREEN)
            }
        }

        fragmentQuizBinding.buttonA.isClickable = false
        fragmentQuizBinding.buttonB.isClickable = false
        fragmentQuizBinding.buttonC.isClickable = false
        fragmentQuizBinding.buttonD.isClickable = false

        optionControl = true
    }

    private fun setButtonToInitialProperties() {
        val buttons = listOf(
            fragmentQuizBinding.buttonA,
            fragmentQuizBinding.buttonB,
            fragmentQuizBinding.buttonC,
            fragmentQuizBinding.buttonD
        )

        for (button in buttons) {
            button.apply {
                setBackgroundColor(Color.WHITE)
                setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
                isClickable = true
            }
        }
    }
}
