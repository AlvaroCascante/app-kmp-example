package org.kskntprojects.kmp.clients

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.kskntprojects.kmp.models.Post
import org.kskntprojects.kmp.network.NetworkErrorType
import org.kskntprojects.kmp.network.Result

class PostClient (
    private val httpClient: HttpClient
) {
    suspend fun getPosts(): Result<List<Post>, NetworkErrorType> {

        val response = try {
            httpClient.get(urlString = "https://jsonplaceholder.typicode.com/posts")
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkErrorType.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkErrorType.SERIALIZATION)
        }
        return when(response.status.value) {
            in 200..299 -> Result.Success(response.body<List<Post>>())
            in 500 .. 599 -> Result.Error(NetworkErrorType.SERVER_ERROR)
            401 -> Result.Error(NetworkErrorType.UNAUTHORIZED)
            409 -> Result.Error(NetworkErrorType.CONFLICT)
            408 -> Result.Error(NetworkErrorType.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkErrorType.PAYLOAD_TOO_LARGE)
            429 -> Result.Error(NetworkErrorType.TOO_MANY_REQUESTS)
            else -> Result.Error(NetworkErrorType.UNKNOWN)
        }
    }
}