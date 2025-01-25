package org.kskntprojects.kmp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp_demo.composeapp.generated.resources.Res
import kmp_demo.composeapp.generated.resources.founders
import kmp_demo.composeapp.generated.resources.freelance_navigator
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.painterResource
import org.kskntprojects.kmp.clients.PostClient
import org.kskntprojects.kmp.models.Post
import org.kskntprojects.kmp.presentation.PostState
import org.kskntprojects.kmp.presentation.PostViewModel
import org.kskntprojects.kmp.repositories.PostRemoteDatasource
import org.kskntprojects.kmp.repositories.PostRemoteDatasourceImpl
import org.kskntprojects.kmp.repositories.PostRepositoryImpl


@Composable
fun PostsScreen(
    colors: DarkModeColors = getColorsTheme(),
    postClient: PostClient,
    modifier: Modifier = Modifier
) {
    val postRemoteDataSource = PostRemoteDatasourceImpl(postClient)
    val viewModel : PostViewModel = viewModel(modelClass = PostViewModel::class) {
        PostViewModel(PostRepositoryImpl(postRemoteDataSource))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    PostScreenContent(
        colors = colors,
        state = state
    )
}

@Composable
fun PostScreenContent(
    colors: DarkModeColors = getColorsTheme(),
    state: PostState,
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
    PostList(
        state = state,
        colors = colors
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostList(
    state: PostState,
    colors: DarkModeColors = getColorsTheme(),
    modifier: Modifier = Modifier
) {
    when(state) {
        is PostState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is PostState.Success -> {
            val posts = state.posts
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                stickyHeader {
                    PostsCard(colors = colors)
                }
                items(posts.size) { index ->
                    PostItem(post = posts[index], colors = colors)
                }
            }
        }
        is PostState.Error -> {
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
fun PostsCard(
    colors: DarkModeColors = getColorsTheme(),
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(2.dp, colors.itemBackgroundColor),
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
            Text(
                text = "Posts",
                fontSize = 36.sp,
                color = colors.cardTextColor,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun PostItem(
    colors: DarkModeColors = getColorsTheme(),
    post: Post,
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
            Text(
                text = "User: " + post.userId,
                fontSize = 18.sp,
                color = colors.itemTextColor,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = post.title,
                fontSize = 18.sp,
                style = MaterialTheme.typography.h4,
                color = colors.itemTextColor,
                maxLines = 1,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }
    }
}