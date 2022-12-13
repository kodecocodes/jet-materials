package com.yourcompany.android.jetreddit

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yourcompany.android.jetreddit.components.ImagePost
import com.yourcompany.android.jetreddit.components.Post
import com.yourcompany.android.jetreddit.components.TextPost
import com.yourcompany.android.jetreddit.domain.model.PostModel
import com.yourcompany.android.jetreddit.factory.PostDataFactory
import com.yourcompany.android.jetreddit.routing.Screen
import com.yourcompany.android.jetreddit.util.Tags
import com.yourcompany.android.jetreddit.viewmodel.MainViewModel
import com.yourcompany.android.jetreddit.viewmodel.MainViewModelFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class JetRedditAppTest {

    //TODO create rule

    @Test
    fun app_shows_home_screen() {
        TODO()
    }

    @Test
    fun app_shows_subreddits_screen() {
        TODO()
    }

    @Test
    fun app_shows_new_post_screen() {
        TODO()
    }

    @Test
    fun app_shows_drawer() {
        TODO()
    }

    @Test
    fun app_shows_toast_when_joining_community() {
        TODO()
    }

}