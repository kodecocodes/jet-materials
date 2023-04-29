package com.yourcompany.android.jetreddit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.yourcompany.android.jetreddit.components.ImagePost
import com.yourcompany.android.jetreddit.components.Post
import com.yourcompany.android.jetreddit.components.TextPost
import com.yourcompany.android.jetreddit.domain.model.PostModel
import com.yourcompany.android.jetreddit.util.Tags
import org.junit.Rule
import org.junit.Test

class PostTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()


    @Test
    fun title_is_displayed() {
        val post = PostModel.DEFAULT_POST
        composeTestRule.setContent {
            Post(post = post)
        }

        composeTestRule.onNodeWithText(post.title).assertIsDisplayed()
    }

    @Test
    fun like_count_is_displayed() {
        val post = PostModel.DEFAULT_POST
        composeTestRule.setContent {
            Post(post = post)
        }

        composeTestRule.onNodeWithText(post.likes).assertIsDisplayed()
    }

    @Test
    fun image_is_displayed_for_post_with_image() {
        val post = PostModel.DEFAULT_POST
        composeTestRule.setContent {
            ImagePost(post = post)
        }

        composeTestRule.onNodeWithTag(Tags.POST_IMAGE, true).assertIsDisplayed()
    }

    @Test
    fun text_is_displayed_for_post_with_text() {
        val post = PostModel.DEFAULT_POST
        composeTestRule.setContent {
            TextPost(post = post)
        }

        composeTestRule.onNodeWithTag(Tags.POST_TEXT, true).assertIsDisplayed()
    }
}