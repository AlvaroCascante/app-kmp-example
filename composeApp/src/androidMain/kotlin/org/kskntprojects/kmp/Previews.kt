package org.kskntprojects.kmp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.kskntprojects.kmp.presentation.FounderState
import org.kskntprojects.kmp.repositories.foundersList
import org.kskntprojects.kmp.ui.CustomTopAppBar
import org.kskntprojects.kmp.ui.FoundItem
import org.kskntprojects.kmp.ui.FounderScreenContent
import org.kskntprojects.kmp.ui.FoundersCard
import org.kskntprojects.kmp.ui.FoundersList


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
        status = true,
        state = FounderState.Success(foundersList)
    )
}


@Preview
@Composable
private fun FoundersListPreviewFalse() {
    FoundersList(
        status = false,
        state = FounderState.Success(foundersList)
    )
}

@Preview
@Composable
private fun FounderScreenContentPreview() {
    FounderScreenContent(
        status = true,
        state = FounderState.Success(foundersList)
    )
}

@Preview
@Composable
private fun FounderScreenContentPreviewFalse() {
    FounderScreenContent(
        status = false,
        state = FounderState.Success(foundersList)
    )
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    CustomTopAppBar()

}