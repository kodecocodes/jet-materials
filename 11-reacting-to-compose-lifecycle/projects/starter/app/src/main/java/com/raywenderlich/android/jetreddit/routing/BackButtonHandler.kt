package com.raywenderlich.android.jetreddit.routing

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*

private val localBackPressedDispatcher = staticCompositionLocalOf<OnBackPressedDispatcher?> { null }

@Composable
fun BackButtonHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
  //TODO Add your code here
}

@Composable
fun BackButtonAction(onBackPressed: () -> Unit) {
  //TODO Add your code here
}
