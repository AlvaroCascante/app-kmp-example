package org.kskntprojects.kmp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp_demo.composeapp.generated.resources.Res
import kmp_demo.composeapp.generated.resources.founders
import kmp_demo.composeapp.generated.resources.freelance_navigator
import kmp_demo.composeapp.generated.resources.ic_fn
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kskntprojects.kmp.models.Founder
import org.kskntprojects.kmp.presentation.FounderState
import org.kskntprojects.kmp.presentation.FounderViewModel
import org.kskntprojects.kmp.repositories.FounderRepositoryImpl

@Composable
@Preview
fun App() {
    PreComposeApp {
        AppTheme {
            val viewModel : FounderViewModel = viewModel (modelClass = FounderViewModel::class) {
                FounderViewModel(FounderRepositoryImpl())
            }
            val state by viewModel.state.collectAsStateWithLifecycle()
            val colors = getColorsTheme()
            FoundersScreen(
                colors = colors,
                state = state
            )
        }
    }
}

@Composable
fun FoundersScreen(
    colors: DarkModeColors = getColorsTheme(),
    state: FounderState,
    modifier: Modifier = Modifier
) {
    val listStatus = mutableStateOf(false)
    Scaffold(
        modifier = Modifier.fillMaxSize().background(colors.backgroundColor),
        topBar = {
            CustomTopAppBar(colors = colors)
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
    ) {
        FounderScreenContent(
            colors = colors,
            state = state,
            status = listStatus.value
        )
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
fun FounderScreenContent(
    colors: DarkModeColors = getColorsTheme(),
    status: Boolean = false,
    state: FounderState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize().background(colors.backgroundColor)
    ) {
        Image(
            painter = painterResource(Res.drawable.freelance_navigator),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
    FoundersList(
        status = status,
        state = state,
        colors = colors
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoundersList(
    status: Boolean = false,
    state: FounderState,
    colors: DarkModeColors = getColorsTheme(),
    modifier: Modifier = Modifier
) {
    when(state) {
        is FounderState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is FounderState.Success -> {
            val founders = state.founders
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                stickyHeader {
                    FoundersCard(colors = colors)
                }
                items(founders.size) { index ->
                    AnimatedVisibility(visible = status) {
                        FoundItem(founder = founders[index], colors = colors)
                    }
                }
            }
        }
        is FounderState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${state.exception.message}",
                    color = colors.itemTextColor
                )
            }
        }
    }
}

@Composable
fun FoundersCard(
    colors: DarkModeColors = getColorsTheme(),
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20),
        backgroundColor = colors.cardBackgroundColor,
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.founders),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun FoundItem(
    colors: DarkModeColors = getColorsTheme(),
    founder: Founder,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16),
        backgroundColor = colors.itemBackgroundColor,
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth().padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(35),
                color = colors.itemImageBackgroundColor,
                modifier = Modifier.size(50.dp)
            ) {
                Image(
                    imageVector = founder.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(colors.itemImageTintColor)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = founder.name,
                    fontSize = 18.sp,
                    color = colors.itemTextColor,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
                Text(
                    text = founder.role,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.h4,
                    color = colors.itemTextColor,
                )
            }
        }
    }
}