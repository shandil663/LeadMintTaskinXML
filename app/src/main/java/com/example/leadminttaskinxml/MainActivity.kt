package com.example.leadminttaskinxml

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var activateButton: Button
    private lateinit var remainingTimeTextView: TextView
    private lateinit var badge: RelativeLayout
    private lateinit var booster: LinearLayout
    private var countDownTimer: CountDownTimer? = null
    private val totalTime = 60 * 60 * 1000L
    lateinit var cardclickable: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val lock = findViewById<TextView>(R.id.locker)
        lock.setOnClickListener {
            showBottomSheet()
        }
        badge = findViewById(R.id.boost_timer_container)
        booster = findViewById(R.id.booster)
        cardclickable = findViewById(R.id.img)
        cardclickable.setOnClickListener {
            val intent = Intent(this, ReferAndEarn::class.java)
            startActivity(intent)
        }
        activateButton = findViewById(R.id.activate_button)
        remainingTimeTextView = findViewById(R.id.remaining_time)

        activateButton.setOnClickListener {
            booster.visibility = View.GONE
            badge.visibility = View.VISIBLE
            startTimer()
        }
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                remainingTimeTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                remainingTimeTextView.text = "00:00"
            }
        }.start()
    }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.sheet, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
}