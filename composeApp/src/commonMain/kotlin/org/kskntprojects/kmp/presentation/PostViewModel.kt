package org.kskntprojects.kmp.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.kskntprojects.kmp.models.Founder
import org.kskntprojects.kmp.models.Post
import org.kskntprojects.kmp.repositories.FounderRepository
import org.kskntprojects.kmp.repositories.PostRepository

sealed class PostState {
    data object Loading : PostState()
    data class Success(val posts: List<Post>) : PostState()
    data class Error(val exception: Throwable) : PostState()
}

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _state = MutableStateFlow<PostState>(PostState.Loading)
    val state: StateFlow<PostState> = _state

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.getPosts().collect {
                _state.value = PostState.Success(it)
            }
        }
    }
}