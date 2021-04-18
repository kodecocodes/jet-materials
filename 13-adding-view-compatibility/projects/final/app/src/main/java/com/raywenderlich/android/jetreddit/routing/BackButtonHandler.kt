package com.raywenderlich.android.jetreddit.routing

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*
import androidx.compose.ui.platform.AmbientLifecycleOwner

private val BackPressedDispatcher = staticAmbientOf<OnBackPressedDispatcher?> { null }

@Composable
fun BackButtonHandler(
  enabled: Boolean = true,
  onBackPressed: () -> Unit
) {
  val dispatcher = BackPressedDispatcher.current ?: return
  val backCallback = remember {
    object : OnBackPressedCallback(enabled) {
      override fun handleOnBackPressed() {
        onBackPressed.invoke()
      }
    }
  }
  DisposableEffect(dispatcher) {
    dispatcher.addCallback(backCallback)
    onDispose {
      backCallback.remove()
    }
  }
}

@Composable
fun BackButtonAction(onBackPressed: () -> Unit) {
  Providers(
    BackPressedDispatcher provides (
        AmbientLifecycleOwner.current as ComponentActivity
        ).onBackPressedDispatcher
  ) {
    BackButtonHandler {
      onBackPressed.invoke()
    }
  }
}