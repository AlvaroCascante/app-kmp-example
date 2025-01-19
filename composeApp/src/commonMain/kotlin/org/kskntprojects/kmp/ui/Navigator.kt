package org.kskntprojects.kmp.ui

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import io.github.oshai.kotlinlogging.KotlinLogging
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.kskntprojects.kmp.clients.PostClient

@Composable
fun Navigation(
    listStatus: MutableState<Boolean> = mutableStateOf(false),
    navigator: Navigator,
    postClient: PostClient
) {

    val logger = KotlinLogging.logger {}
    logger.info { "FoundersApp Navigation listStatus: $listStatus" }
    val colors = getColorsTheme()

    NavHost(
        navigator = navigator,
        initialRoute = "/home",
        modifier = Modifier.background(colors.backgroundColor)
    ) {
        scene(route = "/home") {
            FoundersScreen(
                listStatus = listStatus,
                colors = colors
            )
        }
        scene(route = "/posts") {
            PostsScreen(
                colors = colors,
                postClient = postClient
            )
        }
    }

}