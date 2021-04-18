package com.raywenderlich.android.jetreddit.routing

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner

private val localBackPressedDispatcher = staticCompositionLocalOf<OnBackPressedDispatcher?> { null }

@Composable
fun BackButtonHandler(
  enabled: Boolean = true,
  onBackPressed: () -> Unit
) {
  val dispatcher = localBackPressedDispatcher.current ?: return
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
  CompositionLocalProvider(
    localBackPressedDispatcher provides (
        LocalLifecycleOwner.current as ComponentActivity
        ).onBackPressedDispatcher
  ) {
    BackButtonHandler {
      onBackPressed.invoke()
    }
  }
}