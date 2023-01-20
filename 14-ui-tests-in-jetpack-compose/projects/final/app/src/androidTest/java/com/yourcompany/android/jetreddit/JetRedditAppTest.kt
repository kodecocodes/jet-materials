package com.yourcompany.android.jetreddit

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.yourcompany.android.jetreddit.factory.PostDataFactory
import com.yourcompany.android.jetreddit.routing.Screen
import com.yourcompany.android.jetreddit.util.Tags
import org.junit.Rule
import org.junit.Test

class JetRedditAppTest {

    @get:Rule(order = 0)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun app_shows_home_screen() {
        composeTestRule.activity.setContent {
            JetRedditApp(
                allPosts = PostDataFactory.createPosts(),
                myPosts = PostDataFactory.createPosts(),
                communities = PostDataFactory.createCommunities(),
                selectedCommunity = PostDataFactory.randomString(),
                savePost = {},
                searchCommunities = {},
                communitySelected ={}
            )
        }

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.home)).assertIsDisplayed()
    }

    @Test
    fun app_shows_subreddits_screen() {
        composeTestRule.activity.setContent {
            JetRedditApp(
                allPosts = PostDataFactory.createPosts(),
                myPosts = PostDataFactory.createPosts(),
                communities = PostDataFactory.createCommunities(),
                selectedCommunity = PostDataFactory.randomString(),
                savePost = {},
                searchCommunities = {},
                communitySelected ={}
            )
        }

        composeTestRule.onNodeWithTag(
            Screen.Subscriptions.route
        ).performClick()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.subreddits)).assertIsDisplayed()
    }

    @Test
    fun app_shows_drawer() {
        composeTestRule.activity.setContent {
            JetRedditApp(
                allPosts = PostDataFactory.createPosts(),
                myPosts = PostDataFactory.createPosts(),
                communities = PostDataFactory.createCommunities(),
                selectedCommunity = PostDataFactory.randomString(),
                savePost = {},
                searchCommunities = {},
                communitySelected ={}
            )
        }

        composeTestRule.onNodeWithTag(
            Tags.ACCOUNT_BUTTON
        ).performClick()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.default_username)).assertIsDisplayed()
    }

    @Test
    fun app_shows_toast_when_joining_community() {
        composeTestRule.activity.setContent {
            JetRedditApp(
                allPosts = PostDataFactory.createPosts(),
                myPosts = PostDataFactory.createPosts(),
                communities = PostDataFactory.createCommunities(),
                selectedCommunity = PostDataFactory.randomString(),
                savePost = {},
                searchCommunities = {},
                communitySelected ={}
            )
        }

        composeTestRule.onAllNodes(
            hasTestTag(Tags.JOIN_BUTTON)
        ).onFirst().performClick()

        composeTestRule.onNodeWithTag(Tags.JOINED_TOAST).assertIsDisplayed()
    }
    
    @Test
    fun app_shows_new_post_screen() {
        composeTestRule.activity.setContent {
            JetRedditApp(
                allPosts = PostDataFactory.createPosts(),
                myPosts = PostDataFactory.createPosts(),
                communities = PostDataFactory.createCommunities(),
                selectedCommunity = PostDataFactory.randomString(),
                savePost = {},
                searchCommunities = {},
                communitySelected ={}
            )
        }

        composeTestRule.onNodeWithTag(
            Screen.NewPost.route
        ).performClick()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.new_post)).assertIsDisplayed()
    }

    @Test
    fun chat_button_is_displayed() {
        composeTestRule.onNodeWithTag(Tags.CHAT_BUTTON).performClick()

        Espresso.onView(withId(R.id.composeButton))
            .check(matches(isDisplayed()))
    }
}