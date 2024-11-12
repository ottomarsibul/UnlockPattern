package com.example.unlockpattern

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isSettingPattern = true // is user setting the new pattern?
    private var savedPattern: List<Int>? = null // saved pattern (in the beginning 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // using activity_main.xml

        val patternView = findViewById<PatternView>(R.id.pattern_view) // connecting with xml id
        patternView.onPatternCompleted = { pattern ->
            // new pattern
            if (isSettingPattern) {
                if (pattern.size >= 4) {
                    savedPattern = pattern
                    isSettingPattern = false
                    Toast.makeText(this, "Pattern is saved", Toast.LENGTH_SHORT).show()
                    // too short pattern
                } else {
                    Toast.makeText(this, "Pattern must include at least 4 points.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // matching pattern
                if (pattern == savedPattern) {
                    Toast.makeText(this, "Correct, device is open now!", Toast.LENGTH_SHORT).show()
                    // wrong pattern
                } else {
                    Toast.makeText(this, "Wrong pattern!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
