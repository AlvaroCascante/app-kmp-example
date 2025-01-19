package org.kskntprojects.kmp.models

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    var id: Int = 0,
    var userId: Int = 0,
    var title: String = "",
    var body: String = ""
)