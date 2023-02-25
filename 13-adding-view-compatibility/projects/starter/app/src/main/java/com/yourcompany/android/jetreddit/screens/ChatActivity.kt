package com.yourcompany.android.jetreddit.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

  private lateinit var binding: ActivityChatBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.Theme_JetReddit)

    super.onCreate(savedInstanceState)
    binding = ActivityChatBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.backButton.setOnClickListener {
      finish()
    }

    binding.button.setOnClickListener {
      showToast()
    }
  }

  private fun showToast() {
    Toast.makeText(this, "Imaginary chat started!", Toast.LENGTH_SHORT).show()
  }
}