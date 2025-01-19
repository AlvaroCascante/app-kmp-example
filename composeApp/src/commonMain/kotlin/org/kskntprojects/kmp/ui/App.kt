package org.kskntprojects.kmp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.oshai.kotlinlogging.KotlinLogging
import kmp_demo.composeapp.generated.resources.Res
import kmp_demo.composeapp.generated.resources.ic_fn
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kskntprojects.kmp.clients.PostClient

@Composable
@Preview
fun App(
    postClient : PostClient,
) {

    val logger = KotlinLogging.logger {}
    logger.info { "Founders App screen" }
    PreComposeApp {

        val navigator = rememberNavigator()
        val colors = getColorsTheme()
        val listStatus = mutableStateOf(false)
        AppTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize().background(colors.backgroundColor),
                topBar = {
                    CustomTopAppBar(colors = colors)
                },
                bottomBar = {
                    CustomBottomAppBar(
                        navigator = navigator,
                        colors = colors
                    )
                },
                floatingActionButton = {
                    Button(
                        onClick = {
                            listStatus.value = !listStatus.value
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colors.itemBackgroundColor)
                    ) {
                        Text(
                            text = if (listStatus.value) "Hide" else "Show",
                            color = colors.itemTextColor
                        )
                    }
                }
            ){
                Navigation(
                    listStatus = listStatus,
                    postClient = postClient,
                    navigator = navigator
                )
            }
        }
    }
}

@Composable
fun CustomTopAppBar(
    colors: DarkModeColors = getColorsTheme(),
    modifier: Modifier = Modifier
) {
    TopAppBar(
        elevation = 0.dp,
        title = {
            Text(
                text = "Freelance Navigator Founders",
                color = colors.itemTextColor,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
        },
        backgroundColor = colors.itemBackgroundColor,
        contentColor = colors.itemTextColor,
        navigationIcon = {
            IconButton(
                onClick = { }
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_fn),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    )
}

@Composable
fun CustomBottomAppBar(
    colors: DarkModeColors = getColorsTheme(),
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedButtonWithLabel(
                imageVector = Icons.Outlined.Home,
                label = "Posts",
                onClick = { navigator.navigate("/posts") }
            )
        }
    }
}

@Composable
fun RoundedButtonWithLabel(
    imageVector: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedIconButton(
            imageVector = imageVector,
            onClick = onClick
        )
        Text(
            text = label,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.button
        )
    }
}

@Composable
fun RoundedIconButton(
    imageVector:  ImageVector,
    onClick: () -> Unit = { /**/ },
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.wrapContentSize()
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}