package com.raywenderlich.android.jetreddit.routing

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*
import androidx.compose.ui.platform.AmbientLifecycleOwner

private val AmbientBackPressedDispatcher = staticAmbientOf<OnBackPressedDispatcher?> { null }

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