package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import android.content.res.Configuration
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private const val HOME_ROUTE = "Home"
private const val SETTINGS_ROUTE = "Settings"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Navigation()
            }
        }
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(route = HOME_ROUTE) { Home(navController) }
        composable(route = SETTINGS_ROUTE) { Settings(navController) }
    }
}


@Composable
fun Home(navController: NavController) {
    Column {
        IconButton(
            onClick = { navController.navigate("Settings") },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                Icons.Rounded.Settings, contentDescription = "Settings",
            )
        }
        Conversation(SampleData.conversationSample)
    }
}


@Composable
fun Settings(navController: NavController) {
    var text by remember { mutableStateOf("Lexi") }
    Column {
        IconButton(
            onClick = {
                navController.navigate("Home") {
                    popUpTo("Home") {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                Icons.Rounded.ArrowBack, contentDescription = "ArrowBack",
            )
        }
        Text(text = "User:")
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        TextField(value = text, onValueChange = { text = it })
    }
}


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(
                Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}
