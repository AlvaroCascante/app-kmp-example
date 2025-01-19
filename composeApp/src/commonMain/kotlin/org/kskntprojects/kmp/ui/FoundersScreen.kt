package org.kskntprojects.kmp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.oshai.kotlinlogging.KotlinLogging
import kmp_demo.composeapp.generated.resources.Res
import kmp_demo.composeapp.generated.resources.founders
import kmp_demo.composeapp.generated.resources.freelance_navigator
import kmp_demo.composeapp.generated.resources.test
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.painterResource
import org.kskntprojects.kmp.models.Founder
import org.kskntprojects.kmp.presentation.FounderState
import org.kskntprojects.kmp.presentation.FounderViewModel
import org.kskntprojects.kmp.repositories.FounderRepositoryImpl

@Composable
fun FoundersScreen(
    listStatus : MutableState<Boolean> = mutableStateOf(false),
    colors: DarkModeColors = getColorsTheme(),
    modifier: Modifier = Modifier
) {

    val logger = KotlinLogging.logger {}
    logger.info { "FoundersApp FoundersScreen listStatus: $listStatus" }

    val viewModel : FounderViewModel = viewModel (modelClass = FounderViewModel::class) {
        FounderViewModel(FounderRepositoryImpl())
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    FounderScreenContent(
        colors = colors,
        state = state,
        listStatus = listStatus
    )
}

@Composable
fun FounderScreenContent(
    colors: DarkModeColors = getColorsTheme(),
    listStatus: MutableState<Boolean> = mutableStateOf(false),
    state: FounderState,
    modifier: Modifier = Modifier
) {
    val logger = KotlinLogging.logger {}
    logger.info { "FoundersApp FounderScreenContent listStatus: $listStatus" }
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
        listStatus = listStatus,
        state = state,
        colors = colors
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoundersList(
    listStatus: MutableState<Boolean> = mutableStateOf(false),
    state: FounderState,
    colors: DarkModeColors = getColorsTheme(),
    modifier: Modifier = Modifier
) {

    val logger = KotlinLogging.logger {}
    logger.info { "FoundersApp FoundersList listStatus: $listStatus" }
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
                    AnimatedVisibility(visible = listStatus.value) {
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