package org.kskntprojects.kmp.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kskntprojects.kmp.clients.PostClient
import org.kskntprojects.kmp.models.Post
import org.kskntprojects.kmp.network.onError
import org.kskntprojects.kmp.network.onSuccess

interface PostRemoteDatasource {
    suspend fun getPosts(): Flow<List<Post>>
}

val postsList = listOf(
    Post(id = 1, userId = 1, title = "Title 1", body = "Body 1")
)

class PostRemoteDatasourceImpl(
    private val postClient: PostClient
): PostRemoteDatasource {
    override suspend fun getPosts(): Flow<List<Post>> {
        return flow {
            postClient.getPosts().onSuccess {
                emit(it)
            }.onError {
                emit(emptyList())
            }
        }
    }
}