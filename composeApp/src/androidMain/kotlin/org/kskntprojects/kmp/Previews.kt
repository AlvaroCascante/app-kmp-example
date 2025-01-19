package org.kskntprojects.kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import org.kskntprojects.kmp.presentation.FounderState
import org.kskntprojects.kmp.repositories.foundersList
import org.kskntprojects.kmp.repositories.postsList
import org.kskntprojects.kmp.ui.CustomTopAppBar
import org.kskntprojects.kmp.ui.FoundItem
import org.kskntprojects.kmp.ui.FounderScreenContent
import org.kskntprojects.kmp.ui.FoundersCard
import org.kskntprojects.kmp.ui.FoundersList
import org.kskntprojects.kmp.ui.PostItem
import org.kskntprojects.kmp.ui.PostsCard


@Preview
@Composable
private fun PostItemPreview() {
    PostItem(post = postsList.first())
}

@Preview
@Composable
private fun PostsCardPreview() {
    PostsCard()
}

@Preview
@Composable
private fun FoundItemPreview() {
    FoundItem(founder = foundersList.first())
}

@Preview
@Composable
private fun FoundersCardPreview() {
    FoundersCard()
}

@Preview
@Composable
private fun FoundersListPreview() {
    FoundersList(
        state = FounderState.Success(foundersList)
    )
}


@Preview
@Composable
private fun FoundersListPreviewFalse() {
    FoundersList(
        state = FounderState.Success(foundersList)
    )
}

@Preview
@Composable
private fun FounderScreenContentPreview() {
    FounderScreenContent(
        state = FounderState.Success(foundersList)
    )
}

@Preview
@Composable
private fun FounderScreenContentPreviewFalse() {
    FounderScreenContent(
        state = FounderState.Success(foundersList)
    )
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    CustomTopAppBar()

}