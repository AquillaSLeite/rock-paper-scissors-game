package com.example.rockpaperscissorsgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

private const val VICTORY = "Vitória"
private const val DEFEAT = "Derrota"
private const val DRAW = "Empate"

private val optionsId = listOf(
    R.id.imageRock,
    R.id.imagePaper,
    R.id.imageScissors
)

private val optionsImage = mapOf(
    R.id.imageRock to R.drawable.rock,
    R.id.imagePaper to R.drawable.paper,
    R.id.imageScissors to R.drawable.scissors
)

private val results = mapOf(
    "${R.id.imageRock}${R.id.imageRock}" to DRAW,
    "${R.id.imageRock}${R.id.imagePaper}" to DEFEAT,
    "${R.id.imageRock}${R.id.imageScissors}" to VICTORY,
    "${R.id.imagePaper}${R.id.imageRock}" to VICTORY,
    "${R.id.imagePaper}${R.id.imagePaper}" to DRAW,
    "${R.id.imagePaper}${R.id.imageScissors}" to DEFEAT,
    "${R.id.imageScissors}${R.id.imageRock}" to DEFEAT,
    "${R.id.imageScissors}${R.id.imagePaper}" to VICTORY,
    "${R.id.imageScissors}${R.id.imageScissors}" to DRAW
)

class MainActivity : AppCompatActivity() {

    private val random = Random

    private var victoryCounter = 0
    private var defeatCounter = 0
    private var drawCounter = 0

    private lateinit var textResult: TextView
    private lateinit var appResult: ImageView
    private lateinit var textVictoryCounter: TextView
    private lateinit var textDefeatCounter: TextView
    private lateinit var textDrawCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        textResult = findViewById(R.id.textResult)
        appResult = findViewById(R.id.appResult)
        textVictoryCounter = findViewById(R.id.textVictoryCounter)
        textDefeatCounter = findViewById(R.id.textDefeatCounter)
        textDrawCounter = findViewById(R.id.textDrawCounter)
    }

    fun play(view: View) {
        val playerSelection = view.id
        val appSelection = retrieveAppSelection()

        updateInterfaceTexts(verifyWinner(playerSelection, appSelection))
    }

    fun restartGame(view: View) {
        victoryCounter = 0
        defeatCounter = 0
        drawCounter = 0

        appResult.setImageResource(R.drawable.empty)

        updateInterfaceTexts("")
    }

    private fun retrieveAppSelection(): Int {
        val result = optionsId[random.nextInt(3)]

        appResult.setImageResource(optionsImage[result]!!)

        return result
    }

    private fun verifyWinner(playerSelection: Int, appSelection: Int): String {
        val result = results["$playerSelection$appSelection"]!!

        when (result) {
            VICTORY -> victoryCounter++
            DEFEAT -> defeatCounter++
            else -> drawCounter++
        }

        return result
    }

    @SuppressLint("SetTextI18n")
    private fun updateInterfaceTexts(result: String) {
        textResult.text = "Resultado: $result"
        textVictoryCounter.text = "Vitórias: $victoryCounter"
        textDefeatCounter.text = "Derrotas: $defeatCounter"
        textDrawCounter.text = "Empates: $drawCounter"
    }
}
