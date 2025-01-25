package org.kskntprojects.kmp.repositories

import kotlinx.coroutines.flow.Flow
import org.kskntprojects.kmp.models.Post

interface PostRepository {
    suspend fun getPosts(): Flow<List<Post>>
}

class PostRepositoryImpl(private val remoteDatasource: PostRemoteDatasource) : PostRepository {
    override suspend fun getPosts(): Flow<List<Post>> {
        return remoteDatasource.getPosts()
    }
}