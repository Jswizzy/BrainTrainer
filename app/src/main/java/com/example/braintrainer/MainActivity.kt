package com.example.braintrainer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var answers: List<Int>
    private var correctPos: Int = 0
    private lateinit var buttons: List<Button>

    private var score = 0
    private var numberOfQuestions = 0


    fun onStart(view: View) {
        startButton.visibility = INVISIBLE
        newQuestion()
    }

    fun onPlayAgain(view: View) {
        playAgainButton.visibility = INVISIBLE
        resultTextView.visibility = INVISIBLE
        score = 0
        numberOfQuestions = 0
        scoreTextView.text = "$score/$numberOfQuestions"
        timerTextView.text = "30s"
        enableButtons()
        newQuestion()

        object: CountDownTimer( 30 * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
            override fun onFinish() {
                disableButtons()
                resultTextView.apply {
                    visibility = VISIBLE
                    text = "DONE!"
                }
                playAgainButton.visibility = VISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                val countdown = millisUntilFinished / ONE_SECOND_IN_MILLIS
                timerTextView.text = "${countdown}s"
            }

        }.start()
    }

    fun onAnswer(view: View) {
        val pos = view.tag.toString().toInt()
        val result = if (pos == correctPos) {
            score++
            "Correct!"
        } else {
            "Incorrect!"
        }

        resultTextView.apply {
            text = result
            visibility = VISIBLE
        }
        numberOfQuestions++
        scoreTextView.text = "$score/$numberOfQuestions"

        newQuestion()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons =  listOf(button0, button1, button2, button3)

        resultTextView.visibility = INVISIBLE
        onPlayAgain(timerTextView)
    }

    private fun newQuestion() {
        val a = Random.nextInt(21)
        val b = Random.nextInt(21)

        sumTextView.text = "$a + $b="

        correctPos = Random.nextInt(4)

        answers = buttons.mapIndexed { i, button ->
            if (i == correctPos) {
                button.text = (a + b).toString()
                a + b
            } else {
                var wrongAnswer = Random.nextInt(41)
                while (wrongAnswer == a + b) {
                    wrongAnswer = Random.nextInt(41)
                }
                button.text = wrongAnswer.toString()
                wrongAnswer
            }
        }
    }

    fun disableButtons() {
        buttons.forEach { btn ->
            btn.isEnabled = false
        }
    }

    fun enableButtons() {
        buttons.forEach { btn ->
            btn.isEnabled = true
        }
    }
}



const val ONE_SECOND_IN_MILLIS = 1000L